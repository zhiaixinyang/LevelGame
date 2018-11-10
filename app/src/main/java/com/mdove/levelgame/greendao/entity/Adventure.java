package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2018/10/29.
 * 奇遇
 */
@Entity
public class Adventure {
    @Id(autoincrement = true)
    public Long id;
    // 目前仅支持monsters
    public String type;
    public int days;
    public long monsterPlaceId;
    public int isCycle;
    @Generated(hash = 1428979129)
    public Adventure(Long id, String type, int days, long monsterPlaceId,
            int isCycle) {
        this.id = id;
        this.type = type;
        this.days = days;
        this.monsterPlaceId = monsterPlaceId;
        this.isCycle = isCycle;
    }
    @Generated(hash = 1184037432)
    public Adventure() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getDays() {
        return this.days;
    }
    public void setDays(int days) {
        this.days = days;
    }
    public long getMonsterPlaceId() {
        return this.monsterPlaceId;
    }
    public void setMonsterPlaceId(long monsterPlaceId) {
        this.monsterPlaceId = monsterPlaceId;
    }
    public int getIsCycle() {
        return this.isCycle;
    }
    public void setIsCycle(int isCycle) {
        this.isCycle = isCycle;
    }

}
