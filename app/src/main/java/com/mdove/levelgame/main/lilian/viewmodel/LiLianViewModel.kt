package com.mdove.levelgame.main.lilian.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.mdove.levelgame.main.lilian.bean.LiLianLevelVM
import com.mdove.levelgame.main.lilian.repository.LiLianRepository

/**
 * Created by MDove on 2019/2/2.
 */
class LiLianViewModel : ViewModel() {
    private val repository = LiLianRepository()
    private val loadTag = MutableLiveData<Boolean>()
    var dataLiveData: LiveData<List<LiLianLevelVM>> = Transformations.switchMap(loadTag) {
        repository.loadData()
    }

    fun initData() {
        loadTag.value = true
    }

    fun onClickGet(vm: LiLianLevelVM) {
        val liLianLevel = repository.getLiLianLevel(vm.id.get())

    }
}