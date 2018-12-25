package com.mdove.levelgame.view.cycleviewpager;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.framework.async.WeakHandler;
import com.mdove.levelgame.base.kotlin.Logger;
import com.mdove.levelgame.view.base.SSImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2018/12/25.
 */
public abstract class BaseCycleViewPager extends FrameLayout implements ViewPager.OnPageChangeListener, WeakHandler.IHandler {

    private static final String TAG = "CycleViewPager";

    public Context mContext;

    private ViewPager mViewPager;//实现轮播图的ViewPager

    private LinearLayout mIndicatorLayout; // 指示器

    //    private Handler handler;//每几秒后执行下一张的切换
    private WeakHandler mHandler = new WeakHandler(this);

    private int WHEEL = 100; // 转动

    private int WHEEL_WAIT = 101; // 等待

    private List<View> mViews = new ArrayList<>(); //需要轮播的View，数量为轮播图数量+2

    private ImageView[] mIndicators;    //指示器小圆点

    private boolean isScrolling = false; // 滚动框是否滚动着

    private boolean isCycle = true; // 是否循环，默认为true

    private int delay = 4000; // 默认轮播时间

    private int mCurrentPosition = 0; // 轮播当前位置

    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换

    private ViewPagerAdapter mAdapter;

    private ImageCycleViewListener mImageCycleViewListener;
    private ImageItemShowListener mImageItemShowListener;
    private List<CycleImageEntity> mData;//数据集合

    private int mIndicatorSelected;//指示器图片，被选择状态

    private int mIndicatorUnselected;//指示器图片，未被选择状态

    private final Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (mContext != null) {
                long now = System.currentTimeMillis();
                // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
                if (now - releaseTime > delay - 500) {
                    mHandler.sendEmptyMessage(WHEEL);
                } else {
                    mHandler.sendEmptyMessage(WHEEL_WAIT);
                }
            }
        }
    };

    public BaseCycleViewPager(Context context) {
        this(context, null);
    }

    public BaseCycleViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCycleViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_cycle_view, this, true);
        mViewPager = (ViewPager) findViewById(R.id.cycle_view_pager);
        mIndicatorLayout = (LinearLayout) findViewById(R.id.cycle_indicator);

