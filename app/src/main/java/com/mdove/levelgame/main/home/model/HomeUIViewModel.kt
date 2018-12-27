package com.mdove.levelgame.main.home.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mdove.levelgame.config.AppConfig

/**
 * Created by MDove on 2018/12/27.
 */
class HomeUIViewModel : ViewModel() {
    val enableBigMonster = MutableLiveData<Boolean>()

    init {
        enableBigMonster.value = AppConfig.enableBigMonster()
    }

    fun initUI() {
        enableBigMonster.value = AppConfig.enableBigMonster()
    }
}
