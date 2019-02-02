package com.mdove.levelgame.main.lilian.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mdove.levelgame.greendao.LiLianLevelDao
import com.mdove.levelgame.greendao.entity.LiLianLevel
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.lilian.bean.LiLianLevelVM
import com.ss.android.network.threadpool.FastMain
import com.ss.android.network.threadpool.MDoveBackgroundPool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by MDove on 2019/2/2.
 */
class LiLianRepository {
    private val liLianLevelDao = DatabaseManager.getInstance().liLianLevelDao

    fun loadData(): LiveData<List<LiLianLevelVM>> {
        val liveData = MutableLiveData<List<LiLianLevelVM>>()
        CoroutineScope(FastMain).launch {
            val data = withContext(MDoveBackgroundPool) {
                val baseData = mutableListOf<LiLianLevelVM>()
                liLianLevelDao.queryBuilder().list()?.forEach {
                    baseData.add(LiLianLevelVM(it))
                }
                baseData
            }
            liveData.postValue(data)
        }
        return liveData
    }

    fun getLiLianLevel(id: Long): LiLianLevel {
        return liLianLevelDao.queryBuilder().where(LiLianLevelDao.Properties.Id.eq(id)).unique()
    }
}