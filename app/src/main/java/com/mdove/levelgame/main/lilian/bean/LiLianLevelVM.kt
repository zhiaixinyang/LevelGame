package com.mdove.levelgame.main.lilian.bean

import android.databinding.ObservableField
import com.mdove.levelgame.greendao.entity.LiLianLevel

/**
 * Created by MDove on 2019/2/2.
 */
class LiLianLevelVM(liLian: LiLianLevel) {
    var id = ObservableField<Long>()
    var name = ObservableField<String>()
    var btnName = ObservableField<String>()
    var tips = ObservableField<String>()
    var type = ObservableField<String>()

    init {
        id.set(liLian.id)
        name.set(liLian.name)
        tips.set(liLian.tips)
        type.set(liLian.type)
        btnName.set(liLian.btnName)
    }
}