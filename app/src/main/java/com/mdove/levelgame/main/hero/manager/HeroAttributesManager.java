package com.mdove.levelgame.main.hero.manager;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.BigMonstersDao;
import com.mdove.levelgame.greendao.DropGoodsDao;
import com.mdove.levelgame.greendao.MaterialDao;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Accessories;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.BigMonsters;
import com.mdove.levelgame.greendao.entity.DropGoods;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Material;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.model.AttackResp;
import com.mdove.levelgame.main.monsters.manager.AdventureManager;
import com.mdove.levelgame.main.monsters.manager.SpecialMonsterManager;
import com.mdove.levelgame.model.DropGoodsModel;
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils;
import com.mdove.levelgame.utils.JsonUtil;
import com.mdove.levelgame.view.MyDialog;

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
    // 次数不够
    public static final int ATTACK_STATUS_NO_COUNT = 6;

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

    public int heroRest() {
        // 0普通休息成功；1魔王已经来了，休息失败；2休息成功但，魔王已到
        int resetStatus;
        // 如果魔王此时不是gone状态，则不能rest
        if (!goneBigMonster()) {
            resetStatus = 1;
        } else {
            resetStatus = 0;
            heroAttributes.bodyPower = 100;
            heroAttributes.days += 1;

            if (heroAttributes.days == 10 || heroAttributes.days == 30 || heroAttributes.days == 50) {
                resetStatus = 2;
            }

            // 奇遇设置
            AdventureManager.getInstance().setAdventure();
            // 特殊怪物出现设置
            SpecialMonsterManager.getInstance().setShowSpecialMonster();

            MonstersDao monstersDao = DatabaseManager.getInstance().getMonstersDao();

            // 重置所有怪物次数
            List<Monsters> data = monstersDao.loadAll();
            if (data != null && data.size() > 0) {
                for (Monsters monster : data) {
                    if (monster.isLimitCount == 1) {
                        continue;
                    } else {
                        monster.curCount = monster.limitCount;
                        monstersDao.update(monster);
                    }
                }
            }
            save();
        }
        return resetStatus;
    }

    public boolean goneBigMonster() {
        boolean isGone = true;
        if (heroAttributes.days == 10) {
            isGone = computeBigMonster(1);
        } else if (heroAttributes.days == 30) {
            // 三十天大魔王
            isGone = computeBigMonster(2);
        } else if (heroAttributes.days == 50) {
            // 五十天大魔王
            isGone = computeBigMonster(3);
        }
        return isGone;
    }

    private boolean computeBigMonster(long id) {
        BigMonsters bigMonsters = bigMonstersDao.queryBuilder().where(BigMonstersDao.Properties.Id.eq(id)).unique();
        // 如果魔王没死，说明魔王还没出现
        if (bigMonsters.isDead == 1) {
            //让大魔王出现
            bigMonsters.isGone = 1;
            bigMonstersDao.update(bigMonsters);
            return false;
        } else {
            return true;
        }
    }

    public class SellResp {
        public long pkId;
        // -1 标识需要弹dailog
        public int sellMoney;
    }

    /**
     * @param pkId
     * @param isCheck true表示需要：特殊装备弹dialog确认
     * @return
     */
    public Observable<SellResp> sellGoods(Long pkId, boolean isCheck) {
        final SellResp sellResp = new SellResp();
        sellResp.pkId = pkId;
        Packages pk = DatabaseManager.getInstance().getPackagesDao().queryBuilder().where(PackagesDao.Properties.Id.eq(pkId)).unique();
        int money = 0;
        if (pk != null) {
            Object attack = AllGoodsToDBIdUtils.getInstance().getObjectFromType(pk.type);
            if (attack != null && attack instanceof Weapons) {
                Weapons weapons = (Weapons) attack;
                money = (int) (weapons.price / 2);
                heroAttributes.money += money;
                // 此时表示装备特殊，弹dialog
                if (weapons.isSpecial == 0 && isCheck) {
                    sellResp.sellMoney = -1;
                    return Observable.create(new ObservableOnSubscribe<SellResp>() {
                        @Override
                        public void subscribe(ObservableEmitter<SellResp> e) throws Exception {
                            e.onNext(sellResp);
                        }
                    });
                }
            } else if (attack != null && attack instanceof Armors) {
                Armors armors = (Armors) attack;
                money = (int) (armors.price / 2);
                heroAttributes.money += money;
            } else if (attack != null && attack instanceof Material) {
                Material material = (Material) attack;
                money = (int) (material.price / 2);
                heroAttributes.money += money;
            }
            DatabaseManager.getInstance().getPackagesDao().delete(pk);
            save();
        }
        sellResp.sellMoney = money;
        return Observable.create(new ObservableOnSubscribe<SellResp>() {
            @Override
            public void subscribe(ObservableEmitter<SellResp> e) throws Exception {
                e.onNext(sellResp);
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
            // 地方对我们造成的伤害 = 敌方攻击 - 我方防御
            int realAttackMonster = (monsters.attack - heroAttributes.armor) * attackCount;
            int realHarm = 0;
            // realAttackMonster<0，说明敌方无法破防，realHarm = 0
            if (realAttackMonster > 0) {
                // 敌方需要攻击几次
                int monstersAttackCount = heroAttributes.curLife / realAttackMonster;
                // 如果我方攻击次数少于敌方（并且attackCount>0），则生命降低为：realAttackMonster * attackCount
                if (attackCount <= monstersAttackCount && attackCount > 0) {
                    realHarm = realAttackMonster * attackCount;
                }
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

    public void saveMoney(long money) {
        heroAttributes.money += money;
        save();
    }

    public void heroLevel(long exp) {
        long levelExp = getLevelExp(heroAttributes);
        if (heroAttributes.experience + exp >= levelExp) {
            heroAttributes.level += 1;
            heroAttributes.experience = (heroAttributes.experience + exp) - levelExp;

            heroAttributes.attack += heroAttributes.attackIncrease;
            heroAttributes.armor += heroAttributes.armorIncrease;
            heroAttributes.curLife += heroAttributes.lifeIncrease;
            heroAttributes.maxLife += heroAttributes.lifeIncrease;
        } else {
            heroAttributes.experience += exp;
        }
        save();
    }

    // 判断体力是否够用，够用直接减少
    public boolean computePowerIsHas(long usePower) {
        if (heroAttributes.bodyPower - usePower < 0) {
            return false;
        } else {
            heroAttributes.bodyPower -= usePower;
            return true;
        }
    }

    // 攻击次数判断
    public boolean computeLimitConut(Monsters monsters) {
        boolean isCan = true;
        if (monsters != null) {
            if (monsters.isLimitCount == 0) {
                if (monsters.curCount == 0) {
                    isCan = false;
                }
            }
        } else {
            isCan = false;
        }
        return isCan;
    }

    // 当前血量判断
    public boolean computeCurLife() {
        boolean isCan = true;
        if (heroAttributes.curLife <= 0) {
            isCan = false;
        }
        return isCan;
    }

    public AttackResp attack(long monstersId) {
        AttackResp attackResp = new AttackResp();
        int attackStatus = ATTACK_STATUS_ERROR;
        Monsters monsters = DatabaseManager.getInstance().getMonstersDao().queryBuilder().where(MonstersDao.Properties.Id.eq(monstersId)).unique();

        // 体力判断
        if (monsters != null) {
            if (heroAttributes.bodyPower - monsters.consumePower < 0) {
                attackStatus = ATTACK_STATUS_NO_POWER;
                // 体力不足，直接置为null，跳过后续逻辑
                monsters = null;
            } else {
                heroAttributes.bodyPower -= monsters.consumePower;
            }
        }

        // 次数判断
        if (monsters != null) {
            if (monsters.isLimitCount == 0) {
                if (monsters.curCount == 0) {
                    attackStatus = ATTACK_STATUS_NO_COUNT;
                    // 体力不足，直接置为null，跳过后续逻辑
                    monsters = null;
                }
            }
        }

        if (monsters != null) {
            // 对敌方造成伤害 = 我方攻击 - 敌方防御
            int realAttack = heroAttributes.attack - monsters.armor;
            // 无法破防
            if (realAttack <= 0) {
                attackResp.attackStatus = ATTACK_STATUS_FAIL;
                return attackResp;
            }
            // 需要攻击几次(attackCount==0,说明秒杀对手)
            int attackCount = monsters.life / realAttack;

            // 敌方对我方造成伤害 = 敌方攻击 - 我方防御
            int realAttackMonster = monsters.attack - heroAttributes.armor;
            int realHarm = 0;
            // realAttackMonster<0，说明敌方无法破防，realHarm = 0
            if (realAttackMonster > 0) {
                // 敌方需要攻击几次
                int monstersAttackCount = heroAttributes.curLife / realAttackMonster;
                // 总共对我方造成伤害
                // 如果我方攻击次数少于敌方（并且attackCount>0），则生命降低为：realAttackMonster * attackCount
                if (attackCount <= monstersAttackCount && attackCount > 0) {
                    realHarm = realAttackMonster * attackCount;
                }
                if (monstersAttackCount < attackCount) {
                    attackStatus = ATTACK_STATUS_FAIL;
                    attackResp.attackStatus = attackStatus;
                    return attackResp;
                }
            }

            // 战斗胜利
            if (heroAttributes.curLife - realHarm > 0) {
                attackStatus = ATTACK_STATUS_WIN;

                // 减少次数
                monsters.curCount -= 1;
                DatabaseManager.getInstance().getMonstersDao().update(monsters);
                attackResp.monsterId = monstersId;
                attackResp.curCount = monsters.curCount;

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
                        packages.isSelect = 1;
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
                        packages.isSelect = 1;
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
                        packages.isSelect = 1;
                        packages.type = material.type;
                        DatabaseManager.getInstance().getPackagesDao().insert(packages);
                        name = material.name;
                    }
                    break;
                }
                default:
                    break;
            }
        }
        return name;
    }

    public void takeOffAttack(long strengthen, Weapons weapons) {
        heroAttributes.attack -= (1 + strengthen * 0.2) * (weapons.attack);
        heroAttributes.armor -= (1 + strengthen * 0.2) * weapons.armor;
        save();
    }

    public void holdOnAttack(long strengthen, Weapons weapons) {
        heroAttributes.attack += (1 + strengthen * 0.2) * (weapons.attack);
        heroAttributes.armor += (1 + strengthen * 0.2) * (weapons.armor);
        save();
    }

    public void takeOffArmor(long strengthen, Armors armors) {
        heroAttributes.attack -= (1 + strengthen * 0.2) * (armors.attack);
        heroAttributes.armor -= (1 + strengthen * 0.2) * (armors.armor);
        save();
    }

    public void holdOnArmor(long strengthen, Armors armors) {
        heroAttributes.attack += (1 + strengthen * 0.2) * (armors.attack);
        heroAttributes.armor += (1 + strengthen * 0.2) * (armors.armor);
        save();
    }

    public void takeOffAccessories(long strengthen, Accessories accessories) {
        heroAttributes.attack -= (1 + strengthen * 0.2) * (accessories.attack);
        heroAttributes.armor -= (1 + strengthen * 0.2) * (accessories.armor);
        heroAttributes.maxLife -= (1 + strengthen * 0.2) * (accessories.life);
        heroAttributes.curLife -= (1 + strengthen * 0.2) * (accessories.life);
        save();
    }

    public void holdOnAccessories(long strengthen, Accessories accessories) {
        heroAttributes.attack += (1 + strengthen * 0.2) * (accessories.attack);
        heroAttributes.armor += (1 + strengthen * 0.2) * (accessories.armor);
        heroAttributes.maxLife += (1 + strengthen * 0.2) * (accessories.life);
        heroAttributes.curLife += (1 + strengthen * 0.2) * (accessories.life);
        save();
    }

    public String formatAttributesString() {
        String content = "";
        content += String.format(App.getAppContext().getString(R.string.hero_attributes_msg_attack), heroAttributes.attack, heroAttributes.attackIncrease) + "<br/>";
        content += String.format(App.getAppContext().getString(R.string.hero_attributes_msg_armor), heroAttributes.armor, heroAttributes.armorIncrease) + "<br/>";
        content += String.format(App.getAppContext().getString(R.string.hero_attributes_msg_life), heroAttributes.curLife, heroAttributes.maxLife, heroAttributes.lifeIncrease) + "<br/>";
        content += String.format(App.getAppContext().getString(R.string.hero_attributes_msg_money), heroAttributes.money) + "<br/>";
        content += String.format(App.getAppContext().getString(R.string.hero_attributes_msg_level), heroAttributes.level) + "<br/>";
        content += String.format(App.getAppContext().getString(R.string.hero_attributes_msg_days), heroAttributes.days) + "<br/>";
        content += String.format(App.getAppContext().getString(R.string.hero_attributes_msg_need_exp), heroAttributes.experience, getLevelExp(heroAttributes)) + "<br/>";
        content += String.format(App.getAppContext().getString(R.string.hero_attributes_msg_body_power), heroAttributes.bodyPower, 100);
        return content;
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
