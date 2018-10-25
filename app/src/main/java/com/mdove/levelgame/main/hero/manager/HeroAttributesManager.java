package com.mdove.levelgame.main.hero.manager;

import com.mdove.levelgame.config.AppConfig;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.model.AttackResp;
import com.mdove.levelgame.main.monsters.model.MonstersModel;

import java.util.List;

/**
 * Created by MDove on 2018/10/20.
 */

public class HeroAttributesManager {
    public static final int ATTACK_STATUS_WIN = 1;
    public static final int ATTACK_STATUS_FAIL = 2;
    public static final int ATTACK_STATUS_ERROR = 3;

    private HeroAttributes heroAttributes;

    private static class SingletonHolder {
        static final HeroAttributesManager INSTANCE = new HeroAttributesManager();
    }

    public static HeroAttributesManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private HeroAttributesManager() {
        heroAttributes = HeroManager.getInstance().getHeroAttributes();
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
