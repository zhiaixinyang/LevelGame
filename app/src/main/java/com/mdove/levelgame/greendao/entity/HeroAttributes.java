package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by MDove on 2018/7/12.
 */
@Entity
public class HeroAttributes {
    @Id(autoincrement = true)
    public Long id;
    public int attack;
    // 每级增加多少攻击
    public int attackIncrease;
    public int armor;
    // 每级增加多少护甲
    public int armorIncrease;
    public int curLife;
    // 每级增加多少生命
    public int lifeIncrease;
    public int maxLife;
    public int money;
    // 等级
    public int level;
    // 当前经验值
    public long experience;
    // 基础升级经验值
    public long baseExp;
    // 升级经验值提升倍数
    public int expMultiple;
    // 体力
    public int bodyPower;
    // 当前生存天数
    public int days;
    // 攻击速度，毫秒级（对应Rx的弹射间隔，此值越来越低）
    public long attackSpeed;
    // 剩余技能点
    public int skillCount;
    @Generated(hash = 952374529)
    public HeroAttributes(Long id, int attack, int attackIncrease, int armor,
            int armorIncrease, int curLife, int lifeIncrease, int maxLife,
            int money, int level, long experience, long baseExp, int expMultiple,
            int bodyPower, int days, long attackSpeed, int skillCount) {
        this.id = id;
        this.attack = attack;
        this.attackIncrease = attackIncrease;
        this.armor = armor;
        this.armorIncrease = armorIncrease;
        this.curLife = curLife;
        this.lifeIncrease = lifeIncrease;
        this.maxLife = maxLife;
        this.money = money;
        this.level = level;
        this.experience = experience;
        this.baseExp = baseExp;
        this.expMultiple = expMultiple;
        this.bodyPower = bodyPower;
        this.days = days;
        this.attackSpeed = attackSpeed;
        this.skillCount = skillCount;
    }
    @Generated(hash = 219453175)
    public HeroAttributes() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getAttack() {
        return this.attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public int getAttackIncrease() {
        return this.attackIncrease;
    }
    public void setAttackIncrease(int attackIncrease) {
        this.attackIncrease = attackIncrease;
    }
    public int getArmor() {
        return this.armor;
    }
    public void setArmor(int armor) {
        this.armor = armor;
    }
    public int getArmorIncrease() {
        return this.armorIncrease;
    }
    public void setArmorIncrease(int armorIncrease) {
        this.armorIncrease = armorIncrease;
    }
    public int getCurLife() {
        return this.curLife;
    }
    public void setCurLife(int curLife) {
        this.curLife = curLife;
    }
    public int getLifeIncrease() {
        return this.lifeIncrease;
    }
    public void setLifeIncrease(int lifeIncrease) {
        this.lifeIncrease = lifeIncrease;
    }
    public int getMaxLife() {
        return this.maxLife;
    }
    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }
    public int getMoney() {
        return this.money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public int getLevel() {
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public long getExperience() {
        return this.experience;
    }
    public void setExperience(long experience) {
        this.experience = experience;
    }
    public long getBaseExp() {
        return this.baseExp;
    }
    public void setBaseExp(long baseExp) {
        this.baseExp = baseExp;
    }
    public int getExpMultiple() {
        return this.expMultiple;
    }
    public void setExpMultiple(int expMultiple) {
        this.expMultiple = expMultiple;
    }
    public int getBodyPower() {
        return this.bodyPower;
    }
    public void setBodyPower(int bodyPower) {
        this.bodyPower = bodyPower;
    }
    public int getDays() {
        return this.days;
    }
    public void setDays(int days) {
        this.days = days;
    }
    public long getAttackSpeed() {
        return this.attackSpeed;
    }
    public void setAttackSpeed(long attackSpeed) {
        this.attackSpeed = attackSpeed;
    }
    public int getSkillCount() {
        return this.skillCount;
    }
    public void setSkillCount(int skillCount) {
        this.skillCount = skillCount;
    }
}
