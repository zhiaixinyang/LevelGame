package com.mdove.levelgame.main.monsters.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mdove.levelgame.greendao.MonstersDao
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.monsters.manager.SpecialMonsterManager
import com.mdove.levelgame.main.monsters.model.vm.BaseMonsterModelVM
import com.mdove.levelgame.main.monsters.model.vm.MenuMonsterModelVM
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM
import com.ss.android.network.threadpool.FastMain
import com.ss.android.network.threadpool.MDoveBackgroundPool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by MDove on 2018/12/24.
 */
class MonstersRepository {
    private val monstersDao: MonstersDao by lazy {
        DatabaseManager.getInstance().monstersDao
    }

    fun loadData(placeId: Long): LiveData<List<BaseMonsterModelVM>> {
        val dataLiveData = MutableLiveData<List<BaseMonsterModelVM>>()
        CoroutineScope(FastMain).launch {
            val baseData = withContext(MDoveBackgroundPool) {
                val data = mutableListOf<BaseMonsterModelVM>()
                // 特殊怪物出现设置
                SpecialMonsterManager.getInstance().setShowSpecialMonster()
                val qb = monstersDao.queryBuilder()
                val monsters = qb.whereOr(qb.and(MonstersDao.Properties.MonsterPlaceId.eq(placeId), MonstersDao.Properties.IsShow.eq(0)),
                        MonstersDao.Properties.MonsterPlaceId.eq(0)).list()
                data.add(MenuMonsterModelVM())
                monsters.forEach {
                    data.add(MonstersModelVM(it))
                }
                val lastPos = data.size - 1
                data.add(0, data[lastPos])
                data.removeAt(lastPos+1)
                data
            }
            dataLiveData.postValue(baseData)
        }
        return dataLiveData
    }
}