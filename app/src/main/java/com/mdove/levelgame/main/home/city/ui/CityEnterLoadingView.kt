package com.mdove.levelgame.main.home.city.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import com.google.gson.Gson
import com.mdove.levelgame.R
import com.mdove.levelgame.config.AppConfig
import com.mdove.levelgame.main.home.city.PageControl
import com.mdove.levelgame.main.home.city.model.CityReps
import com.mdove.levelgame.main.home.model.CityViewModel
import com.mdove.levelgame.utils.JsonUtil
import com.mdove.levelgame.view.HorizontalSmoothProgressBar

/**
 * Created by MDove on 2018/12/28.
 */
class CityEnterLoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        ConstraintLayout(context, attrs, defStyle), ICityView {

    init {
        View.inflate(context, R.layout.view_city_enter_loading_view, this)
    }

    private lateinit var disminss: (cityReps: CityReps) -> Unit

    private fun startLoading(cityReps: CityReps) {
        val progress = findViewById<HorizontalSmoothProgressBar>(R.id.pb_loading)
        progress.setAutoIncrement(true)
        progress.progress = 0
        progress.addProgressListener {
            if (::disminss.isInitialized) {
                (context as? AppCompatActivity)?.let {
                    // City和MonstersPlace中Id的映射
                    cityReps.placeId = mapMonsterPlaceId(cityReps.placeId)
                    ViewModelProviders.of(it).get(CityViewModel::class.java).curPlaceId.value = cityReps
                }
                if (!cityReps.isMonsterPlace) {
                    AppConfig.setCurPlaceJson(JsonUtil.encode(cityReps))
                }
                disminss.invoke(cityReps)
            }
        }
    }

    private fun mapMonsterPlaceId(cityId: Long): Long {
        var monsterPlaceId: Long = when (cityId) {
            3.toLong() -> {
                1
            }
            4.toLong() -> {
                2
            }
            5.toLong() -> {
                4
            }
            6.toLong() -> {
                3
            }
            7.toLong() -> {
                5
            }
            8.toLong() -> {
                6
            }
            9.toLong() -> {
                7
            }
            else -> {
                cityId
            }
        }
        return monsterPlaceId
    }

    override fun registerPageControl(pageControl: PageControl) {
        pageControl.registerCityLoadingClick {
            startLoading(it)
        }
    }

    fun registerLoadingDismiss(action: (cityReps: CityReps) -> Unit) {
        disminss = action
    }
}