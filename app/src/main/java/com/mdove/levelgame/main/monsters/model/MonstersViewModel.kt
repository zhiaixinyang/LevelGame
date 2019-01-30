package com.mdove.levelgame.main.monsters.model

import android.arch.lifecycle.*
import com.mdove.levelgame.main.monsters.model.vm.BaseMonsterModelVM
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM

/**
 * Created by MDove on 2018/12/24.
 */
class MonstersViewModel : ViewModel() {
    private var monsterPlaceId = MutableLiveData<Long>()
    val monstersData: LiveData<List<BaseMonsterModelVM>> = MediatorLiveData<List<BaseMonsterModelVM>>().apply {
        addSource(monsterPlaceId) { placeId ->
            placeId?.let {
                value = repository.loadData(it)
            }
        }
    }
    private val repository by lazy {
        MonstersRepository()
    }

    fun loadMonsters(placeId: Long) {
        monsterPlaceId.value = placeId
    }
}