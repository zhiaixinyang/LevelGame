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
    // 声望（解锁内容）
    public int shengWang;
    // 历练（战士1-战士2-战士3...）
    public int liLian;
    // 力量属性
    public int liLiang;
    public long liLiangExp;
    public long liLiangBaseExp;
    public int liLiangExpMultiple;
    // 敏捷
    public int minJie;
    public long minJieExp;
    public long minJieBaseExp;
    public int minJieExpMultiple;
    // 智慧
    public int zhiHui;
    public long zhiHuiExp;
    public long zhiHuiBaseExp;
    public int zhiHuiExpMultiple;
    // 强壮
    public int qiangZhuang;
    public long qiangZhuangExp;
    public long qiangZhuangBaseExp;
    public int qiangZhuangExpMultiple;

    @Generated(hash = 1937033157)
    public HeroAttributes(Long id, int attack, int attackIncrease, int armor,
            int armorIncrease, int curLife, int lifeIncrease, int maxLife,
            int money, int level, long experience, long baseExp, int expMultiple,
            int bodyPower, int days, long attackSpeed, int skillCount,
            int shengWang, int liLian, int liLiang, long liLiangExp,
            long liLiangBaseExp, int liLiangExpMultiple, int minJie, long minJieExp,
            long minJieBaseExp, int minJieExpMultiple, int zhiHui, long zhiHuiExp,
            long zhiHuiBaseExp, int zhiHuiExpMultiple, int qiangZhuang,
            long qiangZhuangExp, long qiangZhuangBaseExp,
            int qiangZhuangExpMultiple) {
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
        this.shengWang = shengWang;
        this.liLian = liLian;
        this.liLiang = liLiang;
        this.liLiangExp = liLiangExp;
        this.liLiangBaseExp = liLiangBaseExp;
        this.liLiangExpMultiple = liLiangExpMultiple;
        this.minJie = minJie;
        this.minJieExp = minJieExp;
        this.minJieBaseExp = minJieBaseExp;
        this.minJieExpMultiple = minJieExpMultiple;
        this.zhiHui = zhiHui;
        this.zhiHuiExp = zhiHuiExp;
        this.zhiHuiBaseExp = zhiHuiBaseExp;
        this.zhiHuiExpMultiple = zhiHuiExpMultiple;
        this.qiangZhuang = qiangZhuang;
        this.qiangZhuangExp = qiangZhuangExp;
        this.qiangZhuangBaseExp = qiangZhuangBaseExp;
        this.qiangZhuangExpMultiple = qiangZhuangExpMultiple;
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

    public int getLiLiang() {
        return this.liLiang;
    }

    public void setLiLiang(int liLiang) {
        this.liLiang = liLiang;
    }

    public long getLiLiangExp() {
        return this.liLiangExp;
    }

    public void setLiLiangExp(long liLiangExp) {
        this.liLiangExp = liLiangExp;
    }

    public long getLiLiangBaseExp() {
        return this.liLiangBaseExp;
    }

    public void setLiLiangBaseExp(long liLiangBaseExp) {
        this.liLiangBaseExp = liLiangBaseExp;
    }

    public int getLiLiangExpMultiple() {
        return this.liLiangExpMultiple;
    }

    public void setLiLiangExpMultiple(int liLiangExpMultiple) {
        this.liLiangExpMultiple = liLiangExpMultiple;
    }

    public int getMinJie() {
        return this.minJie;
    }

    public void setMinJie(int minJie) {
        this.minJie = minJie;
    }

    public long getMinJieExp() {
        return this.minJieExp;
    }

    public void setMinJieExp(long minJieExp) {
        this.minJieExp = minJieExp;
    }

    public long getMinJieBaseExp() {
        return this.minJieBaseExp;
    }

    public void setMinJieBaseExp(long minJieBaseExp) {
        this.minJieBaseExp = minJieBaseExp;
    }

    public int getMinJieExpMultiple() {
        return this.minJieExpMultiple;
    }

    public void setMinJieExpMultiple(int minJieExpMultiple) {
        this.minJieExpMultiple = minJieExpMultiple;
    }

    public int getZhiHui() {
        return this.zhiHui;
    }

    public void setZhiHui(int zhiHui) {
        this.zhiHui = zhiHui;
    }

    public long getZhiHuiExp() {
        return this.zhiHuiExp;
    }

    public void setZhiHuiExp(long zhiHuiExp) {
        this.zhiHuiExp = zhiHuiExp;
    }

    public long getZhiHuiBaseExp() {
        return this.zhiHuiBaseExp;
    }

    public void setZhiHuiBaseExp(long zhiHuiBaseExp) {
        this.zhiHuiBaseExp = zhiHuiBaseExp;
    }

    public int getZhiHuiExpMultiple() {
        return this.zhiHuiExpMultiple;
    }

    public void setZhiHuiExpMultiple(int zhiHuiExpMultiple) {
        this.zhiHuiExpMultiple = zhiHuiExpMultiple;
    }

    public int getQiangZhuang() {
        return this.qiangZhuang;
    }

    public void setQiangZhuang(int qiangZhuang) {
        this.qiangZhuang = qiangZhuang;
    }

    public long getQiangZhuangExp() {
        return this.qiangZhuangExp;
    }

    public void setQiangZhuangExp(long qiangZhuangExp) {
        this.qiangZhuangExp = qiangZhuangExp;
    }

    public long getQiangZhuangBaseExp() {
        return this.qiangZhuangBaseExp;
    }

    public void setQiangZhuangBaseExp(long qiangZhuangBaseExp) {
        this.qiangZhuangBaseExp = qiangZhuangBaseExp;
    }

    public int getQiangZhuangExpMultiple() {
        return this.qiangZhuangExpMultiple;
    }

    public void setQiangZhuangExpMultiple(int qiangZhuangExpMultiple) {
        this.qiangZhuangExpMultiple = qiangZhuangExpMultiple;
    }

    public int getShengWang() {
        return this.shengWang;
    }

    public void setShengWang(int shengWang) {
        this.shengWang = shengWang;
    }

    public int getLiLian() {
        return this.liLian;
    }

    public void setLiLian(int liLian) {
        this.liLian = liLian;
    }

}
