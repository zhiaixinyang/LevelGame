package com.mdove.levelgame.view.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MDove on 2018/12/25.
 */
public class SSImageView extends AppCompatImageView {

    public SSImageView(Context context) {
        super(context);
    }

    public SSImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SSImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean shouldReportCrash = true;

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        Drawable drawable = getDrawable();
        if(drawable!=null)
            drawable.setVisible(getVisibility()==VISIBLE, false);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
            if (shouldReportCrash) {
                shouldReportCrash = false;
            }
        }
    }
}
