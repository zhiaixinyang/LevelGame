package com.mdove.levelgame.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.mdove.levelgame.App;

/**
 * Created by MDove on 2018/2/15.
 */

public class AppConfig implements IAppConfig {
    private static final String PREFS_FILE = "level_game";
    private static SharedPreferences sPrefs;

    private static SharedPreferences initSharedPreferences() {
        if (sPrefs == null) {
            sPrefs = App.getAppContext().getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        }
        return sPrefs;
    }

    public static boolean isHasLogin() {
        SharedPreferences preferences = initSharedPreferences();
        return preferences.getBoolean(KEY_HAS_LOGIN, false);
    }

    public static void setHasLogin() {
        setHasLogin(true);
    }

    public static void setHasLogin(boolean login) {
        SharedPreferences.Editor editor = initSharedPreferences().edit();
        editor.putBoolean(KEY_HAS_LOGIN, login);
        editor.apply();
    }

    public static boolean isShowGuide() {
        SharedPreferences preferences = initSharedPreferences();
        return preferences.getBoolean(KEY_IS_SHOW_GUIDE, false);
    }

    public static void setSwitchBigMonster(boolean bigMonster) {
        SharedPreferences.Editor editor = initSharedPreferences().edit();
        editor.putBoolean(KEY_SWITCH_BIG_MONSTER, bigMonster);
        editor.apply();
    }

    public static boolean isSwitchBigMonster() {
        SharedPreferences preferences = initSharedPreferences();
        return preferences.getBoolean(KEY_SWITCH_BIG_MONSTER, true );
    }


    public static void setShowGuide() {
        setShowGuide(true);
    }

    public static void setShowGuide(boolean isShow) {
        SharedPreferences.Editor editor = initSharedPreferences().edit();
        editor.putBoolean(KEY_IS_SHOW_GUIDE, isShow);
        editor.apply();
    }


    public static long getDBVersion() {
        SharedPreferences preferences = initSharedPreferences();
        return preferences.getLong(KEY_UPDATE_DB, 0);
    }

    public static void setDBVersion(long version) {
        SharedPreferences.Editor editor = initSharedPreferences().edit();
        editor.putLong(KEY_UPDATE_DB, version);
        editor.apply();
    }

    public static void setAppOrderTodayTime(long time) {
        SharedPreferences.Editor editor = initSharedPreferences().edit();
        editor.putLong(KEY_ORDER_TODAY_TIME, time);
        editor.apply();
    }

    public static long getAppOrderTodayTime() {
        SharedPreferences preferences = initSharedPreferences();
        return preferences.getLong(KEY_ORDER_TODAY_TIME, 0);
    }
}
