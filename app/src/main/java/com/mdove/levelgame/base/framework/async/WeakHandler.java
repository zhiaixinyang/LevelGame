package com.mdove.levelgame.base.framework.async;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Handler use WeakReference to hold callback.
 */
public class WeakHandler extends Handler {

    public static final int MSG_CHECK_CATEGORY_TIP = 10;
    public static final int MSG_ON_PULL_REFRESH = 12;
    public static final int MSG_ON_TAB_CLICK_REFRESH = 13;
    public static final int MSG_ON_ACCOUNT_CHANGED_AUTO_REFRESH = 14;
    public static final int MSG_ON_BACK_PRESS_TO_REFRESH = 15;
    public static final int MSG_ON_BLANK_CATEGORY_REFRESH = 16;

    public interface IHandler {
        void handleMsg(Message msg);
    }

    WeakReference<IHandler> mRef;

    public WeakHandler(IHandler handler) {
        mRef = new WeakReference<>(handler);
    }

    public WeakHandler(Looper looper, IHandler handler) {
        super(looper);
        mRef = new WeakReference<>(handler);
    }

    @Override
    public void handleMessage(Message msg) {
        IHandler handler = mRef.get();
        if (handler != null && msg != null)
            handler.handleMsg(msg);
    }
}
