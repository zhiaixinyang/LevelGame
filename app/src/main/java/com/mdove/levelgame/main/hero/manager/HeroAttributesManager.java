package com.mdove.levelgame.main.hero.manager;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.config.AppConfig;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.BigMonstersDao;
import com.mdove.levelgame.greendao.DropGoodsDao;
import com.mdove.levelgame.greendao.MaterialDao;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.BigMonsters;
import com.mdove.levelgame.greendao.entity.DropGoods;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Material;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.model.AttackResp;
import com.mdove.levelgame.main.monsters.model.MonstersModel;
import com.mdove.levelgame.model.DropGoodsModel;
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils;
import com.mdove.levelgame.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by MDove on 2018/10/20.
 */

public class HeroAttributesManager {
    private BigMonstersDao bigMonstersDao;
    // 胜利，但没掉落装备
    public static final int ATTACK_STATUS_WIN = 1;
    // 胜利，掉落装备
    public static final int ATTACK_STATUS_HAS_DROP_GOODS = 5;
    // 打不过
    public static final int ATTACK_STATUS_FAIL = 2;
    // 异常
    public static final int ATTACK_STATUS_ERROR = 3;
    // 体力不足
    public static final int ATTACK_STATUS_NO_POWER = 4;

    private HeroAttributes heroAttributes;

    private static class SingletonHolder {
        static final HeroAttributesManager INSTANCE = new HeroAttributesManager();
    }

    public static HeroAttributesManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private HeroAttributesManager() {
        heroAttributes = HeroManager.getInstance().getHeroAttributes();
        bigMonstersDao = DatabaseManager.getInstance().getBigMonstersDao();
    }

    public boolean heroRest() {
        boolean isCanRest;
        // 第十天大魔王
        if (heroAttributes.days == 10) {
            isCanRest = computeBigMonster(1);
        } else if (heroAttributes.days == 30) {
            // 三十天大魔王
            isCanRest = computeBigMonster(2);
        } else if (heroAttributes.days == 50) {
            // 五十天大魔王
            isCanRest = computeBigMonster(3);
        } else {
            isCanRest = true;
            heroAttributes.bodyPower = 100;
            heroAttributes.days += 1;
        }
        save();
        return isCanRest;
    }

    private boolean computeBigMonster(long id) {
        BigMonsters bigMonsters = bigMonstersDao.queryBuilder().where(BigMonstersDao.Properties.Id.eq(id)).unique();
        // 如果魔王没死，说明魔王还没出现（否则正常rest）
        if (bigMonsters.isDead == 1) {
            //让大魔王出现
            bigMonsters.isGone = 1;
            bigMonstersDao.update(bigMonsters);
            return false;
        } else {
            heroAttributes.bodyPower = 100;
            heroAttributes.days += 1;
            return true;
        }
    }

