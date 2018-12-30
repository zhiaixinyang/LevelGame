package com.mdove.levelgame.main.home.city.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.mdove.levelgame.R
import com.mdove.levelgame.main.home.city.PageControl
import com.mdove.levelgame.view.HorizontalSmoothProgressBar

/**
 * Created by MDove on 2018/12/28.
 */
class CityEnterLoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        ConstraintLayout(context, attrs, defStyle), ICityView {

    init {
        View.inflate(context, R.layout.view_city_enter_loading_view, this)
    }

    private lateinit var disminss: (placeId: Long) -> Unit

    private fun startLoading(placeId: Long) {
        val progress = findViewById<HorizontalSmoothProgressBar>(R.id.pb_loading)
        progress.setAutoIncrement(true)
        progress.progress = 0
        progress.addProgressListener {
            if (::disminss.isInitialized) {
                disminss.invoke(placeId)
            }
        }
    }

    override fun registerPageControl(pageControl: PageControl) {
        pageControl.registerCityLoadingClick {
            startLoading(it)
        }
    }

    fun registerLoadingDismiss(action: (placeId: Long) -> Unit) {
        disminss = action
    }
}