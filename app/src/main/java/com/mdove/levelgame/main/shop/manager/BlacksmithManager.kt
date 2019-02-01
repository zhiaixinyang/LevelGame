package com.mdove.levelgame.main.shop.manager

import android.text.TextUtils

import com.google.gson.reflect.TypeToken
import com.mdove.levelgame.App
import com.mdove.levelgame.R
import com.mdove.levelgame.greendao.PackagesDao
import com.mdove.levelgame.greendao.entity.Packages
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager
import com.mdove.levelgame.model.BaseBlacksmithModel
import com.mdove.levelgame.main.shop.model.StrengthenNeedModel
import com.mdove.levelgame.main.shop.model.StrengthenResp
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils
import com.mdove.levelgame.utils.JsonUtil

import java.util.ArrayList
import java.util.Random

import io.reactivex.Observable

/**
 * Created by MDove on 2018/10/28.
 */

class BlacksmithManager {
    companion object {
        // 换铜矿
        private val TO_COPPER_TYPE = "A10"
        // 换强化石
        private val TO_STRENGTHEN_TYPE = "A11"

        // 强化已满级
        const val STRENGTHEN_STATUS_MAX = 1
        // 强化成功
        const val STRENGTHEN_STATUS_SUC = 2
        const val STRENGTHEN_STATUS_FAIL = 3
        // 材料不足
        const val STRENGTHEN_STATUS_NO_MATERIAL = 5
        const val STRENGTHEN_STATUS_ERROR = 4

        val instance: BlacksmithManager
            get() = SingletonHolder.INSTANCE
    }

    private val pkDao = DatabaseManager.getInstance().packagesDao

    private object SingletonHolder {
        internal val INSTANCE = BlacksmithManager()
    }

    fun goodsUpdate(type: String): Observable<BlacksmithResp> {
        return updateAndMixture(AllGoodsToDBIdUtils.getInstance().getBlacksmithModelFromType(type))

    }

    private fun updateAndMixture(weapon: BaseBlacksmithModel?): Observable<BlacksmithResp> {
        val blacksmithResp = BlacksmithResp()
        if (weapon != null) {
            // 可升级
            if (weapon.canUpdate == 0) {
                // 判断材料是否完全
                val formula = weapon.updateFormulas
                val needs = JsonUtil.decode<List<NeedMaterial>>(formula, object : TypeToken<List<NeedMaterial>>() {

                }.type)
                needs?.let {
                    val hasMaterials = ArrayList<HasMaterial>()
                    for (type in needs) {
                        hasMaterials.add(hasMaterial(type.type, type.count))
                    }
                    var isSuc = hasMaterials.any { material ->
                        !material.isHas
                    }
                    // 升级成功
                    if (isSuc) {
                        // 删除低级材料（装备+石头，直接从Pk中）
                        deleteMaterial(hasMaterials)
                        // 生成新装备
                        newPk(weapon)
                        blacksmithResp.isSuc = true
                        blacksmithResp.title = App.getAppContext().getString(R.string.string_blacksmith_update_suc)
                        blacksmithResp.content = weapon.myName + "升级成功。"
                    } else {
                        resetPkSelectStatus()
                        blacksmithResp.isSuc = false
                        blacksmithResp.title = "升级失败"
                        blacksmithResp.content = "缺少材料：请确认所需材料都持有。"
                    }
                } ?: run {
                    blacksmithResp.isSuc = false
                    blacksmithResp.title = "未知错误"
                    blacksmithResp.content = "获取升级材料异常：null。"
                }
            } else if (weapon.canMixture == 0) {
                // 可合成
                // 判断材料是否完全
                val formula = weapon.mixtureFormulas
                val needs = JsonUtil.decode<List<NeedMaterial>>(formula, object : TypeToken<List<NeedMaterial>>() {}.type)
                needs?.let {
                    val hasMaterials = ArrayList<HasMaterial>()
                    for (type in needs) {
                        hasMaterials.add(hasMaterial(type.type, type.count))
                    }
                    var isSuc = hasMaterials.any { material ->
                        !material.isHas
                    }
                    // 合成成功
                    if (isSuc) {
                        // 删除低级材料（装备+石头，直接从Pk中）
                        deleteMaterial(hasMaterials)
                        // 生成新装备
                        newPk(weapon)
                        blacksmithResp.isSuc = true
                        blacksmithResp.title = "合成成功"
                        blacksmithResp.content = weapon.myName + "合成成功。"
                    } else {
                        // 重置pk中材料的选择状态
                        resetPkSelectStatus()
                        blacksmithResp.isSuc = false
                        blacksmithResp.title = "合成失败"
                        blacksmithResp.content = "缺少材料：请确认所需材料都持有。"
                    }
                } ?: run {
                    blacksmithResp.isSuc = false
                    blacksmithResp.title = "未知错误"
                    blacksmithResp.content = "获取升级材料异常：null。"
                }
            } else {
                blacksmithResp.isSuc = false
                blacksmithResp.title = "未知错误"
                blacksmithResp.content = "未找到对应升级的装备..."
            }
        } else {
            blacksmithResp.isSuc = false
            blacksmithResp.title = "未知错误"
            blacksmithResp.content = "未找到对应升级的装备..."
        }
        return Observable.create { e -> e.onNext(blacksmithResp) }
    }

    private fun resetPkSelectStatus() {
        pkDao.queryBuilder().list()?.forEach { pk ->
            pk.isSelect = 1
            pkDao.update(pk)
        }
    }

