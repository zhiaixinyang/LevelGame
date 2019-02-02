package com.mdove.levelgame.main.lilian.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.mdove.levelgame.main.lilian.bean.PracticePlaceVM
import com.mdove.levelgame.main.lilian.repository.PracticePlaceRepository

/**
 * Created by MDove on 2019/2/2.
 */
class PracticePlaceViewModel : ViewModel() {
    private val repository = PracticePlaceRepository()
    private val loadTag = MutableLiveData<Boolean>()
    var dataLiveData: LiveData<List<PracticePlaceVM>> = Transformations.switchMap(loadTag) {
        repository.loadData()
    }

    fun initData() {
        loadTag.value = true
    }
}