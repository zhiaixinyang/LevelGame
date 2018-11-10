package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by MDove on 2018/11/10.
 */
@Entity
public class Skill {
    @Id
    public Long id;
    public String name;
    public String tips;
    public String type;
    // 暴击倍数，如：1.2
    public long attackHeavy;
    // 暴击几率 如：02
    public float attackHeavyProbability;
    // 吸血率 如：0.2
    public float bloodSuckProbability;
    // 无视防御（%） 如：0.2
    public float ignoreArmorProbability;
    // 无视攻击（%） 如：0.2
    public float ignoreAttackProbability;
    // 真实额外伤害
    public long realAttack;
    // 眩晕 如：1200 毫秒
    public long dizziness;
    // 眩晕几率 如：0.2（应对被动）
    public float dizzinessProbability;
}