    private fun newPk(model: BaseBlacksmithModel) {
        var packages = Packages()
        packages.isEquip = 1
        packages.isSelect = 1
        packages.strengthenLevel = 0
        packages.type = model.myType
        packages.count = 1
        when (model.myType) {
            TO_COPPER_TYPE -> {
                val pkType = "E2"
                pkDao.queryBuilder().where(PackagesDao.Properties.Type.eq("E3")).unique()?.let { hasPk ->
                    hasPk.count = hasPk.count + 1
                    pkDao.update(hasPk)
                } ?: run {
                    packages.type = pkType
                    pkDao.insert(packages)
                }
            }
            TO_STRENGTHEN_TYPE -> {
                val pkType = "E3"
                pkDao.queryBuilder().where(PackagesDao.Properties.Type.eq("E3")).unique()?.let { hasPk ->
                    hasPk.count = hasPk.count + 1
                    pkDao.update(hasPk)
                } ?: run {
                    packages.type = pkType
                    pkDao.insert(packages)
                }
            }
            else -> {
                pkDao.insert(packages)
            }
        }
    }

    private fun deleteMaterial(hasMaterials: List<HasMaterial>) {
        for (material in hasMaterials) {
            val packages = material.pk
            if (packages != null) {
                // 如果删除的装备是已经装备的，则先脱掉装备
                if (packages.isEquip == 0) {
                    val oj = AllGoodsToDBIdUtils.getInstance().getAttrsModelFromType(packages.type)
                    if (oj != null) {
                        HeroAttributesManager.getInstance().takeOff(packages.strengthenLevel.toLong(), oj,
                                packages.getRandomAttr())
                    }
                }
                if (packages.count == material.needCount) {
                    pkDao.delete(packages)
                } else if (packages.count > material.needCount) {
                    packages.count = packages.count - material.needCount
                    pkDao.update(packages)
                }
            }
        }
    }

    fun strengthen(pkId: Long, type: String): StrengthenResp {
        val resp = StrengthenResp()
        resp.status = STRENGTHEN_STATUS_ERROR
        val model = AllGoodsToDBIdUtils.getInstance().getBlacksmithModelFromType(type)
        val pk = pkDao.queryBuilder().where(PackagesDao.Properties.Id.eq(pkId)).unique()
        if (model != null && pk != null) {
            // 强化装备为：武器
            if (model.canStrengthen == 0) {
                val needModels = JsonUtil.decode<List<StrengthenNeedModel>>(model.strengthenFormulas, object : TypeToken<List<StrengthenNeedModel>>() {}.type)
                val nextLevel = pk.strengthenLevel + 1
                // 可强化
                if (nextLevel <= needModels!!.size) {
                    // 数组越界问题
                    val needModel = needModels[nextLevel - 1]
                    val hasMaterial = hasMaterial(needModel.type, needModel.count)
                    if (!hasMaterial.isHas) {
                        resp.status = STRENGTHEN_STATUS_NO_MATERIAL
                        return resp
                    } else {
                        // 材料足够，先删强化石
                        val _pk = hasMaterial.pk
                        if (_pk!!.count > needModel.count) {
                            _pk.count = _pk.count - needModel.count
                            resp.isDelete = false
                            pkDao.update(_pk)
                        } else if (_pk.count == needModel.count) {
                            resp.isDelete = true
                            pkDao.delete(_pk)
                        }
                        val probability = needModel.probability * 100
                        val random = Random(System.currentTimeMillis()).nextInt(100)
                        // 强化成功
                        return if (random <= probability) {
                            resp.status = STRENGTHEN_STATUS_SUC
                            pk.strengthenLevel = nextLevel
                            resp.level = pk.strengthenLevel.toLong()
                            resp.strengthId = hasMaterial.pk!!.id
                            pkDao.update(pk)
                            resp
                        } else {
                            resp.strengthId = hasMaterial.pk!!.id
                            resp.status = STRENGTHEN_STATUS_FAIL
                            resp
                        }
                    }
                } else {
                    resp.status = STRENGTHEN_STATUS_MAX
                    return resp
                }
            }
        }
        return resp
    }

    fun hasMaterial(type: String?, needCount: Int): HasMaterial {
        // 因为龙渊有俩个版本A15和A18（需要单独处理）
        val packages: List<Packages>? = if (TextUtils.equals(type, "A15") || TextUtils.equals(type, "A18")) {
            pkDao.queryBuilder()
                    .where(PackagesDao.Properties.Type.eq("A15"), PackagesDao.Properties.IsSelect.eq(1))
                    .whereOr(PackagesDao.Properties.Type.eq("A18"), PackagesDao.Properties.IsSelect.eq(1)).list()
        } else {
            pkDao.queryBuilder()
                    .where(PackagesDao.Properties.Type.eq(type), PackagesDao.Properties.IsSelect.eq(1)).list()
        }
        return packages?.firstOrNull()?.takeIf { pk ->
            pk.count >= needCount
        }?.let { pk ->
            val material = HasMaterial()
            material.isHas = true
            material.pk = pk
            material.needCount = needCount
            pk.isSelect = 0
            pkDao.update(pk)
            material
        } ?: HasMaterial().apply {
            isHas = false
        }
    }

    private inner class NeedMaterial {
        var type: String? = null
        var count: Int = 0
    }

    inner class BlacksmithResp {
        var isSuc: Boolean = false
        var title: String? = null
        var content: String? = null
    }

    inner class HasMaterial {
        var isHas: Boolean = false
        // 拥有Packages
        var pk: Packages? = null
        var needCount: Int = 0
    }
}
