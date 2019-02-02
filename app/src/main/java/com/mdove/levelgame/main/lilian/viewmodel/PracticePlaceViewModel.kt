package com.mdove.levelgame.main.lilian.viewmodel

import android.arch.lifecycle.*
import com.mdove.levelgame.main.lilian.bean.PracticePlaceVM
import com.mdove.levelgame.main.lilian.repository.PracticePlaceRepository

/**
 * Created by MDove on 2019/2/2.
 */
class PracticePlaceViewModel : ViewModel() {
    val clickPlace = MutableLiveData<PracticePlaceVM>()
    var errorToast = MutableLiveData<String>()

    private val repository = PracticePlaceRepository()
    private val loadTag = MutableLiveData<Boolean>()
    var dataLiveData: LiveData<List<PracticePlaceVM>> = MediatorLiveData<List<PracticePlaceVM>>().apply {
        addSource(loadTag) {
           value= repository.loadData().value
        }
        addSource(clickPlace) {
            it?.let { vm ->
                value=repository.clickPlace(vm.id.get()) {
                    errorToast.value = it
                }.value
            }
        }
    }

    fun initData() {
        loadTag.value = true
    }
}