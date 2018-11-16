package com.mdove.levelgame.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xubin@jiandaola.com
 */
public abstract class SmoothProgressBar extends View {
    public static final int MIN_PROGRESS_DURATION = 100;
    public static final int TOTAL_PROGRESS_DURATION = 1800;
    public static final int LAST_DELAY_DURATION = 300;

    public static final int AUTO_INCREMENT_TOTAL_DURATION = 60 * 1000;// 120s
    private final List<WeakReference<SmoothProgressListener>> progressListeners =
            new ArrayList<>();
    private boolean isAutoIncrement = false;
    private float currentProgress = 0;
    private float targetProgress;
    private ValueAnimator progressAnimator;
    private ValueAnimator autoIncrementAnimator;

    public SmoothProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected abstract void drawForeground(Canvas canvas, float currentProgress);

    protected abstract void drawBackground(Canvas canvas);

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAutoIncrement();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawForeground(canvas, currentProgress);
        invalidate();
    }

    // xml-> app:progress
    @BindingAdapter(value = "progress")
    public static void setDateBindingProgress(HorizontalSmoothProgressBar bar, int progress) {
        bar.setProgress(progress);
    }

    @InverseBindingAdapter(attribute = "progress")
    public static int getDateBindingProgress(HorizontalSmoothProgressBar bar) {
        return bar.getProgress();
    }

    public int getProgress() {
        return (int) targetProgress;
    }

    public void setProgress(int progress) {
        if (this.targetProgress == progress) {
            return;
        }
        this.targetProgress = progress;
        if (progress <= 0) {
            return;
        }
        stopAutoIncrement();
        if (progressAnimator != null && progressAnimator.isRunning()) {
            progressAnimator.cancel();
        }
        int duration;
        if (targetProgress == 100) {
            duration = LAST_DELAY_DURATION;
        } else {
            float deltaProgress = targetProgress - currentProgress;
            duration = (int) (TOTAL_PROGRESS_DURATION * deltaProgress / 100);
            duration = Math.max(duration, MIN_PROGRESS_DURATION);
        }
        progressAnimator =
                ValueAnimator.ofFloat(currentProgress, targetProgress).setDuration(duration);
        progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentProgress = (Float) animation.getAnimatedValue();
                if (currentProgress == 100) {
                    onProgressComplete();
                }
                invalidate();
            }
        });
        progressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startAutoIncrement();
            }
        });
        progressAnimator.start();
    }

    public void resetProgress(int progress) {
        currentProgress = targetProgress = progress;
        invalidate();
    }

    public void setAutoIncrement(boolean enable) {
        this.isAutoIncrement = enable;
        startAutoIncrement();
    }

    public void addProgressListener(SmoothProgressListener listener) {
        if (listener == null) {
            return;
        }
        synchronized (progressListeners) {
            Iterator<WeakReference<SmoothProgressListener>> iterator = progressListeners.iterator();
            while (iterator.hasNext()) {
                WeakReference<SmoothProgressListener> ref = iterator.next();
                SmoothProgressListener progressListener = ref.get();
                if (progressListener == null) {
                    iterator.remove();
                } else if (progressListener == listener) {
                    return;
                }
            }
            progressListeners.add(new WeakReference<>(listener));
        }
    }

    private void startAutoIncrement() {
        if (!isAutoIncrement) {
            return;
        }
        stopAutoIncrement();
        if (currentProgress >= 99) {
            return;
        }
        int duration = (int) (AUTO_INCREMENT_TOTAL_DURATION * (100 - currentProgress) / 100);
        autoIncrementAnimator =
                ValueAnimator.ofFloat(currentProgress, 99).setDuration(duration);
        autoIncrementAnimator.setInterpolator(new LinearInterpolator());
        autoIncrementAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentProgress = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        autoIncrementAnimator.start();
    }

    private void stopAutoIncrement() {
        if (autoIncrementAnimator != null && autoIncrementAnimator.isRunning()) {
            autoIncrementAnimator.cancel();
        }
    }

    private void onProgressComplete() {
        List<SmoothProgressListener> validListeners = new ArrayList<>();
        synchronized (progressListeners) {
            Iterator<WeakReference<SmoothProgressListener>> iterator = progressListeners.iterator();
            while (iterator.hasNext()) {
                WeakReference<SmoothProgressListener> ref = iterator.next();
                SmoothProgressListener progressListener = ref.get();
                if (progressListener == null) {
                    iterator.remove();
                } else {
                    validListeners.add(progressListener);
                }
            }
        }
        for (SmoothProgressListener listener : validListeners) {
            listener.onProgressComplete();
        }
    }

    public interface SmoothProgressListener {
        void onProgressComplete();
    }
}
