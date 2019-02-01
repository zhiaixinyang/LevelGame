package com.mdove.levelgame.base.sliding

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SlidingPaneLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.mdove.levelgame.base.BaseActivity

/**
 * 利用SlidingPaneLayout来处理滑动关闭的Activity。
 *
 * 旧的BuzzAbsSlideBackActivity代码过于久远，而且有严重问题：
 *
 *    在一个播放视频的界面里，进入任意BuzzAbsSlideBackActivity 子类里，滑动退出都会导致崩溃，也没有具体的上层 Java Crash信息
 *    具体可以参考：TTINTHELO-1247
 *    https://jira.bytedance.com/browse/TTINTHELO-1247?jql=project%20%3D%20TTINTHELO%20AND%20status%20in%20(Open%2C%20%22In%20Progress%22%2C%20Reopened)%20AND%20fixVersion%20%3D%20190_VersionB%20AND%20reporter%20in%20(jiangjinlian%2C%20yangyang.66%2C%20yuanqingze%2C%20lijingying%2C%20jiangshantao%2C%20gongyusong%2C%20maxiangqian%2C%20lina.02%2C%20wanglixuan)%20ORDER%20BY%20assignee%20ASC%2C%20priority%20DESC%2C%20updated%20DESC
 *
 * 目前暂时无解，另外新的实现方式也更加容易维护，由官方维护代码！
 *
 * 继承BuzzAbsSlideCloseActivity的子类，必须配合透明主题：android:theme="@style/BuzzTransparentTheme"
 *
 * @author   MDove
 * @since    2019/2/01
 */
abstract class AbsSlideCloseActivity : BaseActivity(), SlidingPaneLayout.PanelSlideListener {

    private lateinit var mSlidingPaneLayout: PagerEnabledSlidingPaneLayout
    private lateinit var mContentView: FrameLayout


    var slideable
        get() = mSlidingPaneLayout?.prohibitSideslip
        set(value) {
            mSlidingPaneLayout?.prohibitSideslip = !value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (enableSlideClose()) {
            initSlidingPaneLayout()
        }
//        mImmersedStatusBarHelper?.setStatusBarColor(android.R.color.transparent)
    }

    fun setSlideContentView(layoutId: Int) {
        setSlideContentView(layoutInflater.inflate(layoutId, null))
    }

    fun setSlideContentView(view: View) {
        super.setContentView(mSlidingPaneLayout, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        mContentView.removeAllViews()
        mContentView.addView(view)
    }

    private fun initSlidingPaneLayout() {
        val slidingPaneLayout = PagerEnabledSlidingPaneLayout(this)
        slidingPaneLayout.setPanelSlideListener(this)
        slidingPaneLayout.sliderFadeColor = ContextCompat.getColor(this, android.R.color.transparent)
        // mOverhangSize值为菜单到右边屏幕的最短距离，默认是32dp
        try {
            val overhangSize = SlidingPaneLayout::class.java.getDeclaredField("mOverhangSize")
            overhangSize.isAccessible = true
            overhangSize.set(slidingPaneLayout, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 左侧的透明视图
        val leftView = View(this)
        leftView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        slidingPaneLayout.addView(leftView, 0)

        // 这种注释的代码性能更好， 因为层级更少，但是兼容性不是很好，各种状态栏处理有问题，后续再优化吧，着急发版
//            (window.decorView as ViewGroup).let { decorView ->
//                val rightView = decorView.getChildAt(0)
//                decorView.removeView(rightView)
//
//                rightView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
//                slidingPaneLayout.addView(rightView, 1)
//                decorView.addView(slidingPaneLayout)
//            }

        val contentView = FrameLayout(this)
        contentView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
        contentView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        slidingPaneLayout.addView(contentView, 1)

        mSlidingPaneLayout = slidingPaneLayout
        mContentView = contentView
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {
    }

    open fun enableSlideClose(): Boolean {
        return true
    }

    override fun onPanelOpened(panel: View) {
        // 滑动的时候关闭退出动画，其他场景动画正常
        onBackPressed()
    }
}