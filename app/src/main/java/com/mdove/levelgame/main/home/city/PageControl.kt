package com.mdove.levelgame.main.home.city

import com.mdove.levelgame.main.home.city.model.CityReps

/**
 * Created by MDove on 2018/12/28.
 */
class PageControl {
    private lateinit var actionCityLoadingClick: (cityReps: CityReps) -> Unit
    private lateinit var actionEnterCityLoading: () -> Unit

    // 通知Loading页面开始刷新页面
    fun registerCityLoadingClick(action: (cityReps: CityReps) -> Unit) {
        actionCityLoadingClick = action
    }

    // 通知Container页面，transition到Loading页面
    fun registerEnterCityLoading(action: () -> Unit) {
        actionEnterCityLoading = action
    }

    fun invokeActionCityLoadingClick(cityReps: CityReps) {
        if (::actionEnterCityLoading.isInitialized) {
            actionEnterCityLoading.invoke()
        }
        if (::actionCityLoadingClick.isInitialized) {
            actionCityLoadingClick.invoke(cityReps)
        }
    }
}