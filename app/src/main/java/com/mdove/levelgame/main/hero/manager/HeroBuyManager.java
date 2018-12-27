package com.mdove.levelgame.main.hero.manager;

import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.MedicinesDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Material;
import com.mdove.levelgame.greendao.entity.Medicines;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Skill;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.model.BaseBuy;
import com.mdove.levelgame.main.hero.model.BuyArmorResp;
import com.mdove.levelgame.main.hero.model.BuyAttackResp;
import com.mdove.levelgame.main.hero.model.BuyMedicinesResp;
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by MDove on 2018/10/20.
 */

public class HeroBuyManager {
    public static final int BUY_MEDICINES_STATUS_SUC = 1;
    // 没钱购买失败
    public static final int BUY_MEDICINES_STATUS_FAIL = 2;
    // 次数不足
    public static final int BUY_MEDICINES_STATUS_FAIL_NO_COUNT = 7;
    public static final int BUY_MEDICINES_STATUS_ERROR = 3;

    public static final int BUY_ATTACK_STATUS_SUC = 4;
    public static final int BUY_ATTACK_STATUS_FAIL = 5;
    public static final int BUY_ATTACK_STATUS_ERROR = 6;

    public static final int BUY_ARMOR_STATUS_SUC = 7;
    public static final int BUY_ARMOR_STATUS_FAIL = 8;
    public static final int BUY_ARMOR_STATUS_ERROR = 9;

    public static final int BUY_BASE_STATUS_SUC = 10;
    public static final int BUY_BASE_STATUS_FAIL = 11;
    public static final int BUY_BASE_STATUS_ERROR = 12;

    private PackagesDao packagesDao;

    private static class SingletonHolder {
        static final HeroBuyManager INSTANCE = new HeroBuyManager();
    }

    private HeroBuyManager() {
        packagesDao = DatabaseManager.getInstance().getPackagesDao();
    }

    public static HeroBuyManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public BuyMedicinesResp buyMedicines(long id) {
        HeroAttributes heroAttributes = HeroManager.getInstance().getHeroAttributes();
        BuyMedicinesResp resp = new BuyMedicinesResp();
        resp.buyStatus = BUY_MEDICINES_STATUS_ERROR;
        Medicines medicine = DatabaseManager.getInstance().getMedicinesDao().queryBuilder()
                .where(MedicinesDao.Properties.Id.eq(id)).unique();
        if (medicine != null) {
            // 没钱
            if (heroAttributes.money - medicine.price < 0) {
                resp.buyStatus = BUY_MEDICINES_STATUS_FAIL;
            } else if (medicine.isLimitCount == 0 && medicine.curCount <= 0) {
                resp.buyStatus = BUY_MEDICINES_STATUS_FAIL_NO_COUNT;
            } else {// 购买成功
                resp.buyStatus = BUY_MEDICINES_STATUS_SUC;
                medicine.curCount--;
                // 先判断能否增加生命上限
                if (medicine.lifeUp > 0) {
                    heroAttributes.maxLife += medicine.lifeUp;
                }
                // 当前生命超出上限，舍弃
                if (heroAttributes.curLife + medicine.life > heroAttributes.maxLife) {
                    heroAttributes.curLife = heroAttributes.maxLife;
                } else {
                    heroAttributes.curLife += medicine.life;
                }
                resp.attack = 0;
                resp.armor = 0;
                // 攻击防御逻辑
                if (medicine.attack > 0) {
                    heroAttributes.attack += medicine.attack;
                    resp.attack = medicine.attack;
                }
                if (medicine.armor > 0) {
                    heroAttributes.armor += medicine.armor;
                    resp.armor = medicine.armor;
                }
                // 扣钱
                heroAttributes.money -= medicine.price;

                // 构建Resp
                resp.life = medicine.life;
                resp.price = medicine.price;
                resp.lifeUp = medicine.lifeUp;
                resp.name = medicine.name;
                DatabaseManager.getInstance().getMedicinesDao().update(medicine);
                save();
            }
        }
        return resp;
    }

