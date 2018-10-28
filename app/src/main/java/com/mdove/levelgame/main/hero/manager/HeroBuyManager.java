package com.mdove.levelgame.main.hero.manager;

import com.mdove.levelgame.App;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Medicines;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.model.BuyArmorResp;
import com.mdove.levelgame.main.hero.model.BuyAttackResp;
import com.mdove.levelgame.main.hero.model.BuyMedicinesResp;
import com.mdove.levelgame.main.shop.model.ShopArmorModel;
import com.mdove.levelgame.main.shop.model.ShopAttackModel;

import java.util.List;

/**
 * Created by MDove on 2018/10/20.
 */

public class HeroBuyManager {
    public static final int BUY_MEDICINES_STATUS_SUC = 1;
    // 没钱购买失败
    public static final int BUY_MEDICINES_STATUS_FAIL = 2;
    public static final int BUY_MEDICINES_STATUS_ERROR = 3;

    public static final int BUY_ATTACK_STATUS_SUC = 4;
    public static final int BUY_ATTACK_STATUS_FAIL = 5;
    public static final int BUY_ATTACK_STATUS_ERROR = 6;

    public static final int BUY_ARMOR_STATUS_SUC = 7;
    public static final int BUY_ARMOR_STATUS_FAIL = 8;
    public static final int BUY_ARMOR_STATUS_ERROR = 9;

    private HeroAttributes heroAttributes;
    private PackagesDao packagesDao;

    private static class SingletonHolder {
        static final HeroBuyManager INSTANCE = new HeroBuyManager();
    }

    private HeroBuyManager() {
        heroAttributes = HeroManager.getInstance().getHeroAttributes();
        packagesDao = DatabaseManager.getInstance().getPackagesDao();
    }

    public static HeroBuyManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public BuyMedicinesResp buyMedicines(long id) {
        BuyMedicinesResp resp = new BuyMedicinesResp();
        resp.buyStatus = BUY_MEDICINES_STATUS_ERROR;
        Medicines medicine = null;
        List<Medicines> medicines = InitDataFileUtils.getInitMedicines();
        for (Medicines model : medicines) {
            if (model.id == id) {
                medicine = model;
            }
        }
        // 没钱
        if (heroAttributes.money - medicine.price < 0) {
            resp.buyStatus = BUY_MEDICINES_STATUS_FAIL;
        } else {// 购买成功
            resp.buyStatus = BUY_MEDICINES_STATUS_SUC;
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
            // 扣钱
            heroAttributes.money -= medicine.price;

            // 构建Resp
            resp.life = medicine.life;
            resp.price = medicine.price;
            resp.lifeUp = medicine.lifeUp;
        }
        return resp;
    }

    public BuyArmorResp buyArmor(long id) {
        BuyArmorResp resp = new BuyArmorResp();
        resp.buyStatus = BUY_ARMOR_STATUS_ERROR;

        List<ShopArmorModel> data = InitDataFileUtils.getShopArmors();
        ShopArmorModel realData = null;
        for (ShopArmorModel model : data) {
            if (model.id == id) {
                realData = model;
                break;
            }
        }
        // 购买逻辑
        if (realData != null) {
            // 钱够
            if (heroAttributes.money >= realData.price) {
                resp.buyStatus = BUY_ARMOR_STATUS_SUC;
                resp.price = realData.price;
                resp.armor = realData.armor;
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

    public BuyAttackResp buyAttack(long id) {
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
