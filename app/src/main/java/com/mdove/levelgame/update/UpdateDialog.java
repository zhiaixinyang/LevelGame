package com.mdove.levelgame.update;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.mdove.levelgame.R;
import com.mdove.levelgame.databinding.DialogUpdateBinding;
import com.mdove.levelgame.utils.SystemUtils;

/**
 * Created by MDove on 18/11/14.
 */
public class UpdateDialog extends AppCompatDialog {
    private DialogUpdateBinding binding;
    private String mAppUrl;
    private float star = 0;
    private Context mContext;

    public UpdateDialog(Context context, String appUrl) {
        super(context, R.style.UpgradeDialog);
        mContext = context;
        mAppUrl = appUrl;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_update,
                null, false);
        setContentView(binding.getRoot());
        WindowManager.LayoutParams paramsWindow = getWindow().getAttributes();
        paramsWindow.width = getWindowWidth();
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    protected int getWindowWidth() {
        float percent = 0.9f;
        WindowManager wm = this.getWindow().getWindowManager();
        int screenWidth = SystemUtils.getScreenWidth(wm);
        int screenHeight = SystemUtils.getScreenHeight(wm);
        return (int) (screenWidth > screenHeight
                ? screenHeight * percent
                : screenWidth * percent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                UpdateAppService.start(mContext, mAppUrl);
            }
        });
    }
}
