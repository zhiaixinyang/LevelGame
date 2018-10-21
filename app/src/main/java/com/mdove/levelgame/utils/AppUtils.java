package com.mdove.levelgame.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by MDove on 2018/1/20.
 */

public class AppUtils {
    public static String getAPPVersionCodeFromAPP(Context context) {
        int currentVersionCode = 0;
        String appVersionName = null;
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionName;
    }
}
