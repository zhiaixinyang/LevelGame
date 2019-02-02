package com.mdove.levelgame.main.lilian.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.mdove.levelgame.greendao.LiLianLevelDao
import com.mdove.levelgame.greendao.PackagesDao
import com.mdove.levelgame.greendao.PracticePlaceDao
import com.mdove.levelgame.greendao.entity.FbMonsters
import com.mdove.levelgame.greendao.entity.LiLianLevel
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager
import com.mdove.levelgame.main.hero.manager.HeroManager
import com.mdove.levelgame.main.lilian.bean.GoodType
import com.mdove.levelgame.main.lilian.bean.LiLianLevelVM
import com.mdove.levelgame.main.lilian.bean.PracticePlaceVM
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils
import com.mdove.levelgame.utils.JsonUtil
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

    fun clickPlace(placeId: Long, error: (error: String) -> Unit): LiveData<List<PracticePlaceVM>> {
        val liveData = MutableLiveData<List<PracticePlaceVM>>()
        CoroutineScope(FastMain).launch {
            val data = withContext(MDoveBackgroundPool) {
                val baseData = mutableListOf<PracticePlaceVM>()
                val place = practicePlaceDao.queryBuilder().where(PracticePlaceDao.Properties.Id.eq(placeId)).unique()
                place?.let {
                    var hasGood = true
                    if (!TextUtils.isEmpty(it.consumeGoods)) {
                        val goodsType = JsonUtil.decode<GoodType>(it.consumeGoods, object : TypeToken<List<FbMonsters>>() {}.type)
                        goodsType?.let {goodType->
                            val hasPk = DatabaseManager.getInstance().packagesDao.queryBuilder().where(PackagesDao.Properties.Type.eq(goodType.type)).list()
                            if (hasPk.isNotEmpty()) {
                                val pk = hasPk[0]
                                if (pk.count>=goodType.count){
                                    pk.count = pk.count - 1
                                    if (pk.count==0){
                                        DatabaseManager.getInstance().packagesDao.delete(pk)
                                    }else{
                                        DatabaseManager.getInstance().packagesDao.update(pk)
                                    }
                                    hasGood = true
                                }else{
                                    hasGood = false
                                }
                            } else {
                                hasGood = false
                            }
                        }
                    }
                    AllGoodsToDBIdUtils.getInstance().getDBType(it.type)
                    when {
                        HeroManager.getInstance().heroAttributes.bodyPower < it.consumePower -> error.invoke("体力不足")
                        HeroManager.getInstance().heroAttributes.money < it.consumeMoney -> error.invoke("金钱不足")
                        it.curCount == 0 -> error.invoke("可修炼次数不足")
                        !hasGood -> error.invoke("缺少道具")
                        else -> {
                            HeroManager.getInstance().heroAttributes.bodyPower = HeroManager.getInstance().heroAttributes.bodyPower - it.consumePower
                            HeroManager.getInstance().heroAttributes.money = HeroManager.getInstance().heroAttributes.money - it.consumeMoney
                            HeroManager.getInstance().heroAttributes.liLian = HeroManager.getInstance().heroAttributes.liLian + it.liLian
                            HeroManager.getInstance().save()
                            it.curCount = it.curCount - 1
                            practicePlaceDao.update(it)
                        }
                    }
                }

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