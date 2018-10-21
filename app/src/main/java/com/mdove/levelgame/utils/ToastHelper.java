package com.mdove.levelgame.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.mdove.levelgame.App;
import com.mdove.levelgame.BuildConfig;
import com.mdove.levelgame.R;


/**
 * @author MDove on 18/2/10
 */
public class ToastHelper {

    private static boolean DEBUG = BuildConfig.DEBUG;
    private static Toast mToast;

    private static boolean CUSTOM_STYLE = true;

    public static void setCustomStyle(boolean enable) {
        CUSTOM_STYLE = enable;
    }

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static void shortToast(String msg) {
        toast(msg, Toast.LENGTH_SHORT, CUSTOM_STYLE);
    }

    public static void shortToast(int resId) {
        toast(App.getAppContext().getString(resId), Toast.LENGTH_SHORT, CUSTOM_STYLE);
    }

    public static void longToast(String msg) {
        toast(msg, Toast.LENGTH_LONG, CUSTOM_STYLE);
    }

    public static void longToast(int resId) {
        toast(App.getAppContext().getString(resId), Toast.LENGTH_LONG, CUSTOM_STYLE);
    }

    public static void debugToast(String msg) {
        if (DEBUG) {
            shortToast(msg);
        }
    }

    public static void debugToast(int resId) {
        if (DEBUG) {
            shortToast(resId);
        }
    }

    private static Toast getToast() {
        if (mToast == null) {
            Context appContext = App.getAppContext();
            mToast = new Toast(appContext);
            mToast.setDuration(Toast.LENGTH_SHORT);
            LayoutInflater inflate = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflate.inflate(R.layout.view_toast, null);
            mToast.setView(v);
        }
        return mToast;
    }

    /**
     * @param toast
     * @param length How long to display the message.  Either {@link Toast#LENGTH_SHORT} or
     *               {@link Toast#LENGTH_LONG}
     * @param custom
     */
    private static final void toast(final String toast, final int length, final boolean custom) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showToast(toast, length, custom);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    showToast(toast, length, custom);
                }
            });
        }
    }

    private static final void showToast(String toast, int length, boolean custom) {
        if (!custom) {
            Toast.makeText(App.getAppContext(), toast, length).show();
        } else {
            try {
                Toast t = getToast();
                t.setText(toast);
                t.setDuration(length);
                t.show();
            } catch (Exception e) {
                Toast.makeText(App.getAppContext(), toast, length).show();
            }
        }
    }
}
