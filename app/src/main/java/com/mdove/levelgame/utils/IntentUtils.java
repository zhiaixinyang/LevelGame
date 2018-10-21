package com.mdove.levelgame.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @author MDove on 2018/4/16.
 */
public class IntentUtils {

    public static void launchCurrentAppMarket(@NonNull Context context) {
        launchMarket(context, context.getPackageName());
    }

    public static void launchMarket(@NonNull Context context, @NonNull String packageName) {
        launchMarket(context, packageName, null);
    }

    public static void launchMarket(@NonNull Context context, @NonNull String packageName, @Nullable String mediaSource) {
        String url = "market://details?id=" + packageName;
        if (!TextUtils.isEmpty(mediaSource)) {
            url = appendUrlParams(url, mediaSource);
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            String targetUrl = "https://play.google.com/store/apps/details?id=" + packageName;
            if (!TextUtils.isEmpty(mediaSource)) {
                targetUrl = appendUrlParams(targetUrl, mediaSource);
            }
            uri = Uri.parse(targetUrl);
            intent = new Intent(Intent.ACTION_VIEW, uri);
            try {
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void launchSystemShareDialog(@NonNull Context context, @NonNull String shareContent, String
            shareDialogTitle) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);
        context.startActivity(Intent.createChooser(intent, shareDialogTitle));
    }

    public static void launchByUri(@NonNull Context context, @NonNull String url) {
        launchByUri(context, null, url);
    }

    public static void launchByUri(@NonNull Context context, @Nullable String targetPkg, @NonNull String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (!TextUtils.isEmpty(targetPkg)) {
            intent.setPackage(targetPkg);
        }
        boolean enable = resolveActivity(context, intent);
        if (!enable) {
            return;
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }

    public static boolean resolveActivity(@NonNull Context context, @NonNull Intent intent) {
        PackageManager pm = context.getPackageManager();
        ComponentName cn = intent.resolveActivity(pm);
        return cn != null;
    }

    public static boolean isReceiverRegisted(@NonNull Context context, @NonNull Intent intent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryBroadcastReceivers(intent, 0);
        return resolveInfos != null && !resolveInfos.isEmpty();
    }

    /**
     * 判断程序是否安装
     *
     * @param context
     * @param pkgName
     * @return
     */
    public static boolean isAppInstalled(@NonNull Context context, @NonNull String pkgName) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(pkgName, 0);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return installed;
    }

    /**
     * 打开安装的程序
     *
     * @param context
     * @param pkgName
     */
    public static void startupApp(@NonNull Context context, @NonNull String pkgName) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkgName);
            if (intent != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                context.startActivity(intent);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 打开已安装程序的制定Activity
     *
     * @param context
     * @param pkgName
     * @param activityName
     */
    public static void startupAppActivity(@NonNull Context context, @NonNull String pkgName, @NonNull String activityName) {
        ComponentName componetName = new ComponentName(pkgName, activityName);
        try {
            Intent intent = new Intent();
            intent.setComponent(componetName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void openOverlayPermissionActivity(@NonNull Activity activity, @NonNull String pkg, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + pkg));
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 根据 指定的Intent 查询匹配的Activity信息
     *
     * @param cxt
     * @param intent
     * @return
     */
    public static List<ResolveInfo> getActivityInfo(@NonNull Context cxt, @NonNull Intent intent) {
        return cxt.getPackageManager().queryIntentActivities(intent, 0);
    }

    /**
     * 拼接 get 参数
     *
     * @param url       例如 https://www.baidu.com?from=1
     * @param extraPair 例如 utm_medium=draw&utm_source=collage
     * @return
     */
    static String appendUrlParams(@NonNull String url, String extraPair) {
        if (TextUtils.isEmpty(extraPair)) {
            return url;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (!url.contains("?")) {
            sb.append('?');
        } else if (!url.endsWith("?") && !url.endsWith("&")) {
            sb.append("&");
        }
        sb.append(extraPair);
        String result = sb.toString();
        if (result.endsWith("&")) {
            result = result.substring(0, sb.length() - 1);
        }
        return result;
    }
}
