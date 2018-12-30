package com.mdove.levelgame.main.home.city

/**
 * Created by MDove on 2018/12/28.
 */
class PageControl {
    private lateinit var actionCityLoadingClick: (placeId:Long) -> Unit
    private lateinit var actionEnterCityLoading: () -> Unit

    // 通知Loading页面开始刷新页面
    fun registerCityLoadingClick(action: (placeId: Long) -> Unit) {
        actionCityLoadingClick = action
    }

    // 通知Container页面，transition到Loading页面
    fun registerEnterCityLoading(action: () -> Unit) {
        actionEnterCityLoading = action
    }

    fun invokeActionCityLoadingClick(placeId: Long) {
        if (::actionEnterCityLoading.isInitialized) {
            actionEnterCityLoading.invoke()
        }
        if (::actionCityLoadingClick.isInitialized) {
            actionCityLoadingClick.invoke(placeId)
        }
    }
}