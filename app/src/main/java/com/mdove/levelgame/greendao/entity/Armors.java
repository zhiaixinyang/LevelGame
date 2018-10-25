package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2018/10/20.
 */
@Entity
public class Armors {
    @Id(autoincrement = true)
    public long id;

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
     *  C：表示怪物
     */
    public String type;
    // 强化级别
    public int strengthen;
    @Generated(hash = 1790829770)
    public Armors(long id, String name, String tips, int attack, int armor,
            long price, String type, int strengthen) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.attack = attack;
        this.armor = armor;
        this.price = price;
        this.type = type;
        this.strengthen = strengthen;
    }
    @Generated(hash = 1024617871)
    public Armors() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
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
    public int getStrengthen() {
        return this.strengthen;
    }
    public void setStrengthen(int strengthen) {
        this.strengthen = strengthen;
    }

}
