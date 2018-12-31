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

    public BaseAttrsModel(String name, String tips, int baseAttack, int baseArmor, int baseLife, long baseAttackSpeed) {
        this.baseAttack = baseAttack;
        this.baseArmor = baseArmor;
        this.baseLife = baseLife;
        this.name = name;
        this.tips = tips;
        this.baseAttackSpeed = baseAttackSpeed;
    }
}