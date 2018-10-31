package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author MDove on 2018/10/31
 */
@Entity
public class Accessories {
    @Id(autoincrement = true)
    public Long id;
    public String name;
    public String tips;
    // 攻击值
    public int attack;
    // 护甲值
    public int armor;
    public int life;
    // 价格
    public long price;
    public String type;
    // 是否可以强化
    public int isCanStrengthen;
    // 是否可以升级
    public int isCanUpdate;
    // 是否可以合成
    public int isCanMixture;
    // 合成公式 json
    public String mixtureFormula;
    public String updateFormula;
    public String strengthenFormula;
    // 出售是否弹dialog
    public int isSpecial;
    public String belongMonsterId;
    @Generated(hash = 569723552)
    public Accessories(Long id, String name, String tips, int attack, int armor,
            int life, long price, String type, int isCanStrengthen, int isCanUpdate,
            int isCanMixture, String mixtureFormula, String updateFormula,
            String strengthenFormula, int isSpecial, String belongMonsterId) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.attack = attack;
        this.armor = armor;
        this.life = life;
        this.price = price;
        this.type = type;
        this.isCanStrengthen = isCanStrengthen;
        this.isCanUpdate = isCanUpdate;
        this.isCanMixture = isCanMixture;
        this.mixtureFormula = mixtureFormula;
        this.updateFormula = updateFormula;
        this.strengthenFormula = strengthenFormula;
        this.isSpecial = isSpecial;
        this.belongMonsterId = belongMonsterId;
    }
    @Generated(hash = 1483837939)
    public Accessories() {
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
    public int getLife() {
        return this.life;
    }
    public void setLife(int life) {
        this.life = life;
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
    public int getIsSpecial() {
        return this.isSpecial;
    }
    public void setIsSpecial(int isSpecial) {
        this.isSpecial = isSpecial;
    }
    public String getBelongMonsterId() {
        return this.belongMonsterId;
    }
    public void setBelongMonsterId(String belongMonsterId) {
        this.belongMonsterId = belongMonsterId;
    }
}
