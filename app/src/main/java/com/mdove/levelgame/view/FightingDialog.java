package com.mdove.levelgame.view;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;

import com.mdove.levelgame.R;

/**
 * Created by MDove on 2018/11/1.
 */

public class FightingDialog extends AppCompatDialog {
    public FightingDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_fighting);
    }
}
