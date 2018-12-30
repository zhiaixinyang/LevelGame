package com.mdove.levelgame.main.home.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mdove.levelgame.config.AppConfig
import com.mdove.levelgame.main.home.city.model.CityReps
import com.mdove.levelgame.utils.JsonUtil

/**
 * Created by MDove on 2018/12/30.
 */
class CityViewModel : ViewModel() {
    val curPlaceId = MutableLiveData<CityReps>()

    init {
        curPlaceId.value = JsonUtil.decode(AppConfig.getCurPlaceJson(),CityReps::class.java)
    }
}