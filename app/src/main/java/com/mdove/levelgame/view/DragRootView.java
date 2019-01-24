package com.mdove.levelgame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import com.mdove.levelgame.R;

/**
 * DetailRootView
 * Created by zhaoshe on 2017/2/6.
 */
public class DragRootView extends RelativeLayout {

    private static final int DEFAULT_STATUS_BAR_COLOR = R.color.black_trans_50;

    private long mDownTime;
    private float[] mDownPoint;
    private boolean mCanceled;
    private int mStatusBarHeight;
    private int mStatusBarColor = DEFAULT_STATUS_BAR_COLOR;

    private Paint mPaint;

    private final int mTouchSlop;
    private final MyDragCallback mDragCallback = new MyDragCallback();
    private final ViewDragHelper mViewDragHelper = ViewDragHelper.create(this, mDragCallback);

    private OnInterceptClickListener mInterceptClickListener;
    private OnDragListener mOnDragListener;
    private OnTouchListener mOnInterceptTouchListener;
    private boolean isSlidingOut;

    public DragRootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(mStatusBarColor));
    }

    public void setStatusBarColor(int color) {
        mStatusBarColor = color;
    }

    public void setOnDragListener(OnDragListener onDragListener) {
        mOnDragListener = onDragListener;
    }

    public boolean slideOut() {
        if (isSlidingOut) {
            return true;
        }
        final View dragView = findViewById(R.id.root_drag_view);
        if (dragView == null) {
            return false;
        }

        isSlidingOut = true;

        final Runnable task = new Runnable() {
            @Override
            public void run() {
                mDragCallback.tryCaptureView(dragView, 0);
                mViewDragHelper.smoothSlideViewTo(dragView, dragView.getLeft(), dragView.getTop() + dragView.getHeight());
                ViewCompat.postInvalidateOnAnimation(DragRootView.this);
            }
        };

        if (dragView.getHeight() <= 0) {
            post(new Runnable() {
                @Override
                public void run() {
                    task.run();
                }
            });
        } else {
            task.run();
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mOnInterceptTouchListener != null
                && mOnInterceptTouchListener.onTouch(this, event)) {
            return true;
        }
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDragCallback.determinedDrag = false;
                mDownTime = System.currentTimeMillis();
                mDownPoint = new float[]{event.getX(), event.getY()};
                mCanceled = false;
                break;
            case MotionEvent.ACTION_UP:
                if (!mCanceled
//                        && System.currentTimeMillis() - mDownTime <= ViewConfiguration.getLongPressTimeout()
                        && mTouchSlop >= Math.abs(event.getX() - mDownPoint[0])
                        && mTouchSlop >= Math.abs(event.getY() - mDownPoint[1])) {
                    if (mInterceptClickListener != null) {
                        return mInterceptClickListener.onClick(this);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTouchSlop < Math.abs(event.getX() - mDownPoint[0])
                        || mTouchSlop < Math.abs(event.getY() - mDownPoint[1])) {
                    mCanceled = true;
                }
                break;
            default:
                mCanceled = true;
                break;
        }

        return mViewDragHelper.shouldInterceptTouchEvent(event) || super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        super.draw(canvas);
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setInterceptClickListener(OnInterceptClickListener interceptClickListener) {
        mInterceptClickListener = interceptClickListener;
    }

    public interface OnInterceptClickListener {
        boolean onClick(View view);
    }

    private class MyDragCallback extends ViewDragHelper.Callback {

        int originTop;
        boolean determinedDrag;

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            if (determinedDrag) {
                return false;
            }
            determinedDrag = true;
            if (mViewDragHelper.getViewDragState() == ViewDragHelper.STATE_IDLE
                    && child.getId() == R.id.root_drag_view
                    && (mOnDragListener == null || mOnDragListener.canDragNow(child))) {
                getParent().requestDisallowInterceptTouchEvent(true);
                originTop = child.getTop();
                return true;
            }
            return false;
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return getHeight() - getPaddingTop();
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            boolean in;
            if (yvel > 0) {
                in = false;
            } else if (yvel < 0) {
                in = true;
            } else {
                int topDelta = releasedChild.getTop() - originTop;
                if (topDelta > releasedChild.getHeight() / 2) {
                    in = false;
                } else {
                    in = true;
                }
            }

            mViewDragHelper.smoothSlideViewTo(releasedChild, releasedChild.getLeft(), in ? originTop : originTop + releasedChild.getHeight());
            ViewCompat.postInvalidateOnAnimation(DragRootView.this);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if (top < originTop) {
                return originTop;
            }
            return top;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
//            View bgView = findViewById(R.id.bg_view);
//            if (bgView != null) {
//                int delta = changedView.getTop() - originTop;
//                float alpha = 1 - (float) delta / changedView.getHeight();
//                bgView.setAlpha(alpha);
//                mOnDragListener.onAlphaCallBack((int) (255 * alpha));
//            }
            int delta = changedView.getTop() - originTop;
            float alpha = 1 - (float) delta / changedView.getHeight();
            mPaint.setAlpha((int) (128 * alpha));
            ViewCompat.postInvalidateOnAnimation(DragRootView.this);

            if (mViewDragHelper.getViewDragState() == ViewDragHelper.STATE_SETTLING
                    && top == originTop + changedView.getHeight()
                    && mOnDragListener != null) {
                mOnDragListener.onExit(changedView);
            }
        }
    }

    public interface OnDragListener {
        boolean canDragNow(View dragView);

        void onExit(View exitView);
    }
}
