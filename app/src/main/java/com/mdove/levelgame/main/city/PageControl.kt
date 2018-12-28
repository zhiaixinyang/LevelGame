package com.mdove.levelgame.main.city

/**
 * Created by MDove on 2018/12/28.
 */
class PageControl {
    private lateinit var actionCityLoadingClick: () -> Unit
    private lateinit var actionEnterCityLoading: () -> Unit
    fun registerCityLoadingClick(action: () -> Unit) {
        actionCityLoadingClick = action
    }

    fun registerEnterCityLoading(action: () -> Unit) {
        actionEnterCityLoading = action
    }

    fun invokeActionCityLoadingClick() {
        if (::actionEnterCityLoading.isInitialized) {
            actionEnterCityLoading.invoke()
        }
        if (::actionCityLoadingClick.isInitialized) {
            actionCityLoadingClick.invoke()
        }
    }
}