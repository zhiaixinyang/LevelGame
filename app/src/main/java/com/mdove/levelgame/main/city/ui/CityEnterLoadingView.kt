package com.mdove.levelgame.main.city.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.mdove.levelgame.R
import com.mdove.levelgame.base.kotlin.contextJob
import com.mdove.levelgame.main.city.PageControl
import com.mdove.levelgame.view.HorizontalSmoothProgressBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by MDove on 2018/12/28.
 */
class CityEnterLoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        ConstraintLayout(context, attrs, defStyle), ICityView {

    init {
        View.inflate(context, R.layout.view_city_enter_loading_view, this)
    }

    private lateinit var disminss: () -> Unit

    private fun startLoading() {
        val progress = findViewById<HorizontalSmoothProgressBar>(R.id.pb_loading)
        progress.setAutoIncrement(true)
        progress.progress = 0
        progress.addProgressListener {
            disminss?.invoke()
        }
    }

    override fun registerPageControl(pageControl: PageControl) {
        pageControl.registerCityLoadingClick {
            startLoading()
        }
    }

    fun setDismiss(action: () -> Unit) {
        disminss = action
    }
}