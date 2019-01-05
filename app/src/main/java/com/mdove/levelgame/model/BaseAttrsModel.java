package com.mdove.levelgame.model;

/**
 * Created by MDove on 2018/12/2.
 */
public class BaseAttrsModel {
    public String name;
    public String tips;
    // 攻击值
    public int baseAttack;
    // 护甲值
    public int baseArmor;
    public int baseLife;
    public long baseAttackSpeed;

    public int baseNeedLevel;
    public int baseNeedLiLiang;
    public int baseNeedMinJie;
    public int baseNeedZhiHui;
    public int baseNeedQiangZhuang;

    public BaseAttrsModel(String name, String tips) {
        this.name = name;
        this.tips = tips;
    }

    public BaseAttrsModel(String name, String tips, int baseAttack, int baseArmor, int baseLife, long baseAttackSpeed
            , int needLevel, int liLiang, int minJie, int zhiHui, int qiangZhuang) {
        this.baseAttack = baseAttack;
        this.baseArmor = baseArmor;
        this.baseLife = baseLife;
        this.name = name;
        this.tips = tips;
        this.baseAttackSpeed = baseAttackSpeed;
        baseNeedLevel = needLevel;
        baseNeedLiLiang = liLiang;
        baseNeedMinJie = minJie;
        baseNeedZhiHui = zhiHui;
        baseNeedQiangZhuang = qiangZhuang;
    }
}