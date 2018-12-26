package com.mdove.levelgame.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;

/**
 * Created by MDove on 2018/12/26.
 */
public class MDoveDialog extends Dialog {
    protected Context mContext;

    public MDoveDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    public boolean isViewValid() {
        if (mContext instanceof Activity) {
            return !((Activity) mContext).isFinishing();
        }
        return true;
    }

    @Override
    public void show() {
        if (!isViewValid()) {
            return;
        }
        try {
            super.show();
        } catch (Exception e) {
            Log.w("SSDialog", "", e);
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        if (!isViewValid()) {
            return;
        }
        try {
            super.dismiss();
        } catch (Exception e) {

        }
    }

}
