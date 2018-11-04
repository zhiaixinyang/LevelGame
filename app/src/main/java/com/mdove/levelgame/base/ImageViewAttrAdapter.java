package com.mdove.levelgame.base;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by MDove on 2018/11/2.
 */

public class ImageViewAttrAdapter {
    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

}
