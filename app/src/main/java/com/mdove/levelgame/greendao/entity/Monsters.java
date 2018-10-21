package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MBENBEN on 2018/10/20.
 */
@Entity
public class Monsters {
    @Id(autoincrement = true)
    public Long id;
    // 生命值
    public int life;
    // 怪物类型 标识什么样子的怪物。比如：普通小怪（369等），BOSS...
    public String type;
    // 攻击力
    public int attack;
    // 护甲
    public int armor;
    // 掉落金钱
    public int money;
    // 描述
    public String tips;
    // 名字
    public String name;
    // 特殊掉落的物品的id（json串可以掉落多个）
    public String dropGoodsId;
    // 对应野区的Id
    public long monsterPlaceId;
    // 经验
    public long exp;
    @Generated(hash = 553614126)
    public Monsters(Long id, int life, String type, int attack, int armor,
            int money, String tips, String name, String dropGoodsId,
            long monsterPlaceId, long exp) {
        this.id = id;
        this.life = life;
        this.type = type;
        this.attack = attack;
        this.armor = armor;
        this.money = money;
        this.tips = tips;
        this.name = name;
        this.dropGoodsId = dropGoodsId;
        this.monsterPlaceId = monsterPlaceId;
        this.exp = exp;
    }
    @Generated(hash = 1349461748)
    public Monsters() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getLife() {
        return this.life;
    }
    public void setLife(int life) {
        this.life = life;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
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
    public int getMoney() {
        return this.money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public String getTips() {
        return this.tips;
    }
    public void setTips(String tips) {
        this.tips = tips;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDropGoodsId() {
        return this.dropGoodsId;
    }
    public void setDropGoodsId(String dropGoodsId) {
        this.dropGoodsId = dropGoodsId;
    }
    public long getMonsterPlaceId() {
        return this.monsterPlaceId;
    }
    public void setMonsterPlaceId(long monsterPlaceId) {
        this.monsterPlaceId = monsterPlaceId;
    }
    public long getExp() {
        return this.exp;
    }
    public void setExp(long exp) {
        this.exp = exp;
    }

}
