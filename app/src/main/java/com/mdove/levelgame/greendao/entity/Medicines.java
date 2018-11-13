package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2018/10/21.
 * <p>
 * 药瓶，type：D开头
 */

@Entity
public class Medicines {
    @Id(autoincrement = true)
    public Long id;
    // 药品名
    public String name;
    // 提示
    public String tips;
    // 加血
    public int life;
    // 血上限
    public int lifeUp;
    // 价格
    public int price;
    public String type;
    public long attack;
    public long armor;
    public int limitCount;
    public int isLimitCount;
    // 当前还剩了
    public int curCount;
    @Generated(hash = 900804102)
    public Medicines(Long id, String name, String tips, int life, int lifeUp,
            int price, String type, long attack, long armor, int limitCount,
            int isLimitCount, int curCount) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.life = life;
        this.lifeUp = lifeUp;
        this.price = price;
        this.type = type;
        this.attack = attack;
        this.armor = armor;
        this.limitCount = limitCount;
        this.isLimitCount = isLimitCount;
        this.curCount = curCount;
    }
    @Generated(hash = 1436234687)
    public Medicines() {
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
    public int getLife() {
        return this.life;
    }
    public void setLife(int life) {
        this.life = life;
    }
    public int getLifeUp() {
        return this.lifeUp;
    }
    public void setLifeUp(int lifeUp) {
        this.lifeUp = lifeUp;
    }
    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public long getAttack() {
        return this.attack;
    }
    public void setAttack(long attack) {
        this.attack = attack;
    }
    public long getArmor() {
        return this.armor;
    }
    public void setArmor(long armor) {
        this.armor = armor;
    }
    public int getLimitCount() {
        return this.limitCount;
    }
    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }
    public int getIsLimitCount() {
        return this.isLimitCount;
    }
    public void setIsLimitCount(int isLimitCount) {
        this.isLimitCount = isLimitCount;
    }
    public int getCurCount() {
        return this.curCount;
    }
    public void setCurCount(int curCount) {
        this.curCount = curCount;
    }
}
