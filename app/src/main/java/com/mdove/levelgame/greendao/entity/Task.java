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

    public String name;
    public String tips;
    public String type;
    public long awardMoney;
    public long awardExp;
    public long awardAttack;
    public long awardArmor;
    public long awardMaxLife;
    @Generated(hash = 1984693036)
    public Task(Long id, String name, String tips, String type, long awardMoney,
            long awardExp, long awardAttack, long awardArmor, long awardMaxLife) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.type = type;
        this.awardMoney = awardMoney;
        this.awardExp = awardExp;
        this.awardAttack = awardAttack;
        this.awardArmor = awardArmor;
        this.awardMaxLife = awardMaxLife;
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
}