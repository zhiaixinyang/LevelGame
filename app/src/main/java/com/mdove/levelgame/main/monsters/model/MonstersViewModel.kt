package com.mdove.levelgame.main.monsters.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.mdove.levelgame.main.monsters.model.vm.BaseMonsterModelVM

/**
 * Created by MDove on 2018/12/24.
 */
class MonstersViewModel : ViewModel() {
    private var monsterPlaceId = MutableLiveData<Long>()
    val monstersData: LiveData<List<BaseMonsterModelVM>> = Transformations.switchMap(monsterPlaceId) {
        repository.loadData(it)
    }
    private val repository by lazy {
        MonstersRepository()
    }

    fun loadMonsters(placeId: Long) {
        monsterPlaceId.value = placeId
    }
}