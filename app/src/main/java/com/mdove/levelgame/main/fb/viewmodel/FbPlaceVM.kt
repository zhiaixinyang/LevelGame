package com.mdove.levelgame.main.fb.viewmodel

import android.databinding.ObservableField
import com.mdove.levelgame.greendao.entity.FbPlace

/**
 * Created by MDove on 2018/12/26.
 */
class FbPlaceVM(fbPlace: FbPlace) {
    var id = ObservableField<Long>()
    var fbPlaceId = ObservableField<Long>()
    var type = ObservableField<String>()
    var name = ObservableField<String>()
    var tips = ObservableField<String>()
    var isLimitCount = ObservableField<Int>()
    var maxLimitCount = ObservableField<Int>()
    var curCount = ObservableField<Int>()
    var consumePower = ObservableField<Int>()

    init {
        id.set(fbPlace.id)
        fbPlaceId.set(fbPlace.fbPlaceId)
        type.set(fbPlace.type)
        name.set(fbPlace.name)
        tips.set(fbPlace.tips)
        isLimitCount.set(fbPlace.isLimitCount)
        maxLimitCount.set(fbPlace.limitCount)
        curCount.set(fbPlace.curCount)
        consumePower.set(fbPlace.consumePower)
    }
}