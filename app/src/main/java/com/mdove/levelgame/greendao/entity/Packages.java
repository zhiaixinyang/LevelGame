package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author MDove on 2018/10/23
 *         <p>
 *         开启强化之后：强化过的装备都会对应到MyWeapons上
 */
@Entity
public class Packages {
    @Id(autoincrement = true)
    public Long id;
    // 道具Type（标识唯一物品）
    public String type;
    // 是否装备：0表示装备
    public int isEquip;
    // 装备强化等级
    public int strengthenLevel;
    // 选中，表示在升级/合成 选材料的时候已经被选中了
    public int isSelect;
    @Generated(hash = 967187845)
    public Packages(Long id, String type, int isEquip, int strengthenLevel,
            int isSelect) {
        this.id = id;
        this.type = type;
        this.isEquip = isEquip;
        this.strengthenLevel = strengthenLevel;
        this.isSelect = isSelect;
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
    public int getStrengthenLevel() {
        return this.strengthenLevel;
    }
    public void setStrengthenLevel(int strengthenLevel) {
        this.strengthenLevel = strengthenLevel;
    }
    public int getIsSelect() {
        return this.isSelect;
    }
    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }

}
