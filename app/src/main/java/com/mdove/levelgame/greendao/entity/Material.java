package com.mdove.levelgame.greendao.entity;

import com.mdove.levelgame.model.BaseAttrsModel;
import com.mdove.levelgame.model.BaseBlacksmithModel;
import com.mdove.levelgame.model.IAttrsModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2018/10/27.
 * <p>
 * 材料 type：E
 */
@Entity
public class Material extends BaseBlacksmithModel implements IAttrsModel {
    @Id(autoincrement = true)
    public Long id;

    public String type;
    public String name;
    public String tips;
    public long price;
    // 是否可以合成
    public int isCanMixture;
    // 合成公式 json
    public String mixtureFormula;
    @Generated(hash = 97262922)
    public Material(Long id, String type, String name, String tips, long price,
            int isCanMixture, String mixtureFormula) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.tips = tips;
        this.price = price;
        this.isCanMixture = isCanMixture;
        this.mixtureFormula = mixtureFormula;
    }
    @Generated(hash = 1176792654)
    public Material() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
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
    public long getPrice() {
        return this.price;
    }
    public void setPrice(long price) {
        this.price = price;
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

    @Override
    public void constructorBlacksmithModel() {
        canMixture = isCanMixture;

        myName = name;
        myType = type;

        mixtureFormulas = mixtureFormula;
    }

    @Override
    public BaseAttrsModel getAttrsModel() {
        return new BaseAttrsModel(name, tips);

    }
}
