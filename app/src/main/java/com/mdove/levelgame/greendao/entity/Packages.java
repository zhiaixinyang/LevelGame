package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author MDove on 2018/10/23
 */

@Entity
public class Packages {
    @Id(autoincrement = true)
    public Long id;
    // 道具Type（标识唯一物品）
    public String type;
}
