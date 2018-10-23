package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author MDove on 2018/10/23
 */

@Entity
public class Packages {
    @Id(autoincrement = true)
    public Long id;
    // 道具Type（标识唯一物品）
    public String type;
    // 是否装备：0表示装备
    public int isEquip;
    @Generated(hash = 257811575)
    public Packages(Long id, String type, int isEquip) {
        this.id = id;
        this.type = type;
        this.isEquip = isEquip;
    }
    @Generated(hash = 688242455)
    public Packages() {
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
    public int getIsEquip() {
        return this.isEquip;
    }
    public void setIsEquip(int isEquip) {
        this.isEquip = isEquip;
    }
}
