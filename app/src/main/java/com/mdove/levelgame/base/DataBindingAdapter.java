package com.mdove.levelgame.base;

import android.databinding.BindingAdapter;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MDove on 2018/3/5.
 */

public class DataBindingAdapter {
    @BindingAdapter("htmlColorText")
    public static void htmlColorText(TextView view,String str) {
        if (!TextUtils.isEmpty(str)){
            view.setText(Html.fromHtml(str));
        }
    }
}