    public Observable<Integer> sellGoods(Long pkId) {
        Packages pk = DatabaseManager.getInstance().getPackagesDao().queryBuilder().where(PackagesDao.Properties.Id.eq(pkId)).unique();
        int money = 0;
        if (pk != null) {
            Object attack = AllGoodsToDBIdUtils.getInstance().getObjectFromType(pk.type);
            if (attack != null && attack instanceof Weapons) {
                Weapons weapons = (Weapons) attack;
                money = (int) (weapons.price / 2);
                heroAttributes.money += money;
            } else if (attack != null && attack instanceof Armors) {
                Armors armors = (Armors) attack;
                money = (int) (armors.price / 2);
                heroAttributes.money += money;
            } else if (attack != null && attack instanceof Material) {
                money = 50;
                heroAttributes.money += money;
            }
            DatabaseManager.getInstance().getPackagesDao().delete(pk);
            save();
        }
        final int finalMoney = money;
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(Integer.valueOf(finalMoney));
            }
        });
    }

    public AttackResp attackBigMonsters(long bigMonstersId) {
        AttackResp attackResp = new AttackResp();
        int attackStatus = ATTACK_STATUS_ERROR;

        BigMonsters monsters = bigMonstersDao.queryBuilder().where(BigMonstersDao.Properties.Id.eq(bigMonstersId)).unique();
        if (monsters != null) {
            // 对敌方造成伤害 = 我方攻击 - 敌方防御
            int realAttack = heroAttributes.attack - monsters.armor;
            // 无法破防
            if (realAttack <= 0) {
                attackStatus = ATTACK_STATUS_FAIL;
                attackResp.attackStatus = attackStatus;
                return attackResp;
            }
            // 我方需要攻击几次
            int attackCount = monsters.life / realAttack;// 一刀秒的情况下，此时为0
            // 我方消耗生命 = （敌方攻击 - 我方防御）*attackCount
            int realHarm = (monsters.attack - heroAttributes.armor) * attackCount;
            // 如果无法破防，需要置为0，否则出现负数
            if (realHarm <= 0) {
                realHarm = 0;
            } else {
                // 敌方需要攻击几次
                int monstersAttackCount = heroAttributes.curLife / realHarm;
                if (monstersAttackCount < attackCount) {
                    attackStatus = ATTACK_STATUS_FAIL;
                    attackResp.attackStatus = attackStatus;
                    return attackResp;
                }
            }

            // 战斗胜利
            if (heroAttributes.curLife - realHarm > 0) {
                attackStatus = ATTACK_STATUS_WIN;
                monsters.isDead = 0;
                monsters.isGone = 0;
                bigMonstersDao.update(monsters);
                // 刷新数据
                heroAttributes.curLife -= realHarm;
                // 判断是否需要升级
                long levelExp = getLevelExp(heroAttributes);
                if (heroAttributes.experience + monsters.exp >= levelExp) {
                    heroAttributes.level += 1;
                    heroAttributes.experience = (heroAttributes.experience + monsters.exp) - levelExp;

                    heroAttributes.attack += heroAttributes.attackIncrease;
                    heroAttributes.armor += heroAttributes.armorIncrease;
                    heroAttributes.curLife += heroAttributes.lifeIncrease;
                    heroAttributes.maxLife += heroAttributes.lifeIncrease;
                } else {
                    heroAttributes.experience += monsters.exp;
                }
                heroAttributes.money += monsters.money;
                save();
                attackResp.exp = (int) monsters.exp;
                attackResp.life = realHarm;
                attackResp.money = monsters.money;
            } else {
                attackStatus = ATTACK_STATUS_FAIL;
            }
        }
        attackResp.attackStatus = attackStatus;
        return attackResp;
    }

    public AttackResp attack(long monstersId) {
        AttackResp attackResp = new AttackResp();
        int attackStatus = ATTACK_STATUS_ERROR;
        Monsters monsters = DatabaseManager.getInstance().getMonstersDao().queryBuilder().where(MonstersDao.Properties.Id.eq(monstersId)).unique();
        if (monsters != null) {
            if (heroAttributes.bodyPower - monsters.consumePower < 0) {
                attackStatus = ATTACK_STATUS_NO_POWER;
                // 体力不足，直接置为null，跳过后续逻辑
                monsters = null;
            } else {
                heroAttributes.bodyPower -= monsters.consumePower;
            }
        }

        if (monsters != null) {
            // 对敌方造成伤害 = 我方攻击 - 敌方防御
            int realAttack = heroAttributes.attack - monsters.armor;
            // 无法破防
            if (realAttack <= 0) {
                attackStatus = ATTACK_STATUS_FAIL;
                attackResp.attackStatus = attackStatus;
                return attackResp;
            }
            // 需要攻击几次
            int attackCount = monsters.life / realAttack;
            // 我方消耗生命 = （敌方攻击 - 我方防御）* attackCount
            int realHarm = (monsters.attack - heroAttributes.armor) * attackCount;
            // 如果无法破防，需要置为0，否则出现负数
            if (realHarm <= 0) {
                realHarm = 0;
            } else {
                // 敌方需要攻击几次
                int monstersAttackCount = heroAttributes.curLife / realHarm;
                if (monstersAttackCount < attackCount) {
                    attackStatus = ATTACK_STATUS_FAIL;
                    attackResp.attackStatus = attackStatus;
                    return attackResp;
                }
            }

            // 战斗胜利
            if (heroAttributes.curLife - realHarm > 0) {
                attackStatus = ATTACK_STATUS_WIN;
                attackResp.dropGoods = dropGoods(monsters.dropGoodsId);
                if (attackResp.dropGoods.size() > 0) {
                    attackStatus = ATTACK_STATUS_HAS_DROP_GOODS;
                }
                // 刷新数据
                heroAttributes.curLife -= realHarm;
                // 判断是否需要升级
                long levelExp = getLevelExp(heroAttributes);
                if (heroAttributes.experience + monsters.exp >= levelExp) {
                    heroAttributes.level += 1;
                    heroAttributes.experience = (heroAttributes.experience + monsters.exp) - levelExp;

                    heroAttributes.attack += heroAttributes.attackIncrease;
                    heroAttributes.armor += heroAttributes.armorIncrease;
                    heroAttributes.curLife += heroAttributes.lifeIncrease;
                    heroAttributes.maxLife += heroAttributes.lifeIncrease;
                } else {
                    heroAttributes.experience += monsters.exp;
                }
                heroAttributes.money += monsters.money;
                save();
                attackResp.exp = (int) monsters.exp;
                attackResp.life = realHarm;
                attackResp.money = monsters.money;
            } else {
                attackStatus = ATTACK_STATUS_FAIL;
            }
        }
        attackResp.attackStatus = attackStatus;
        return attackResp;
    }

    // 爆装备计算
    private List<String> dropGoods(Long dropGoodsId) {
        List<String> data = new ArrayList<>();
        DropGoods dropGoods = DatabaseManager.getInstance().getDropGoodsDao().queryBuilder().where(DropGoodsDao.Properties.Id.eq(dropGoodsId)).unique();
        if (dropGoods != null) {
            List<DropGoodsModel> dropGoodsList = JsonUtil.decode(dropGoods.types, new TypeToken<List<DropGoodsModel>>() {
            }.getType());
            if (dropGoodsList != null && dropGoodsList.size() > 0) {
                for (DropGoodsModel model : dropGoodsList) {
                    String name = getDropGoodName(model);
                    if (!TextUtils.isEmpty(name)) {
                        data.add(name);
                    }
                }
            }
        }
        return data;
    }

    // 保存掉落装备，并返回装备名
    private String getDropGoodName(DropGoodsModel model) {
        String name = null;
        float probability = model.probability * 100;
        int random = new Random(System.currentTimeMillis()).nextInt(100);
        if (random <= probability) {
            switch (AllGoodsToDBIdUtils.getInstance().getDBType(model.type)) {
                case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK: {
                    Weapons weapons = DatabaseManager.getInstance().getWeaponsDao().queryBuilder().where(WeaponsDao.Properties.Type.eq(model.type)).unique();
                    if (weapons != null) {
                        Packages packages = new Packages();
                        packages.isEquip = 1;
                        packages.type = weapons.type;
                        DatabaseManager.getInstance().getPackagesDao().insert(packages);
                        name = weapons.name;
                    }
                    break;
                }
                case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR: {
                    Armors armors = DatabaseManager.getInstance().getArmorsDao().queryBuilder().where(ArmorsDao.Properties.Type.eq(model.type)).unique();
                    if (armors != null) {
                        Packages packages = new Packages();
                        packages.isEquip = 1;
                        packages.type = armors.type;
                        DatabaseManager.getInstance().getPackagesDao().insert(packages);
                        name = armors.name;
                    }
                    break;
                }
                case AllGoodsToDBIdUtils.DB_TYPE_IS_MATERIALS: {
                    Material material = DatabaseManager.getInstance().getMaterialDao().queryBuilder().where(MaterialDao.Properties.Type.eq(model.type)).unique();
                    if (material != null) {
                        Packages packages = new Packages();
                        packages.isEquip = 1;
                        packages.type = material.type;
                        DatabaseManager.getInstance().getPackagesDao().insert(packages);
                        name = material.name;
                    }
                    break;
                }
            }
        }
        return name;
    }

    public void takeOffAttack(Weapons weapons) {
        heroAttributes.attack -= weapons.attack;
        heroAttributes.armor -= weapons.armor;
        save();
    }

    public void holdOnAttack(Weapons weapons) {
        heroAttributes.attack += weapons.attack;
        heroAttributes.armor += weapons.armor;
        save();
    }

    public void takeOffArmor(Armors armors) {
        heroAttributes.attack -= armors.attack;
        heroAttributes.armor -= armors.armor;
        save();
    }

    public void holdOnArmor(Armors armors) {
        heroAttributes.attack += armors.attack;
        heroAttributes.armor += armors.armor;
        save();
    }

    private long getLevelExp(HeroAttributes heroAttributes) {
        long levelExp = heroAttributes.baseExp;
        if (heroAttributes.level > 1) {
            levelExp = heroAttributes.level * heroAttributes.expMultiple * heroAttributes.baseExp;
        }
        return levelExp;
    }

    public void save() {
        HeroManager.getInstance().save();
    }
}
