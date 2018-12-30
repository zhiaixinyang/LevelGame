package com.mdove.levelgame.main.home.city

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mdove.levelgame.config.AppConfig

/**
 * Created by MDove on 2018/12/30.
 */
class CityViewModel : ViewModel() {
    val curPlaceId = MutableLiveData<Long>()

    init {
        curPlaceId.value = AppConfig.getCurPlaceId()
    }
}