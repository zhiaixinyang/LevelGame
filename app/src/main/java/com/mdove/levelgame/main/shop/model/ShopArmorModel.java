package com.mdove.levelgame.main.shop.model;

/**
 * @author zhaojing on 2018/10/22
 */
public class ShopArmorModel {
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
    public int price;
    /**
     *  装备类型 标识什么样子的装备。比如：普通装备（369等），神装...
     *  首字母A：表示武器
     *  B：表示铠甲
     *  C：表示怪物
     */
    public String type;
    // 强化级别
    public int strengthen;
}
