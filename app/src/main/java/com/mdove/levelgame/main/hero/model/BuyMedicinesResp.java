package com.mdove.levelgame.main.hero.model;

/**
 * Created by MDove on 2018/10/21.
 */

public class BuyMedicinesResp extends BaseBuy{
    // 消耗金钱
    public int price;
    // 增加生命
    public int life;
    public long attack;
    public long armor;
    // 增加生命上限
    public int lifeUp;
    // 道具名字
    public String name;
}
