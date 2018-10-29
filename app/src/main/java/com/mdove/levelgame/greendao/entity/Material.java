package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2018/10/27.
 * <p>
 * 材料 type：E
 */
@Entity
public class Material {
    @Id(autoincrement = true)
    public Long id;

    public String type;
    public String name;
    public String tips;
    public long price;
    @Generated(hash = 1136677447)
    public Material(Long id, String type, String name, String tips, long price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.tips = tips;
        this.price = price;
    }
    @Generated(hash = 1176792654)
    public Material() {
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
    public long getPrice() {
        return this.price;
    }
    public void setPrice(long price) {
        this.price = price;
    }

}
