package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2018/10/20.
 */
@Entity
public class Weapons {
    @Id(autoincrement = true)
    public Long id;

    // 武器名
    public String name;
    // 提示
    public String tips;
    // 攻击值
    public int attack;
    // 护甲值
    public int armor;
    // 价格
    public long price;
    /**
     *  装备类型 标识什么样子的装备。比如：普通装备（369等），神装...
     *  首字母A：表示武器
     *  B：表示铠甲
     */
    public String type;
    // 是否可以强化
    public int isCanStrengthen;
    // 是否可以升级
    public int isCanUpdate;
    // 是否可以合成
    public int isCanMixture;
    @Generated(hash = 815521936)
    public Weapons(Long id, String name, String tips, int attack, int armor,
            long price, String type, int isCanStrengthen, int isCanUpdate,
            int isCanMixture) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.attack = attack;
        this.armor = armor;
        this.price = price;
        this.type = type;
        this.isCanStrengthen = isCanStrengthen;
        this.isCanUpdate = isCanUpdate;
        this.isCanMixture = isCanMixture;
    }
    @Generated(hash = 1666722499)
    public Weapons() {
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
    public int getAttack() {
        return this.attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public int getArmor() {
        return this.armor;
    }
    public void setArmor(int armor) {
        this.armor = armor;
    }
    public long getPrice() {
        return this.price;
    }
    public void setPrice(long price) {
        this.price = price;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getIsCanStrengthen() {
        return this.isCanStrengthen;
    }
    public void setIsCanStrengthen(int isCanStrengthen) {
        this.isCanStrengthen = isCanStrengthen;
    }
    public int getIsCanUpdate() {
        return this.isCanUpdate;
    }
    public void setIsCanUpdate(int isCanUpdate) {
        this.isCanUpdate = isCanUpdate;
    }
    public int getIsCanMixture() {
        return this.isCanMixture;
    }
    public void setIsCanMixture(int isCanMixture) {
        this.isCanMixture = isCanMixture;
    }
}
