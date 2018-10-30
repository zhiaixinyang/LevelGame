package com.mdove.levelgame.main.hero.model;

import java.util.List;

/**
 * Created by MDove on 2018/10/21.
 */

public class AttackResp {
    public long monsterId;
    public int attackStatus;
    // 获得经验
    public int exp;
    // 获得金钱
    public int money;
    // 损失生命
    public int life;
    // 获取道具名字
    public List<String> dropGoods;
}
