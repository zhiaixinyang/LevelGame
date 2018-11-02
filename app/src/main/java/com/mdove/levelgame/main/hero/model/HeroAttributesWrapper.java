package com.mdove.levelgame.main.hero.model;

import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;

/**
 * @author MDove on 2018/11/2
 */
public class HeroAttributesWrapper {
    public Long id;
    private int attack;
    private int armor;
    private int curLife;
    private int maxLife;
    // 攻击速度，毫秒级（对应Rx的弹射间隔，此值越来越低）
    public long attackSpeed;

    private static class SingletonHolder {
        static final HeroAttributesWrapper INSTANCE = new HeroAttributesWrapper();
    }

    public static HeroAttributesWrapper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private HeroAttributesWrapper() {
        resetAttributes();
    }

    // 获取最新的Attributes值
    private HeroAttributes resetAttributes() {
        HeroAttributes heroAttributes = HeroManager.getInstance().getHeroAttributes();
        resetAttributes(heroAttributes);
        return heroAttributes;
    }

    private void resetAttributes(HeroAttributes heroAttributes) {
        id = heroAttributes.id;
        attack = heroAttributes.attack;
        armor = heroAttributes.armor;
        curLife = heroAttributes.curLife;
        maxLife = heroAttributes.maxLife;
        attackSpeed = heroAttributes.attackSpeed;
    }

    // 为以后增加技能/特殊属性做准备
    public int realAttack() {
        resetAttributes();
        return attack;
    }

    // 计算伤害并更新数据库
    public int computeHarmLief(int enemyAttack) {
        HeroAttributes heroAttributes = resetAttributes();
        int harm = enemyAttack - armor;
        heroAttributes.curLife -= harm;
        HeroManager.getInstance().save();
        curLife = heroAttributes.curLife;
        return harm;
    }

    // 为以后增加技能/特殊属性做准备
    public int realArmor() {
        resetAttributes();
        return armor;
    }
}
