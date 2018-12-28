package com.mdove.levelgame.main.city.model

import android.databinding.ObservableField
import com.mdove.levelgame.greendao.entity.City

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

    init {
        id.set(city.id)
        type.set(city.type)
        name.set(city.name)
        tips.set(city.tips)
        enableOpen.set(city.enableOpen)
        clickId.set(city.clickId)
    }
}
