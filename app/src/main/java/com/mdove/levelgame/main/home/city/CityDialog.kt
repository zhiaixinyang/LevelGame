package com.mdove.levelgame.main.home.city

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
class CityDialog(mContext: Context, theme: Int = android.R.style.Theme_Black_NoTitleBar_Fullscreen) : MDoveDialog(mContext, theme) {
    private lateinit var fbContainerView: CityContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.attributes.windowAnimations = R.style.BottomInOutAnim

        fbContainerView = CityContainerView(mContext)

        setContentView(fbContainerView.outerDrag)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //一定要在setContentView之后调用，否则无效
        var lp = window.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = lp

        fbContainerView.setOnDismissListener {
            dismiss()
        }
        fbContainerView.registerLoadingDismiss {
            dismiss()
        }

        setCancelable(false)
    }

    override fun onBackPressed() {
    }
}