package com.mdove.levelgame.main.fb

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.mdove.levelgame.R
import com.mdove.levelgame.view.base.MDoveDialog

/**
 * Created by MDove on 2018/12/26.
 */
class CityDialog(context: Context, theme: Int = android.R.style.Theme_Black_NoTitleBar_Fullscreen) : MDoveDialog(context, theme) {

    private lateinit var fbContainerView: CityContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.attributes.windowAnimations = R.style.BottomInOutAnim

        fbContainerView = CityContainerView(context)

        setContentView(fbContainerView?.containerView)

        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
        // [Bugfix] TTINTHELO-1251
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //一定要在setContentView之后调用，否则无效
        var lp = window.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = lp

        fbContainerView.setOnDismissListener {
            dismiss()
        }
        fbContainerView.setDismiss {
            dismiss()
        }

        setCancelable(false)
    }

    override fun onBackPressed() {
    }
}