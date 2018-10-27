package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2018/10/20.
 * <p>
 * 用于标明不同类型的怪物掉落不同类型的装备
 */
@Entity
public class DropGoods {
    @Id(autoincrement = true)
    public Long id;
    // json格式的 掉落具体装备。数据结构在DropGoodsModel
    public String types;
    @Generated(hash = 1331265454)
    public DropGoods(Long id, String types) {
        this.id = id;
        this.types = types;
    }
    @Generated(hash = 680567192)
    public DropGoods() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTypes() {
        return this.types;
    }
    public void setTypes(String types) {
        this.types = types;
    }

}
