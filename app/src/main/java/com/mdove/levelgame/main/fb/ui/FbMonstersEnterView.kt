package com.mdove.levelgame.main.fb.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.mdove.levelgame.R
import com.mdove.levelgame.base.kotlin.setDebounceOnClickListener

/**
 * Created by MDove on 2018/12/26.
 */
class FbMonstersEnterView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle) {
    init {
        View.inflate(context, R.layout.view_fb_monsters_enter_view, this)
    }

    fun setListener(action: () -> Unit) {
        findViewById<TextView>(R.id.tv_btn).setDebounceOnClickListener {
            action()
        }
    }
}