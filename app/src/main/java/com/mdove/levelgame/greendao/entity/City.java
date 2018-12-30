package com.mdove.levelgame.greendao.entity;

import com.mdove.levelgame.greendao.utils.IntegerConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by MDove on 2018/12/28.
 * <p>
 * 所有可移动的场景
 */
@Entity
public class City {
    @Id(autoincrement = true)
    public Long id;
    public String name;
    public String tips;
    public String type;
    @Convert(columnType = String.class, converter = IntegerConverter.class)
    public List<Integer> menuBtnListId;
    public int enableOpen;
    public int clickId;
    public int isShow;
    public int isAdventure;
    public int position;
    @Generated(hash = 1435604687)
    public City(Long id, String name, String tips, String type,
            List<Integer> menuBtnListId, int enableOpen, int clickId, int isShow,
            int isAdventure, int position) {
        this.id = id;
        this.name = name;
        this.tips = tips;
        this.type = type;
        this.menuBtnListId = menuBtnListId;
        this.enableOpen = enableOpen;
        this.clickId = clickId;
        this.isShow = isShow;
        this.isAdventure = isAdventure;
        this.position = position;
    }
    @Generated(hash = 750791287)
    public City() {
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
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<Integer> getMenuBtnListId() {
        return this.menuBtnListId;
    }
    public void setMenuBtnListId(List<Integer> menuBtnListId) {
        this.menuBtnListId = menuBtnListId;
    }
    public int getEnableOpen() {
        return this.enableOpen;
    }
    public void setEnableOpen(int enableOpen) {
        this.enableOpen = enableOpen;
    }
    public int getClickId() {
        return this.clickId;
    }
    public void setClickId(int clickId) {
        this.clickId = clickId;
    }
    public int getIsShow() {
        return this.isShow;
    }
    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }
    public int getIsAdventure() {
        return this.isAdventure;
    }
    public void setIsAdventure(int isAdventure) {
        this.isAdventure = isAdventure;
    }
    public int getPosition() {
        return this.position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
}
