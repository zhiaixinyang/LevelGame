package com.mdove.levelgame.base;

import android.content.Context;

/**
 * @author MDove on 2018/2/10.
 */
public interface BaseView {
    Context getContext();

    void showLoadingDialog(String msg);

    void dismissLoadingDialog();
}
