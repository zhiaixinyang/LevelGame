package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MBENBEN on 2018/10/20.
 */
@Entity
public class MonstersPlace {
    @Id(autoincrement = true)
    public Long id;

    // 野区名字
    public String name;
    // 野区描述
    public String tips;
    @Generated(hash = 1419623646)
    public MonstersPlace(Long id, String name, String tips) {
        this.id = id;
        this.name = name;
        this.tips = tips;
    }
    @Generated(hash = 1850515903)
    public MonstersPlace() {
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
}
