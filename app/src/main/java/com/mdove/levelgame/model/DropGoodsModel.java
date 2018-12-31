package com.mdove.levelgame.model;

import java.util.List;

/**
 * Created by MDove on 2018/10/21.
 *
 * 掉落装备的临时数据结构
 */
public class DropGoodsModel {
    // 装备类型
    public String type;
    // 几率
    public float probability;
    public List<Integer> randomAttack;
    public List<Integer> randomArmor;
    public List<Integer> randomLife;
    public List<Integer> randomLiLiang;
    public List<Integer> randomMinJie;
    public List<Integer> randomZhiHui;
    public List<Integer> randomQiangZhuang;
}
