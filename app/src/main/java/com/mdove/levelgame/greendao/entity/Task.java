package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author MDove on 2018/11/5
 */
@Entity
public class Task {
    @Id(autoincrement = true)
    public Long id;
    public long attackCount;
    public String taskContentType;
    public String name;
    public String tips;
    public String type;
    public long awardMoney;
    public long awardExp;
    public long awardAttack;
    public long awardArmor;
    public long awardMaxLife;
    public int consumePower;
    public int consumeMoney;
    // 0代表可被任务，1表示进行中，2代表可以提交了
    public int taskStatus;
    public String consumeFormula;
    @Generated(hash = 1822552119)
    public Task(Long id, long attackCount, String taskContentType, String name,
            String tips, String type, long awardMoney, long awardExp,
            long awardAttack, long awardArmor, long awardMaxLife, int consumePower,
            int consumeMoney, int taskStatus, String consumeFormula) {
        this.id = id;
        this.attackCount = attackCount;
        this.taskContentType = taskContentType;
        this.name = name;
        this.tips = tips;
        this.type = type;
        this.awardMoney = awardMoney;
        this.awardExp = awardExp;
        this.awardAttack = awardAttack;
        this.awardArmor = awardArmor;
        this.awardMaxLife = awardMaxLife;
        this.consumePower = consumePower;
        this.consumeMoney = consumeMoney;
        this.taskStatus = taskStatus;
        this.consumeFormula = consumeFormula;
    }
    @Generated(hash = 733837707)
    public Task() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getAttackCount() {
        return this.attackCount;
    }
    public void setAttackCount(long attackCount) {
        this.attackCount = attackCount;
    }
    public String getTaskContentType() {
        return this.taskContentType;
    }
    public void setTaskContentType(String taskContentType) {
        this.taskContentType = taskContentType;
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
    public long getAwardMoney() {
        return this.awardMoney;
    }
    public void setAwardMoney(long awardMoney) {
        this.awardMoney = awardMoney;
    }
    public long getAwardExp() {
        return this.awardExp;
    }
    public void setAwardExp(long awardExp) {
        this.awardExp = awardExp;
    }
    public long getAwardAttack() {
        return this.awardAttack;
    }
    public void setAwardAttack(long awardAttack) {
        this.awardAttack = awardAttack;
    }
    public long getAwardArmor() {
        return this.awardArmor;
    }
    public void setAwardArmor(long awardArmor) {
        this.awardArmor = awardArmor;
    }
    public long getAwardMaxLife() {
        return this.awardMaxLife;
    }
    public void setAwardMaxLife(long awardMaxLife) {
        this.awardMaxLife = awardMaxLife;
    }
    public int getConsumePower() {
        return this.consumePower;
    }
    public void setConsumePower(int consumePower) {
        this.consumePower = consumePower;
    }
    public int getConsumeMoney() {
        return this.consumeMoney;
    }
    public void setConsumeMoney(int consumeMoney) {
        this.consumeMoney = consumeMoney;
    }
    public int getTaskStatus() {
        return this.taskStatus;
    }
    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }
    public String getConsumeFormula() {
        return this.consumeFormula;
    }
    public void setConsumeFormula(String consumeFormula) {
        this.consumeFormula = consumeFormula;
    }
}