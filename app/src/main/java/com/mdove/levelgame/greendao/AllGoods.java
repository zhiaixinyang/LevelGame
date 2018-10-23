package com.mdove.levelgame.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author MDove on 2018/10/23
 * <p>
 * 此实体包含所有道具的type及对应id（goods）
 *
 * 目前需要去type，拿首个字符，映射对应的数据
 */
@Entity
public class AllGoods {
    @Id(autoincrement = true)
    public Long id;

    public String type;

    public long goodsId;

    @Generated(hash = 150364130)
    public AllGoods(Long id, String type, long goodsId) {
        this.id = id;
        this.type = type;
        this.goodsId = goodsId;
    }

    @Generated(hash = 1080634193)
    public AllGoods() {
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

    public long getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
