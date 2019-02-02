package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2019/2/2.
 */
@Entity
public class PracticePlace {
    @Id(autoincrement = true)
    public Long id;
    public String name;
    public String tips;
    public String type;
    public int isLimitCount;
    public int limitCount;
    public int curCount;
    public int consumeMoney;
    public int consumePower;
    public String consumeGoods;
    @Generated(hash = 31567547)
    public PracticePlace(Long id, String name, String tips, String type,
            int isLimitCount, int limitCount, int curCount, int consumeMoney,
            int consumePower, String consumeGoods) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.type = type;
        this.isLimitCount = isLimitCount;
        this.limitCount = limitCount;
        this.curCount = curCount;
        this.consumeMoney = consumeMoney;
        this.consumePower = consumePower;
        this.consumeGoods = consumeGoods;
    }
    @Generated(hash = 1458600013)
    public PracticePlace() {
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
    public int getIsLimitCount() {
        return this.isLimitCount;
    }
    public void setIsLimitCount(int isLimitCount) {
        this.isLimitCount = isLimitCount;
    }
    public int getLimitCount() {
        return this.limitCount;
    }
    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }
    public int getCurCount() {
        return this.curCount;
    }
    public void setCurCount(int curCount) {
        this.curCount = curCount;
    }
    public int getConsumeMoney() {
        return this.consumeMoney;
    }
    public void setConsumeMoney(int consumeMoney) {
        this.consumeMoney = consumeMoney;
    }
    public int getConsumePower() {
        return this.consumePower;
    }
    public void setConsumePower(int consumePower) {
        this.consumePower = consumePower;
    }
    public String getConsumeGoods() {
        return this.consumeGoods;
    }
    public void setConsumeGoods(String consumeGoods) {
        this.consumeGoods = consumeGoods;
    }
}
