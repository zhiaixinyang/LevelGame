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
    // 价格
    public int price;
    public String type;
    @Generated(hash = 1395275160)
    public Medicines(Long id, String name, String tips, int life, int price,
            String type) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.life = life;
        this.price = price;
        this.type = type;
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
   
}