    public BuyArmorResp buyArmor(long id) {
        HeroAttributes heroAttributes = HeroManager.getInstance().getHeroAttributes();
        BuyArmorResp resp = new BuyArmorResp();
        resp.buyStatus = BUY_ARMOR_STATUS_ERROR;

        Armors realData = DatabaseManager.getInstance().getArmorsDao().queryBuilder().where(ArmorsDao.Properties.Id.eq(id)).unique();
        // 购买逻辑
        if (realData != null) {
            // 钱够
            if (heroAttributes.money >= realData.price) {
                resp.buyStatus = BUY_ARMOR_STATUS_SUC;
                resp.price = realData.price;
                resp.armor = realData.armor;
                resp.name = realData.name;
                resp.attack = realData.attack;

                heroAttributes.money -= realData.price;

                Packages packages = new Packages();
                packages.isEquip = 1;
                packages.isSelect = 1;
                packages.type = realData.type;
                packagesDao.insert(packages);
            } else {
                resp.buyStatus = BUY_ARMOR_STATUS_FAIL;
            }
        }
        return resp;
    }

    public Observable<BaseBuy> study(String type, long price) {
        return buy(type, price);
    }

    public Observable<BaseBuy> buy(String type, long price) {
        HeroAttributes heroAttributes = HeroManager.getInstance().getHeroAttributes();
        final BaseBuy baseBuy = new BaseBuy();

        Object oj = AllGoodsToDBIdUtils.getInstance().getBlacksmithModelFromType(type);
        if (oj != null) {
            // 构建BaseBuy
            if (oj instanceof Weapons) {
                Weapons model = (Weapons) oj;
                baseBuy.name = model.name;
                baseBuy.tips = model.tips;
                baseBuy.type = model.type;
                if (price > 0) {
                    baseBuy.price = price;
                } else {
                    baseBuy.price = model.price;
                }
            } else if (oj instanceof Armors) {
                Armors model = (Armors) oj;
                baseBuy.name = model.name;
                baseBuy.tips = model.tips;
                baseBuy.type = model.type;
                if (price > 0) {
                    baseBuy.price = price;
                } else {
                    baseBuy.price = model.price;
                }
            } else if (oj instanceof Material) {
                Material model = (Material) oj;
                baseBuy.name = model.name;
                baseBuy.tips = model.tips;
                baseBuy.type = model.type;
                if (price > 0) {
                    baseBuy.price = price;
                } else {
                    baseBuy.price = model.price;
                }
            } else if (oj instanceof Skill) {
                Skill model = (Skill) oj;
                baseBuy.name = model.name;
                baseBuy.tips = model.tips;
                baseBuy.type = model.type;
                if (price > 0) {
                    baseBuy.price = price;
                } else {
                    baseBuy.price = 0;
                }
            }
            // 开始购买
            if (baseBuy.price > 0) {
                if (heroAttributes.money >= baseBuy.price) {
                    baseBuy.buyStatus = BUY_BASE_STATUS_SUC;

                    heroAttributes.money -= baseBuy.price;

                    Packages packages = new Packages();
                    packages.isEquip = 1;
                    packages.isSelect = 1;
                    packages.strengthenLevel = 0;
                    packages.type = baseBuy.type;
                    packagesDao.insert(packages);
                } else {
                    baseBuy.buyStatus = BUY_BASE_STATUS_FAIL;
                }
            }
        } else {
            baseBuy.buyStatus = BUY_BASE_STATUS_ERROR;
        }
        return Observable.create(new ObservableOnSubscribe<BaseBuy>() {
            @Override
            public void subscribe(ObservableEmitter<BaseBuy> e) throws Exception {
                e.onNext(baseBuy);
            }
        });
    }

    public BuyAttackResp buyAttack(long id) {
        HeroAttributes heroAttributes = HeroManager.getInstance().getHeroAttributes();
        BuyAttackResp resp = new BuyAttackResp();
        resp.buyStatus = BUY_ATTACK_STATUS_ERROR;

        Weapons realData = DatabaseManager.getInstance().getWeaponsDao().queryBuilder().where(WeaponsDao.Properties.Id.eq(id)).unique();
        // 购买逻辑
        if (realData != null) {
            // 钱够
            if (heroAttributes.money >= realData.price) {
                resp.buyStatus = BUY_ATTACK_STATUS_SUC;
                resp.price = realData.price;
                resp.armor = realData.armor;
                resp.attack = realData.attack;
                resp.name = realData.name;

                heroAttributes.money -= realData.price;

                Packages packages = new Packages();
                packages.isEquip = 1;
                packages.isSelect = 1;
                packages.strengthenLevel = 0;
                packages.type = realData.type;
                packagesDao.insert(packages);
            } else {
                resp.buyStatus = BUY_ATTACK_STATUS_FAIL;
            }
        }
        return resp;
    }

    public void save() {
        HeroManager.getInstance().save();
    }
}
