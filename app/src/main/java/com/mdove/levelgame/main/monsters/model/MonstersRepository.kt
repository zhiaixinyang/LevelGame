package com.mdove.levelgame.main.monsters.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mdove.levelgame.greendao.MonstersDao
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.monsters.manager.SpecialMonsterManager
import com.mdove.levelgame.main.monsters.model.vm.BaseMonsterModelVM
import com.mdove.levelgame.main.monsters.model.vm.MenuMonsterModelVM
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM
import com.ss.android.network.threadpool.MDoveApiPool
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by MDove on 2018/12/24.
 */
class MonstersRepository {
    private val monstersDao: MonstersDao by lazy {
        DatabaseManager.getInstance().monstersDao
    }

    fun loadData(placeId: Long): List<MonstersModelVM> {
        var data = mutableListOf<MonstersModelVM>()
        GlobalScope.launch {
            val deferred = GlobalScope.async(MDoveApiPool) {
                loadDataFromPlaceId(placeId)
            }
            data.addAll(deferred.await())
        }
        return data
    }

    private fun loadDataFromPlaceId(placeId: Long): MutableList<MonstersModelVM> {
        var data = mutableListOf<MonstersModelVM>()
        // 特殊怪物出现设置
        SpecialMonsterManager.getInstance().setShowSpecialMonster()
        val monsters = monstersDao.queryBuilder()
                .where(MonstersDao.Properties.MonsterPlaceId.eq(placeId), MonstersDao.Properties.IsShow.eq(0)).list()
        if (monsters == null || monsters.size == 0) {
            return data
        }
        monsters.filter {
            it.monsterPlaceId == placeId && it.isShow == 0
        }.forEach {
            data.add(MonstersModelVM(it))
        }
        return data
    }
}