package com.mdove.levelgame.main.fb.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.mdove.levelgame.main.fb.repository.FbRepostitory

/**
 * Created by MDove on 2018/12/26.
 */
class FbPlaceViewModel : ViewModel() {
    var fbPlaceData = MutableLiveData<List<FbPlaceVM>>()
    fun loadData() {
        val data = FbRepostitory.inst.loadData()
        var vmData = mutableListOf<FbPlaceVM>()
        data.value?.forEach {
            vmData.add(FbPlaceVM(it))
        }
        fbPlaceData.value = vmData
    }
}