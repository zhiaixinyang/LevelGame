package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2018/12/31.
 */
@Entity
public class RandomAttr {
    @Id(autoincrement = true)
    public Long id;
    // 随机的攻击力
    public int randomAttack;
    public int randomArmor;
    public int randomLife;
    
    public int randomLiLiang;
    public int randomMinJie;
    public int randomZhiHui;
    public int randomQiangZhuang;
    @Generated(hash = 1858637243)
    public RandomAttr(Long id, int randomAttack, int randomArmor, int randomLife,
            int randomLiLiang, int randomMinJie, int randomZhiHui,
            int randomQiangZhuang) {
        this.id = id;
        this.randomAttack = randomAttack;
        this.randomArmor = randomArmor;
        this.randomLife = randomLife;
        this.randomLiLiang = randomLiLiang;
        this.randomMinJie = randomMinJie;
        this.randomZhiHui = randomZhiHui;
        this.randomQiangZhuang = randomQiangZhuang;
    }
    @Generated(hash = 1953398312)
    public RandomAttr() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getRandomAttack() {
        return this.randomAttack;
    }
    public void setRandomAttack(int randomAttack) {
        this.randomAttack = randomAttack;
    }
    public int getRandomArmor() {
        return this.randomArmor;
    }
    public void setRandomArmor(int randomArmor) {
        this.randomArmor = randomArmor;
    }
    public int getRandomLife() {
        return this.randomLife;
    }
    public void setRandomLife(int randomLife) {
        this.randomLife = randomLife;
    }
    public int getRandomLiLiang() {
        return this.randomLiLiang;
    }
    public void setRandomLiLiang(int randomLiLiang) {
        this.randomLiLiang = randomLiLiang;
    }
    public int getRandomMinJie() {
        return this.randomMinJie;
    }
    public void setRandomMinJie(int randomMinJie) {
        this.randomMinJie = randomMinJie;
    }
    public int getRandomZhiHui() {
        return this.randomZhiHui;
    }
    public void setRandomZhiHui(int randomZhiHui) {
        this.randomZhiHui = randomZhiHui;
    }
    public int getRandomQiangZhuang() {
        return this.randomQiangZhuang;
    }
    public void setRandomQiangZhuang(int randomQiangZhuang) {
        this.randomQiangZhuang = randomQiangZhuang;
    }

}
