package com.mdove.levelgame.model;

/**
 * Created by MDove on 2018/12/2.
 */
public class BaseAttrsModel {
    // 攻击值
    public int baseAttack;
    // 护甲值
    public int baseArmor;
    public int baseLife;
    public long baseAttackSpeed;

    public BaseAttrsModel(int baseAttack, int baseArmor, int baseLife, long baseAttackSpeed) {
        this.baseAttack = baseAttack;
        this.baseArmor = baseArmor;
        this.baseLife = baseLife;
        this.baseAttackSpeed = baseAttackSpeed;
    }
}