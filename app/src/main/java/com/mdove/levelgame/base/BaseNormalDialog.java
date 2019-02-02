package com.mdove.levelgame.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.mdove.levelgame.R;
import com.mdove.levelgame.databinding.DialogBaseNormalBinding;
import com.mdove.levelgame.utils.SystemUtils;

/**
 * Created by MDove on 18/10/28.
 */
public class BaseNormalDialog extends AppCompatDialog {
    private DialogBaseNormalBinding binding;
    private BaseDialogListener listener;
    private boolean isOnlyOk = false;

    public BaseNormalDialog(Context context) {
        super(context, R.style.BaseDialog);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_base_normal,
                null, false);
        setContentView(binding.getRoot());
        WindowManager.LayoutParams paramsWindow = getWindow().getAttributes();
        paramsWindow.width = getWindowWidth();
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    public void setContent(String content) {
        if (binding != null) {
            binding.tvContent.setText(Html.fromHtml(content));
        }
    }

    public void setTitle(String title) {
        if (binding != null) {
            binding.tvTitle.setText(Html.fromHtml(title));
        }
    }

    public void setCancelText(String text) {
        if (binding != null) {
            binding.btnCancel.setText(Html.fromHtml(text));
        }
    }

    public void setOkText(String text) {
        if (binding != null) {
            binding.btnSubmit.setText(Html.fromHtml(text));
        }
    }

    public void setOnlyOk(boolean isOnlyOk) {
        this.isOnlyOk = isOnlyOk;
    }

    public void setOkListener(BaseDialogListener listener) {
        this.listener = listener;
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
        if (listener == null) {
            binding.btnCancel.setVisibility(View.GONE);
            binding.btnSubmit.setVisibility(View.GONE);
        }

        if (isOnlyOk) {
            binding.btnSubmit.setVisibility(View.VISIBLE);
            binding.btnCancel.setVisibility(View.GONE);
        }

        binding.btnCancel.setOnClickListener(v -> dismiss());
        binding.btnSubmit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick();
            }
            dismiss();
        });
    }

    public interface BaseDialogListener {
        void onClick();
    }
}
