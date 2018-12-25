package com.mdove.levelgame.view.cycleviewpager;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MDove on 2018/12/25.
 */
public class CycleImageEntity {
    @SerializedName("banner_image_url")
    public String imageUrl;

    @SerializedName("banner_click_url")
    public String directUrl;

    public CycleImageEntity(String imageUrl, String directUrl) {
        this.imageUrl = imageUrl;
        this.directUrl = directUrl;
    }

}
