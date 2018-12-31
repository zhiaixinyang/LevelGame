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
    public List<Integer> randomAttacks;
    public List<Integer> randomArmors;
    public List<Integer> randomLifes;
    public List<Integer> randomLiLiangs;
    public List<Integer> randomMinJies;
    public List<Integer> randomZhiHuis;
    public List<Integer> randomQiangZhuangs;
}
