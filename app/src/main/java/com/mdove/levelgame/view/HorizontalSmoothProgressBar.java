package com.mdove.levelgame.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.mdove.levelgame.R;

/**
 * @author xubin@jiandaola.com
 */
public class HorizontalSmoothProgressBar extends SmoothProgressBar {
    private final Paint foregroundPaint;
    private final Paint backgroundPaint;

    public HorizontalSmoothProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeCap(Paint.Cap.ROUND);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        TypedArray attributes =
                context.obtainStyledAttributes(attrs, R.styleable.HorizontalSmoothProgressBar);
        foregroundPaint.setColor(attributes.getColor(R.styleable
                .HorizontalSmoothProgressBar_hspForegroundProgressColor, 0));
        backgroundPaint.setColor(attributes.getColor(R.styleable
                .HorizontalSmoothProgressBar_hspBackgroundProgressColor, 0));
        attributes.recycle();
    }

    public void setForegroundProgressColor(int color) {
        this.foregroundPaint.setColor(color);
    }

    public void setBackgroundProgressColor(int color) {
        this.backgroundPaint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int strokeWidth = getMeasuredHeight();
        this.foregroundPaint.setStrokeWidth(strokeWidth);
        this.backgroundPaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void drawForeground(Canvas canvas, float currentProgress) {
        int width = getMeasuredWidth();
        float currentWidth = width * currentProgress / 100;
        float y = getMeasuredHeight() / 2;

        canvas.drawLine(0, y, currentWidth, y, foregroundPaint);
    }

    @Override
    protected void drawBackground(Canvas canvas) {
        float y = getMeasuredHeight() / 2;
        canvas.drawLine(0, y, getMeasuredWidth(), y, backgroundPaint);
    }
}
