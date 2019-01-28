package com.mdove.levelgame.main.hero.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by zhaojing on 2019/1/28.
 */
class HeroPackageViewModel : ViewModel() {
    var equipPkPosition = MutableLiveData<Int>()
    var packageAddId = MutableLiveData<Long>()
}