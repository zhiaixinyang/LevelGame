package com.mdove.levelgame.main.home.city.model

import android.databinding.ObservableField
import com.mdove.levelgame.config.AppConfig
import com.mdove.levelgame.greendao.entity.City
import com.mdove.levelgame.greendao.utils.SrcIconMap
import com.mdove.levelgame.utils.JsonUtil

/**
 * Created by MDove on 2018/12/28.
 */
class CityVM(city: City) {
    var id = ObservableField<Long>()
    var clickId = ObservableField<Int>()
    var type = ObservableField<String>()
    var name = ObservableField<String>()
    var tips = ObservableField<String>()
    var enableOpen = ObservableField<Int>()
    var src = ObservableField<Int>()
    var isCurPlace = ObservableField<Boolean>()
    var isMonsterPlace = ObservableField<Boolean>()

    init {
        id.set(city.id)
        type.set(city.type)
        name.set(city.name)
        tips.set(city.tips)
        enableOpen.set(city.enableOpen)
        clickId.set(city.clickId)
        src.set(SrcIconMap.getInstance().getSrc(city.type))
        isMonsterPlace.set(city.isMonsterPlace==0)

        isCurPlace.set(false)
        JsonUtil.decode<CityReps>(AppConfig.getCurPlaceJson(),CityReps::class.java)?.let {
            isCurPlace.set(city.id == it.placeId)
        }
    }
}
