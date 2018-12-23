package com.mdove.levelgame.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.mdove.levelgame.R;

/**
 * Created by MDoveon 2018/12/17.
 * 闪光滑动控件，内置一张闪光图，平滑地从做到右滑动，无限重复
 * 做的比较简单，定制需求可以再扩展
 */
public class BlinkSlideView extends FrameLayout {
    private static final String TAG = BlinkSlideView.class.getSimpleName();
    private static final int DEFAULT_ANIMATION_DURATION = 1500;

    private boolean autoStart;
    private int repeat;
    private int round;

    private int roundTopLeft;
    private int roundTopRight;
    private int roundBottomLeft;
    private int roundBottomRight;

    private float maskOffsetX;
    private float maskOffsetY;
    private RectF maskRect;
    private Paint maskPaint;
    private ValueAnimator maskAnimator;

    private Bitmap maskBitmap;
    private Path path;
    /** 某些机型需要关闭硬件加速*/
    private boolean checkClipPath;

    private boolean isAnimationStarted;
    private int shimmerAnimationDuration = DEFAULT_ANIMATION_DURATION;

    private ViewTreeObserver.OnPreDrawListener startAnimationPreDrawListener;

    public BlinkSlideView(Context context) {
        this(context, null);
    }

    public BlinkSlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlinkSlideView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setWillNotDraw(false);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BlinkSlideView,
                0, 0);

        try {
            autoStart = a.getBoolean(R.styleable.BlinkSlideView_auto_start, true);
            repeat = a.getInteger(R.styleable.BlinkSlideView_repeat, 0);
            round = a.getDimensionPixelSize(R.styleable.BlinkSlideView_round, 0);

            roundTopLeft = a.getDimensionPixelSize(R.styleable.BlinkSlideView_round_top_left, 0);
            roundTopRight = a.getDimensionPixelSize(R.styleable.BlinkSlideView_round_top_right, 0);
            roundBottomLeft = a.getDimensionPixelSize(R.styleable.BlinkSlideView_round_bottom_left, 0);
            roundBottomRight = a.getDimensionPixelSize(R.styleable.BlinkSlideView_round_bottom_right, 0);
        } finally {
            a.recycle();
        }

        if (getVisibility() == VISIBLE && autoStart) {
            startBlinkAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        resetShimmering();
        super.onDetachedFromWindow();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (!isAnimationStarted || getWidth() <= 0 || getHeight() <= 0) {
            super.dispatchDraw(canvas);
        } else {
            dispatchDrawShimmer(canvas);
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE && autoStart) {
            startBlinkAnimation();
        } else {
            stopBlinkAnimation();
        }
    }

    public void startBlinkAnimation() {
        if (isAnimationStarted) {
            return;
        }

        if (getWidth() == 0) {
            startAnimationPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    getViewTreeObserver().removeOnPreDrawListener(this);
                    startBlinkAnimation();

                    return true;
                }
            };

            getViewTreeObserver().addOnPreDrawListener(startAnimationPreDrawListener);

            return;
        }

        Animator animator = getShimmerAnimation();
        animator.start();
        isAnimationStarted = true;
    }

    public void stopBlinkAnimation() {
        if (startAnimationPreDrawListener != null) {
            getViewTreeObserver().removeOnPreDrawListener(startAnimationPreDrawListener);
        }

        resetShimmering();
    }

    private void dispatchDrawShimmer(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        //兼容性 某些4.0.3 4.0.4需要关闭硬件加速
        if (!checkClipPath) {
            try {
                canvas.clipPath(path);
            } catch (UnsupportedOperationException exception) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                try {
                    canvas.clipPath(path);
                } catch (UnsupportedOperationException exception2) {
                    // shouldn't happen, but just in case
                }
            }
            checkClipPath = true;
        } else {
            canvas.clipPath(path);
        }
        drawShimmer(canvas);
        canvas.restore();
    }

    private void drawShimmer(Canvas destinationCanvas) {
        createShimmerPaint();

        destinationCanvas.save();
        //横向滚到，纵向居中
        destinationCanvas.translate(maskOffsetX, maskOffsetY);
        destinationCanvas.drawRect(maskRect, maskPaint);

        destinationCanvas.restore();
    }

    private void resetShimmering() {
        if (maskAnimator != null) {
            maskAnimator.end();
            maskAnimator.removeAllUpdateListeners();
        }

        maskAnimator = null;
        maskPaint = null;
        isAnimationStarted = false;

        releaseBitMaps();
    }

    private void releaseBitMaps() {
        if (maskBitmap != null) {
            maskBitmap.recycle();
            maskBitmap = null;
        }
    }

    private void createShimmerPaint() {
        if (maskPaint != null) {
            return;
        }

        BitmapShader maskBitmapShader = new BitmapShader(maskBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        maskPaint = new Paint();
        maskPaint.setAntiAlias(true);
        maskPaint.setDither(true);
        maskPaint.setFilterBitmap(true);
        maskPaint.setShader(maskBitmapShader);
    }

    /**
     * 在这里初始化资源
     * @return
     */
    private Animator getShimmerAnimation() {
        if (maskAnimator != null) {
            return maskAnimator;
        }

        if (maskBitmap == null) {
            maskBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blink_slide_img);
        }

        if (maskRect == null) {
            maskRect = calculateBitmapMaskRect();
        }

        //限定显示区域
        path = new Path();
        if (roundTopLeft > 0 || roundTopRight > 0 || roundBottomLeft > 0 || roundBottomRight > 0) {
            path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                    new float[]{
                            roundTopLeft, roundTopLeft,
                            roundTopRight, roundTopRight,
                            roundBottomRight, roundBottomRight,
                            roundBottomLeft, roundBottomLeft}, Path.Direction.CCW);
        } else if (round > 0) {
            path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), round, round, Path.Direction.CCW);
        } else {
            path.addRect(new RectF(0, 0, getWidth(), getHeight()), Path.Direction.CCW);
        }

        //移动终点
        final int animationToX = getWidth();
        //移动起点
        final float animationFromX = -maskRect.width();
        //移动全长
        final float shimmerAnimationFullLength = animationToX - animationFromX;

        maskOffsetY = getHeight() / 2 - maskRect.height() / 2;

        maskAnimator = ValueAnimator.ofFloat(0, shimmerAnimationFullLength);
        maskAnimator.setDuration(shimmerAnimationDuration);
        if (repeat > 0) {
            maskAnimator.setRepeatCount(repeat);
        } else {
            maskAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        }

        maskAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                maskOffsetX = animationFromX + (float)animation.getAnimatedValue();

                invalidate();
            }
        });

        return maskAnimator;
    }

    private RectF calculateBitmapMaskRect() {
        return new RectF(0, 0, maskBitmap.getWidth(), maskBitmap.getHeight());
    }

}