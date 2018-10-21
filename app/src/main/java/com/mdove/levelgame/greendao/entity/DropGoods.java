package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MBENBEN on 2018/10/20.
 * <p>
 * 用于标明不同类型的怪物掉落不同类型的装备
 */
@Entity
public class DropGoods {
    @Id(autoincrement = true)
    public long id;
    // json格式的 掉落具体装备。数据结构在DropGoodsModel
    public String data;
    @Generated(hash = 222985917)
    public DropGoods(long id, String data) {
        this.id = id;
        this.data = data;
    }
    @Generated(hash = 680567192)
    public DropGoods() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }

}
