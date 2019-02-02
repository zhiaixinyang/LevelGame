package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2019/2/2.
 */
@Entity
public class LiLianLevel {
    @Id(autoincrement = true)
    public Long id;
    public String name;
    public String tips;
    public String type;
    public int needLiLian;
    public String unlockType;
    public String btnName;

    @Generated(hash = 1820157376)
    public LiLianLevel(Long id, String name, String tips, String type,
            int needLiLian, String unlockType, String btnName) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.type = type;
        this.needLiLian = needLiLian;
        this.unlockType = unlockType;
        this.btnName = btnName;
    }

    @Generated(hash = 494127864)
    public LiLianLevel() {
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

    public int getNeedLiLian() {
        return this.needLiLian;
    }

    public void setNeedLiLian(int needLiLian) {
        this.needLiLian = needLiLian;
    }

    public String getUnlockType() {
        return this.unlockType;
    }

    public void setUnlockType(String unlockType) {
        this.unlockType = unlockType;
    }

    public String getBtnName() {
        return this.btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }
}
