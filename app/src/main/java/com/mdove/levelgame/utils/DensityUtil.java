package com.mdove.levelgame.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;

import com.mdove.levelgame.App;


public class DensityUtil {

    private static int mWidthPixels;
    private static int mHeightPixels;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Converts sp to px
     *
     * @param context Context
     * @param sp      the value in sp
     * @return int
     */
    public static int dip2sp(Context context, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        return display.getWidth();
    }

    public static int getScreenWidth(Context context) {
        if (mWidthPixels == 0) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            mWidthPixels = displayMetrics.widthPixels;
        }
        return mWidthPixels;
    }

    /**
     * 获得状态栏的高度
     */
    public static int getStatusHeight() {

        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = App.getAppContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int getScreenHeight(Context context) {
        if (mHeightPixels == 0) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            mHeightPixels = displayMetrics.heightPixels;
        }
        return mHeightPixels;
    }

    public static int getYByView(View view) {
        int[] arrY = new int[2];
        view.getLocationOnScreen(arrY);
        return arrY[1];
    }

    public static int getXByView(View view) {
        int[] arrY = new int[2];
        view.getLocationOnScreen(arrY);
        return arrY[0];
    }
}