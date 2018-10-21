package com.mdove.levelgame.main.hero;

import com.mdove.levelgame.App;
import com.mdove.levelgame.greendao.HeroAttributesDao;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Medicines;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.model.AttackResp;
import com.mdove.levelgame.main.hero.model.BuyMedicinesResp;
import com.mdove.levelgame.main.monsters.model.MonstersModel;

import java.util.List;

/**
 * Created by MDove on 2018/10/20.
 */

public class HeroAttributesManager {
    public static final int ATTACK_STATUS_WIN = 1;
    public static final int ATTACK_STATUS_FAIL = 2;
    public static final int ATTACK_STATUS_ERROR = 3;

    public static final int BUY_MEDICINES_STATUS_SUC = 1;
    // 没钱购买失败
    public static final int BUY_MEDICINES_STATUS_FAIL = 2;
    public static final int BUY_MEDICINES_STATUS_ERROR = 3;

    private HeroAttributes heroAttributes;
    private HeroAttributesDao heroAttributesDao;

    private static class SingletonHolder {
        static final HeroAttributesManager INSTANCE = new HeroAttributesManager();
    }

    private HeroAttributesManager() {
        heroAttributesDao = App.getDaoSession().getHeroAttributesDao();
    }

    public HeroAttributes getHeroAttributes() {
        return heroAttributes;
    }

    public void setHeroAttributes(HeroAttributes model) {
        heroAttributes = model;
    }

    public static HeroAttributesManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public AttackResp attack(long monstersId) {
        AttackResp attackResp = new AttackResp();
        int attackStatus = ATTACK_STATUS_ERROR;
        List<MonstersModel> monsters = InitDataFileUtils.getInitMonsters();
        MonstersModel realModel = null;
        for (MonstersModel model : monsters) {
            if (model.id == monstersId) {
                realModel = model;
            }
        }

        if (realModel != null) {
            // 对敌方造成伤害 = 我方攻击 - 敌方防御
            int realAttack = heroAttributes.attack - realModel.armor;
            // 需要攻击几次
            int attackCount = realModel.life / realAttack;
            // 我方消耗生命 = （敌方攻击 - 我方防御）*attackCount
            int realHarm = (realModel.attack - heroAttributes.armor) * attackCount;
            // 战斗胜利
            if (heroAttributes.curLife - realHarm > 0) {
                attackStatus = ATTACK_STATUS_WIN;
                // 刷新数据
                heroAttributes.curLife -= realHarm;
                // 判断是否需要升级
                long levelExp = getLevelExp(heroAttributes);
                if (heroAttributes.experience + realModel.exp >= levelExp) {
                    heroAttributes.level += 1;
                    heroAttributes.experience = (heroAttributes.experience + realModel.exp) - levelExp;

                    heroAttributes.attack += heroAttributes.attackIncrease;
                    heroAttributes.armor += heroAttributes.armorIncrease;
                    heroAttributes.curLife += heroAttributes.lifeIncrease;
                    heroAttributes.maxLife += heroAttributes.lifeIncrease;
                } else {
                    heroAttributes.experience += realModel.exp;
                }
                heroAttributes.money += realModel.money;
                save();
                attackResp.exp = (int) realModel.exp;
                attackResp.life = realHarm;
                attackResp.money = realModel.money;
            } else {
                attackStatus = ATTACK_STATUS_FAIL;
            }
        }
        attackResp.attackStatus = attackStatus;
        return attackResp;
    }

    public BuyMedicinesResp buyMedicines(long id) {
        BuyMedicinesResp resp = new BuyMedicinesResp();
        resp.buyStatus = BUY_MEDICINES_STATUS_ERROR;
        Medicines realModel = null;
        List<Medicines> medicines = InitDataFileUtils.getInitMedicines();
        for (Medicines model : medicines) {
            if (model.id == id) {
                realModel = model;
            }
        }
        // 没钱
        if (heroAttributes.money - realModel.price < 0) {
            resp.buyStatus = BUY_MEDICINES_STATUS_FAIL;
        } else {// 购买成功
            resp.buyStatus = BUY_MEDICINES_STATUS_SUC;
            // 当前生命超出上限，舍弃
            if (heroAttributes.curLife + realModel.life > heroAttributes.maxLife) {
                heroAttributes.curLife = heroAttributes.maxLife;
            } else {
                heroAttributes.curLife += realModel.life;
            }
            // 扣钱
            heroAttributes.money -= realModel.price;

            // 构建Resp
            resp.life = realModel.life;
            resp.price = realModel.price;
        }

        return resp;
    }

    private long getLevelExp(HeroAttributes heroAttributes) {
        long levelExp = heroAttributes.baseExp;
        if (heroAttributes.level > 1) {
            levelExp = heroAttributes.level * heroAttributes.expMultiple * heroAttributes.baseExp;
        }
        return levelExp;
    }

    public void save() {
        heroAttributesDao.update(heroAttributes);
    }
}
