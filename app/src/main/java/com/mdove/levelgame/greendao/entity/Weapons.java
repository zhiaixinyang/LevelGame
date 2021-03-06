package com.mdove.levelgame.greendao.entity;

import com.mdove.levelgame.model.BaseAttrsModel;
import com.mdove.levelgame.model.BaseBlacksmithModel;
import com.mdove.levelgame.model.IAttrsModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2018/10/20.
 */
@Entity
public class Weapons extends BaseBlacksmithModel implements IAttrsModel {
    @Id(autoincrement = true)
    public Long id;

    // 武器名
    public String name;
    // 提示
    public String tips;
    // 攻击值
    public int attack;
    // 护甲值
    public int armor;
    // 价格
    public long price;
    public int isLock;
    /**
     * 装备类型 标识什么样子的装备。比如：普通装备（369等），神装...
     * 首字母A：表示武器
     * B：表示铠甲
     */
    public String type;
    // 是否可以强化
    public int isCanStrengthen;
    // 是否可以升级
    public int isCanUpdate;
    // 是否可以合成
    public int isCanMixture;
    // 出售是否弹dialog
    public int isSpecial;
    // 合成公式 json
    public String mixtureFormula;
    public String updateFormula;
    public String strengthenFormula;
    // 此装备属于哪个monster（1000表示武器商店，1001表示防具商店，1002表示铁匠铺）通过split(",")去截
    public String belongMonsterId;
    // 此值为多少，就降低多少攻击间隔，毫秒级（对应Rx的弹射间隔，此值越来越低）
    public long attackSpeed;
    public int position;
    public int needLevel;
    public int needLiLiang;
    public int needMinJie;
    public int needZhiHui;
    public int needQiangZhuang;


    @Generated(hash = 889371871)
    public Weapons(Long id, String name, String tips, int attack, int armor,
            long price, int isLock, String type, int isCanStrengthen,
            int isCanUpdate, int isCanMixture, int isSpecial, String mixtureFormula,
            String updateFormula, String strengthenFormula, String belongMonsterId,
            long attackSpeed, int position, int needLevel, int needLiLiang,
            int needMinJie, int needZhiHui, int needQiangZhuang) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.attack = attack;
        this.armor = armor;
        this.price = price;
        this.isLock = isLock;
        this.type = type;
        this.isCanStrengthen = isCanStrengthen;
        this.isCanUpdate = isCanUpdate;
        this.isCanMixture = isCanMixture;
        this.isSpecial = isSpecial;
        this.mixtureFormula = mixtureFormula;
        this.updateFormula = updateFormula;
        this.strengthenFormula = strengthenFormula;
        this.belongMonsterId = belongMonsterId;
        this.attackSpeed = attackSpeed;
        this.position = position;
        this.needLevel = needLevel;
        this.needLiLiang = needLiLiang;
        this.needMinJie = needMinJie;
        this.needZhiHui = needZhiHui;
        this.needQiangZhuang = needQiangZhuang;
    }

    @Generated(hash = 1666722499)
    public Weapons() {
    }
    

    @Override
    public void constructorBlacksmithModel() {
        canMixture = isCanStrengthen;
        canUpdate = isCanUpdate;
        canMixture = isCanMixture;

        myName = name;
        myType = type;

        strengthenFormulas = strengthenFormula;
        updateFormulas = updateFormula;
        mixtureFormulas = mixtureFormula;
    }

    @Override
    public BaseAttrsModel getAttrsModel() {
        return new BaseAttrsModel(name, tips, attack, armor, 0, 0,
                needLevel, needLiLiang, needMinJie, needZhiHui, needQiangZhuang);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTips() {
        return this.tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getAttack() {
        return this.attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getArmor() {
        return this.armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public long getPrice() {
        return this.price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsCanStrengthen() {
        return this.isCanStrengthen;
    }

    public void setIsCanStrengthen(int isCanStrengthen) {
        this.isCanStrengthen = isCanStrengthen;
    }

    public int getIsCanUpdate() {
        return this.isCanUpdate;
    }

    public void setIsCanUpdate(int isCanUpdate) {
        this.isCanUpdate = isCanUpdate;
    }

    public int getIsCanMixture() {
        return this.isCanMixture;
    }

    public void setIsCanMixture(int isCanMixture) {
        this.isCanMixture = isCanMixture;
    }

    public int getIsSpecial() {
        return this.isSpecial;
    }

    public void setIsSpecial(int isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getMixtureFormula() {
        return this.mixtureFormula;
    }

    public void setMixtureFormula(String mixtureFormula) {
        this.mixtureFormula = mixtureFormula;
    }

    public String getUpdateFormula() {
        return this.updateFormula;
    }

    public void setUpdateFormula(String updateFormula) {
        this.updateFormula = updateFormula;
    }

    public String getStrengthenFormula() {
        return this.strengthenFormula;
    }

    public void setStrengthenFormula(String strengthenFormula) {
        this.strengthenFormula = strengthenFormula;
    }

    public String getBelongMonsterId() {
        return this.belongMonsterId;
    }

    public void setBelongMonsterId(String belongMonsterId) {
        this.belongMonsterId = belongMonsterId;
    }

    public long getAttackSpeed() {
        return this.attackSpeed;
    }

    public void setAttackSpeed(long attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNeedLevel() {
        return this.needLevel;
    }

    public void setNeedLevel(int needLevel) {
        this.needLevel = needLevel;
    }

    public int getNeedLiLiang() {
        return this.needLiLiang;
    }

    public void setNeedLiLiang(int needLiLiang) {
        this.needLiLiang = needLiLiang;
    }

    public int getNeedMinJie() {
        return this.needMinJie;
    }

    public void setNeedMinJie(int needMinJie) {
        this.needMinJie = needMinJie;
    }

    public int getNeedZhiHui() {
        return this.needZhiHui;
    }

    public void setNeedZhiHui(int needZhiHui) {
        this.needZhiHui = needZhiHui;
    }

    public int getNeedQiangZhuang() {
        return this.needQiangZhuang;
    }

    public void setNeedQiangZhuang(int needQiangZhuang) {
        this.needQiangZhuang = needQiangZhuang;
    }

    public int getIsLock() {
        return this.isLock;
    }

    public void setIsLock(int isLock) {
        this.isLock = isLock;
    }
}
