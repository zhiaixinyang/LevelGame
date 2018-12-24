package com.mdove.levelgame.main.monsters.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM

/**
 * Created by MDove on 2018/12/24.
 */
class MonstersViewModel : ViewModel() {
    val monstersData = MutableLiveData<List<MonstersModelVM>>()

    private val repository by lazy {
        MonstersRepository()
    }

    fun loadMonsters(placeId: Long) {
        monstersData.value = repository.loadData(placeId)
    }
}