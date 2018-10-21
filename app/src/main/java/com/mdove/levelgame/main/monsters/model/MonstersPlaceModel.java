package com.mdove.levelgame.main.monsters.model;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersPlaceModel {
    public Long id;

    // 野区名字
    public String name;
    // 野区描述
    public String tips;

    public MonstersPlaceModel(long id, String name, String tips) {
        this.id = id;
        this.name = name;
        this.tips = tips;
    }
}
