package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author MDove on 2018/10/26
 */
@Entity
public class BigMonsters {
    @Id(autoincrement = true)
    public Long id;
    public String name;
    public String tips;
    public int attack;
    public int armor;
    public String type;
    public String dropGoodsId;
    // 对应野区的Id
    public long monsterPlaceId;
    // 经验
    public long exp;
    public long money;
}