//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//
//            }
//        };
    }

    /**
     * 设置指示器图片，在setData之前调用
     *
     * @param select   选中时的图片
     * @param unselect 未选中时的图片
     */
    public void setIndicators(int select, int unselect) {
        mIndicatorSelected = select;
        mIndicatorUnselected = unselect;
    }

    public void setItemOnClickListener(ImageCycleViewListener listener) {
        mImageCycleViewListener = listener;
    }

    public void setItemOnShowListener(ImageItemShowListener listener) {
        mImageItemShowListener = listener;
    }


    /**
     * 初始化viewpager
     *
     * @param list         要显示的数据
     * @param showPosition 默认显示位置
     */
    public void setData(List<CycleImageEntity> list, int showPosition) {
        if (list == null || list.size() == 0) {
            return;
        }

        mViews.clear();
        mData = list;

        if (list.size() == 1) {
            isCycle = false;
        }

        if (isCycle) {
            //添加轮播图View，数量为集合数+2
            // 将最后一个View添加进来
            mViews.add(getImageView(mContext, mData.get(mData.size() - 1).imageUrl));
            for (int i = 0; i < mData.size(); i++) {
                mViews.add(getImageView(mContext, mData.get(i).imageUrl));
            }
            // 将第一个View添加进来
            mViews.add(getImageView(mContext, mData.get(0).imageUrl));
        } else {
            //只添加对应数量的View
            for (int i = 0; i < mData.size(); i++) {
                mViews.add(getImageView(mContext, mData.get(i).imageUrl));
            }
        }


        if (mViews == null || mViews.size() == 0) {
            //没有View时隐藏整个布局
            this.setVisibility(View.GONE);
            return;
        }

        int ivSize = mViews.size();
        if (ivSize > 1) {
            // 设置指示器
            mIndicators = new ImageView[ivSize];
            if (isCycle)
                mIndicators = new ImageView[ivSize - 2];
            mIndicatorLayout.removeAllViews();
            for (int i = 0; i < mIndicators.length; i++) {
                mIndicators[i] = new ImageView(mContext);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 0, 10, 0);
                mIndicators[i].setLayoutParams(lp);
                mIndicatorLayout.addView(mIndicators[i]);
            }
            setIndicator(0);
        }

        mAdapter = new ViewPagerAdapter();

        // 默认指向第一项，下方viewPager.setCurrentItem将触发重新计算指示器指向


        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setAdapter(mAdapter);
        if (showPosition < 0 || showPosition >= mViews.size()) {
            showPosition = 0;
        }
        if (isCycle) {
            showPosition = showPosition + 1;
            startCycling();
        }
        mViewPager.setCurrentItem(showPosition);
        if (mImageItemShowListener != null)
            mImageItemShowListener.onImageShow(mData.get(0));
    }

    /**
     * 获取轮播图View
     *
     * @param context
     * @param url
     */
    private View getImageView(Context context, String url) {
        RelativeLayout rl = new RelativeLayout(context);
        //添加一个ImageView，并加载图片
        SSImageView imageView = new SSImageView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(layoutParams);

        loadImage(url, imageView);
        rl.addView(imageView);
        //在Imageview前添加一个半透明的黑色背景，防止文字和图片混在一起
//        ImageView backGround = new ImageView(context);
//        backGround.setLayoutParams(layoutParams);
//        backGround.setBackgroundResource(R.color.transparent);
//        rl.addView(backGround);

        return rl;
    }

    public abstract void loadImage(final String url, final SSImageView imageView);


    /**
     * 设置指示器，和文字内容
     *
     * @param selectedPosition 默认指示器位置
     */
    private void setIndicator(int selectedPosition) {
        try {
            for (ImageView mIndicator : mIndicators) {
                mIndicator.setBackgroundResource(mIndicatorUnselected);
            }
            if (mIndicators.length > selectedPosition) {
                mIndicators[selectedPosition].setBackgroundResource(mIndicatorSelected);
            }
        } catch (Exception e) {
            Log.i(TAG, "指示器路径不正确");
        }
    }

    @Override
    public void handleMsg(Message msg) {
        if (msg.what == WHEEL && mViews.size() > 0) {
            if (!isScrolling) {
                //当前为非滚动状态，切换到下一页
                int posttion = (mCurrentPosition + 1) % mViews.size();
                mViewPager.setCurrentItem(posttion, true);
            }
            releaseTime = System.currentTimeMillis();
            mHandler.removeCallbacks(runnable);
            mHandler.postDelayed(runnable, delay);
            Logger.i(TAG, "handler.postDelayed 1");
            return;

        }
        if (msg.what == WHEEL_WAIT && mViews.size() > 0) {
            mHandler.removeCallbacks(runnable);
            mHandler.postDelayed(runnable, delay);
            Logger.i(TAG, "handler.postDelayed 2");
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(runnable);
    }

    /**
     * 页面适配器 返回对应的view
     *
     * @author Yuedong Li
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public View instantiateItem(@NonNull ViewGroup container, final int position) {
            View v = mViews.get(position);
            if (mImageCycleViewListener != null) {
                v.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mImageCycleViewListener.onImageClick(mData.get(isCycle ? mCurrentPosition - 1 : mCurrentPosition), mCurrentPosition, v);
                    }
                });
            }
            container.addView(v);
            return v;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int arg0) {
        int max = mViews.size() - 1;
        int position = arg0;
        mCurrentPosition = arg0;
        if (isCycle) {
            if (arg0 == 0) {

                //滚动到mView的1个（界面上的最后一个），将mCurrentPosition设置为max - 1
                mCurrentPosition = max - 1;
            } else if (arg0 == max) {
                //滚动到mView的最后一个（界面上的第一个），将mCurrentPosition设置为1
                mCurrentPosition = 1;
            }
            position = mCurrentPosition - 1;
        }
        setIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 1) { // viewPager在滚动
            isScrolling = true;
            return;
        } else if (state == 0) { // viewPager滚动结束
            releaseTime = System.currentTimeMillis();
            //跳转到第mCurrentPosition个页面（没有动画效果，实际效果页面上没变化）
            mViewPager.setCurrentItem(mCurrentPosition, false);
            if (mData.size() == 1 && (mCurrentPosition == 2 || mCurrentPosition == 0)) {
                mImageItemShowListener.onImageShow(mData.get(0));
            } else {
                mImageItemShowListener.onImageShow(mData.get(mCurrentPosition - 1));
            }
        }
        isScrolling = false;
    }

    /**
     * 为textview设置文字
     *
     * @param textView
     * @param text
     */
    public static void setText(TextView textView, String text) {
        if (text != null && textView != null) textView.setText(text);
    }

    /**
     * 为textview设置文字
     *
     * @param textView
     * @param text
     */
    public static void setText(TextView textView, int text) {
        if (textView != null) setText(textView, text + "");
    }

    /**
     * 是否处于循环状态
     *
     * @return
     */
    public boolean isCycle() {
        return isCycle;
    }

    public void startCycling() {
        if (isCycle) {
            mHandler.postDelayed(runnable, delay);
            Logger.i(TAG, "handler.postDelayed 3");
        }
    }

    /**
     * 刷新数据，当外部视图更新后，通知刷新数据
     */
    public void refreshData() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置轮播暂停时间,单位毫秒（默认4000毫秒）
     *
     * @param delay
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * 轮播控件的监听事件
     *
     * @author minking
     */
    public interface ImageCycleViewListener {

        /**
         * 单击图片事件
         *
         * @param info
         * @param position
         * @param imageView
         */
        void onImageClick(CycleImageEntity info, int position, View imageView);
    }

    public interface ImageItemShowListener {

        void onImageShow(CycleImageEntity info);
    }

    private int lastX = -1;
    private int lastY = -1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isCycle) {
                    // 保证子View能够接收到Action_move事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isCycle) {
                    dealtX += Math.abs(x - lastX);
                    dealtY += Math.abs(y - lastY);
                    Log.i(TAG, "dealtX:=" + dealtX + " dealtY:=" + dealtY);
                    // 判断拦截条件：是否是左右滑动
                    getParent().requestDisallowInterceptTouchEvent(dealtX >= dealtY);
                    lastX = x;
                    lastY = y;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
