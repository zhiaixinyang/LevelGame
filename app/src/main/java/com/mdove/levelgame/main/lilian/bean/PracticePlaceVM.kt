package com.mdove.levelgame.main.lilian.bean

import android.databinding.ObservableField
import com.mdove.levelgame.App
import com.mdove.levelgame.R
import com.mdove.levelgame.greendao.entity.PracticePlace

/**
 * Created by MDove on 2019/2/2.
 */
class PracticePlaceVM(practicePlace: PracticePlace) {
    var id = ObservableField<Long>()
    var name = ObservableField<String>()
    var btnName = ObservableField<String>()
    var tips = ObservableField<String>()
    var type = ObservableField<String>()
    var strlimitCount = ObservableField<String>()
    var curCount = -1
    var isLimitCount = ObservableField<Boolean>()

    init {
        id.set(practicePlace.id)
        name.set(practicePlace.name)
        tips.set(practicePlace.tips)
        type.set(practicePlace.type)
        btnName.set("修炼")
        isLimitCount.set(false)
        strlimitCount.set("")
        if (practicePlace.isLimitCount == 0) {
            isLimitCount.set(true)
            curCount = practicePlace.curCount
            strlimitCount.set(String.format(App.getAppContext().getString(
                    R.string.string_practice_has_count, practicePlace.curCount, practicePlace.limitCount)))
        }
    }
}