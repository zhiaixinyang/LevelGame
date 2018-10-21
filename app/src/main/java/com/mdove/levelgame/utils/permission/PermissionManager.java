package com.mdove.levelgame.utils.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mdove.levelgame.R;

/**
 * @author MDove on 2018/4/16.
 */
public class PermissionManager {

    public static void askForPermission(@NonNull final Activity activity, final int requestCode, @NonNull final String[] permissions, @Nullable final PermissionGrantCallback callback) {
        new AlertDialog.Builder(activity)
                .setTitle(R.string.permission_title)
                .setMessage(R.string.permissions_message)
                .setPositiveButton("没问题", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.requestPermission(activity, requestCode, permissions, callback);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("不行", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
}
