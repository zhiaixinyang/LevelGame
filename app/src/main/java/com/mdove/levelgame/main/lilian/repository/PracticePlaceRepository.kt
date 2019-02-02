package com.mdove.levelgame.main.lilian.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mdove.levelgame.greendao.LiLianLevelDao
import com.mdove.levelgame.greendao.entity.LiLianLevel
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.lilian.bean.LiLianLevelVM
import com.mdove.levelgame.main.lilian.bean.PracticePlaceVM
import com.ss.android.network.threadpool.FastMain
import com.ss.android.network.threadpool.MDoveBackgroundPool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by MDove on 2019/2/2.
 */
class PracticePlaceRepository {
    private val practicePlaceDao = DatabaseManager.getInstance().practicePlaceDao

    fun loadData(): LiveData<List<PracticePlaceVM>> {
        val liveData = MutableLiveData<List<PracticePlaceVM>>()
        CoroutineScope(FastMain).launch {
            val data = withContext(MDoveBackgroundPool) {
                val baseData = mutableListOf<PracticePlaceVM>()
                practicePlaceDao.queryBuilder().list()?.forEach {
                    baseData.add(PracticePlaceVM(it))
                }
                baseData
            }
            liveData.postValue(data)
        }
        return liveData
    }
}