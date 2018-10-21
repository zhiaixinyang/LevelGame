package com.mdove.levelgame.main.monsters.model;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersModel {
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

}
