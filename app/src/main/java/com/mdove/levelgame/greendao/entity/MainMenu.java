package com.mdove.levelgame.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author MDove on 2018/10/31
 */
@Entity
public class MainMenu {
    @Id(autoincrement = true)
    public Long id;
    public String name;
    public String tips;
    public Long clickId;
    public String btnText;
    public String type;
    public int position;
    @Generated(hash = 68722745)
    public MainMenu(Long id, String name, String tips, Long clickId, String btnText,
            String type, int position) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.clickId = clickId;
        this.btnText = btnText;
        this.type = type;
        this.position = position;
    }
    @Generated(hash = 1928140747)
    public MainMenu() {
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
    public Long getClickId() {
        return this.clickId;
    }
    public void setClickId(Long clickId) {
        this.clickId = clickId;
    }
    public String getBtnText() {
        return this.btnText;
    }
    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getPosition() {
        return this.position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
}
