package com.mdove.levelgame.utils.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.mdove.levelgame.base.listener.OnDefaultAlertListener;


/**
 * Created by MDove on 2018/4/16.
 */

public class AlertDialogUtils {

    public static void defaultAlertDialog(@NonNull final Activity activity, @NonNull String title, @NonNull String content, @NonNull final OnDefaultAlertListener listener) {
        defaultAlertDialog(activity, title, content, "没问题", "不行", listener);
    }

    public static void defaultAlertDialog(@NonNull final Activity activity, @NonNull String title, @NonNull String content, @NonNull String ok, @NonNull String cancel, @NonNull final OnDefaultAlertListener listener) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onClickPositive();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onClickNegative();
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
}
