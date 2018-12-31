package com.mdove.levelgame.main.hero.model

import android.databinding.ObservableField
import com.mdove.levelgame.greendao.utils.SrcIconMap

/**
 * Created by zhaojing on 2018/12/31.
 */
class CustomPkBaseViewModel(name: String, tips: String, type: String) {
    var name = ObservableField<String>()
    var tips = ObservableField<String>()
    var src = ObservableField<Int>()

    init {
        this.name.set(name)
        this.tips.set(tips)
        src.set(SrcIconMap.getInstance().getSrc(type))
    }
}