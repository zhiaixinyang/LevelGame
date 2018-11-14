package com.mdove.levelgame.update.model;

import java.io.Serializable;

/**
 * Created by MDove on 2018/11/14.
 */

public class RealUpdate implements Serializable{
    private String src;
    private String check;
    private String nowversion;

    public String getNowversion() {
        return nowversion;
    }

    public void setNowversion(String nowversion) {
        this.nowversion = nowversion;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
