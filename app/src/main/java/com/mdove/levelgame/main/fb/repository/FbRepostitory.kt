package com.mdove.levelgame.main.fb.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mdove.levelgame.greendao.entity.FbPlace
import com.mdove.levelgame.greendao.utils.DatabaseManager

/**
 * Created by MDove on 2018/12/26.
 */
class FbRepostitory {
    companion object {
        val inst: FbRepostitory by lazy {
            FbRepostitory()
        }
    }

    private val fbPlaceLiveData = MutableLiveData<List<FbPlace>>()

    private val fbPlaceDao by lazy {
        DatabaseManager.getInstance().fbPlaceDao
    }

    fun loadData(): LiveData<List<FbPlace>> {
        fbPlaceLiveData.value = fbPlaceDao.queryBuilder().list()
        return fbPlaceLiveData
    }
}