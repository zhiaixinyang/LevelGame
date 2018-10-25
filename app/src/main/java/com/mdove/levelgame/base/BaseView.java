package com.mdove.levelgame.base;

import android.content.Context;

/**
 * @author MDove on 2018/2/10.
 */
public interface BaseView {
    Context getContext();

    String getString(int stringId);

    void showLoadingDialog(String msg);

    void dismissLoadingDialog();
}
