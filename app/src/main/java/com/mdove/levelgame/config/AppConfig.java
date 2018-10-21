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

    public static void setHoldWeaponsId(long id) {
        SharedPreferences.Editor editor = initSharedPreferences().edit();
        editor.putLong(KEY_HOLD_WEAPONS_ID, id);
        editor.apply();
    }

    public static boolean isFirstLogin(){
        SharedPreferences preferences = initSharedPreferences();
        return preferences.getBoolean(KEY_FIRST_LOGIN, false);
    }

    public static void setFirstLogin() {
        SharedPreferences.Editor editor = initSharedPreferences().edit();
        editor.putBoolean(KEY_FIRST_LOGIN, true);
        editor.apply();
    }
}
