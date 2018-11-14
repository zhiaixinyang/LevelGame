package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

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
    public float attackHeavy;
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
    public String belongType;
    public int needSkillCount;
    @Generated(hash = 481896070)
    public Skill(Long id, String name, String tips, String type, float attackHeavy,
            float attackHeavyProbability, float bloodSuckProbability,
            float ignoreArmorProbability, float ignoreAttackProbability,
            long realAttack, long dizziness, float dizzinessProbability,
            String belongType, int needSkillCount) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.type = type;
        this.attackHeavy = attackHeavy;
        this.attackHeavyProbability = attackHeavyProbability;
        this.bloodSuckProbability = bloodSuckProbability;
        this.ignoreArmorProbability = ignoreArmorProbability;
        this.ignoreAttackProbability = ignoreAttackProbability;
        this.realAttack = realAttack;
        this.dizziness = dizziness;
        this.dizzinessProbability = dizzinessProbability;
        this.belongType = belongType;
        this.needSkillCount = needSkillCount;
    }
    @Generated(hash = 1993658816)
    public Skill() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTips() {
        return this.tips;
    }
    public void setTips(String tips) {
        this.tips = tips;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public float getAttackHeavy() {
        return this.attackHeavy;
    }
    public void setAttackHeavy(float attackHeavy) {
        this.attackHeavy = attackHeavy;
    }
    public float getAttackHeavyProbability() {
        return this.attackHeavyProbability;
    }
    public void setAttackHeavyProbability(float attackHeavyProbability) {
        this.attackHeavyProbability = attackHeavyProbability;
    }
    public float getBloodSuckProbability() {
        return this.bloodSuckProbability;
    }
    public void setBloodSuckProbability(float bloodSuckProbability) {
        this.bloodSuckProbability = bloodSuckProbability;
    }
    public float getIgnoreArmorProbability() {
        return this.ignoreArmorProbability;
    }
    public void setIgnoreArmorProbability(float ignoreArmorProbability) {
        this.ignoreArmorProbability = ignoreArmorProbability;
    }
    public float getIgnoreAttackProbability() {
        return this.ignoreAttackProbability;
    }
    public void setIgnoreAttackProbability(float ignoreAttackProbability) {
        this.ignoreAttackProbability = ignoreAttackProbability;
    }
    public long getRealAttack() {
        return this.realAttack;
    }
    public void setRealAttack(long realAttack) {
        this.realAttack = realAttack;
    }
    public long getDizziness() {
        return this.dizziness;
    }
    public void setDizziness(long dizziness) {
        this.dizziness = dizziness;
    }
    public float getDizzinessProbability() {
        return this.dizzinessProbability;
    }
    public void setDizzinessProbability(float dizzinessProbability) {
        this.dizzinessProbability = dizzinessProbability;
    }
    public String getBelongType() {
        return this.belongType;
    }
    public void setBelongType(String belongType) {
        this.belongType = belongType;
    }
    public int getNeedSkillCount() {
        return this.needSkillCount;
    }
    public void setNeedSkillCount(int needSkillCount) {
        this.needSkillCount = needSkillCount;
    }
}
