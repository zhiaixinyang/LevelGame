package com.mdove.levelgame.main.hero.model;

/**
 * Created by MDove on 2018/10/23.
 */

public class BuyAttackResp extends BaseBuy{
    // 消耗金钱
    public long price;
    // 增加攻击
    public int attack;
    // 增加防御
    public int armor;
    // 增加血上限
    public int life;
    // 道具名字
    public String name;
}
