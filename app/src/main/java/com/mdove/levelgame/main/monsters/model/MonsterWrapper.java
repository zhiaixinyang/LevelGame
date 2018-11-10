package com.mdove.levelgame.main.monsters.model;

import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.main.hero.model.HeroAttributesWrapper;

/**
 * @author MDove on 2018/11/2
 */
public class MonsterWrapper {
    private Monsters monsters;
    public Long id;
    private int attack;
    private int armor;
    private int curLife;
    private int maxLife;
    // 攻击速度，毫秒级（对应Rx的弹射间隔，此值越来越低）
    public long attackSpeed = 1500;
    public int isBusinessman = 1;
    private HeroAttributesWrapper wrapper;

    public MonsterWrapper(Monsters monsters) {
        this.monsters = monsters;
        attack = monsters.attack;
        armor = monsters.armor;
        curLife = monsters.life;
        maxLife = monsters.life;
        this.isBusinessman = monsters.isBusinessman;
        wrapper = HeroAttributesWrapper.getInstance();
    }

    // 为以后增加技能/特殊属性做准备
    public int realAttack() {
        // 减去英雄忽视的攻击
        int realAttack;
        realAttack = (int) ((1 - wrapper.getInnerSkillModel().ignoreAttackProbability) * attack);
        return realAttack;
    }

    public int computeHarmLife() {
        int harm;
        harm = computeHarmLife(wrapper.realAttack());
        return harm;
    }

    // 计算伤害并更新数据库
    public int computeHarmLife(int heroAttack) {
        int harm = heroAttack - realArmor();
        if (harm < 0) {
            harm = 0;
        }
        curLife -= harm;
        // 此时代表怪物已死
        if (curLife <= 0) {
            curLife = 0;
        }
        return harm;
    }

    public int realCurLife() {
        return curLife;
    }

    // 为以后增加技能/特殊属性做准备
    public int realArmor() {
        // 减去英雄忽视的护甲
        int realArmor;
        realArmor = (int) ((1 - wrapper.getInnerSkillModel().ignoreArmorProbability) * armor);
        return realArmor;
    }
}
