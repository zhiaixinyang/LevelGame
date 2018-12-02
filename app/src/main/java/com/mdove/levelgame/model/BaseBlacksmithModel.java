package com.mdove.levelgame.model;

/**
 * Created by MDove on 2018/12/2.
 */
public abstract class BaseBlacksmithModel {
    // 是否可以强化
    public int canStrengthen;
    // 是否可以升级
    public int canUpdate;
    // 是否可以合成
    public int canMixture;
    // 合成公式 json
    public String strengthenFormulas;
    public String updateFormulas;
    public String mixtureFormulas;
    public String myType;
    public String myName;

    public abstract void constructorBlacksmithModel();

}