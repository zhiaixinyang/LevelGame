package com.mdove.levelgame.main.shop.presenter

import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.mdove.levelgame.R
import com.mdove.levelgame.greendao.MonstersDao
import com.mdove.levelgame.greendao.entity.*
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.hero.manager.HeroBuyManager
import com.mdove.levelgame.main.hero.model.BuyMedicinesResp
import com.mdove.levelgame.main.shop.manager.BlacksmithManager
import com.mdove.levelgame.main.shop.model.mv.SellGoodsModelVM
import com.mdove.levelgame.main.shop.presenter.contract.BusinessmanContract
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils
import com.mdove.levelgame.utils.JsonUtil
import com.mdove.levelgame.utils.ToastHelper
import com.mdove.levelgame.view.MyDialog
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import java.util.*

/**
 * @author MDove on 2018/10/29
 */
class BusinessmanPresenter : BusinessmanContract.IBusinessmanPresenter {
    private lateinit var view: BusinessmanContract.IBusinessmanView
    private var data: MutableList<SellGoodsModelVM> = mutableListOf()

    override fun initData(monstersId: Long?) {
        if (monstersId == (-1).toLong()) {
            return
        }
        data = mutableListOf()
        val monsters = DatabaseManager.getInstance().monstersDao.queryBuilder()
                .where(MonstersDao.Properties.Id.eq(monstersId), MonstersDao.Properties.IsBusinessman.eq(0)).unique()
        if (monsters != null && !TextUtils.isEmpty(monsters.sellGoodsJson)) {
            view.showTitle(monsters.name)
            val sellGoodsTemps = JsonUtil.decode<List<SellGoodsTemp>>(monsters.sellGoodsJson, object : TypeToken<List<SellGoodsTemp>>() {

            }.type)
            for (temp in sellGoodsTemps!!) {
                val oj = AllGoodsToDBIdUtils.getInstance().getBlacksmithModelFromType(temp.type)
                if (oj != null) {
                    when (oj) {
                        is Weapons -> {
                            var status = 0
                            if (oj.isCanMixture == 0) {
                                status = 1
                            } else if (oj.isCanUpdate == 0) {
                                status = 2
                            }
                            data.add(SellGoodsModelVM(temp.price, oj.name, oj.tips, oj.type, status))
                        }
                        is Armors -> {
                            var status = 0
                            if (oj.isCanMixture == 0) {
                                status = 1
                            } else if (oj.isCanUpdate == 0) {
                                status = 2
                            }
                            data.add(SellGoodsModelVM(temp.price, oj.name, oj.tips, oj.type, status))
                        }
                        is Material -> {
                            var status = 0
                            if (oj.isCanMixture == 0) {
                                status = 1
                            }
                            data.add(SellGoodsModelVM(temp.price, oj.name, oj.tips, oj.type, status))
                        }
                        is Accessories -> {
                            var status = 0
                            if (oj.isCanMixture == 0) {
                                status = 1
                            } else if (oj.isCanUpdate == 0) {
                                status = 2
                            }
                            data.add(SellGoodsModelVM(temp.price, oj.name, oj.tips, oj.type, status))
                        }
                        is Skill -> {
                            val status = 3
                            data.add(SellGoodsModelVM(temp.price, oj.name, oj.tips, oj.type, status))
                        }
                        is Medicines -> {
                            val status = 4
                            data.add(SellGoodsModelVM(temp.price, oj.name, oj.tips, oj.type, status))
                        }
                    }
                }
            }
            view.showData(data)
        }
    }

    override fun onItemBtnClick(status: Int, type: String, price: Long) {
        when (status) {
            // 3表示技能
            3, 0 -> {
                HeroBuyManager.getInstance().buy(type, price).subscribe { baseBuy ->
                    when (baseBuy.buyStatus) {
                        HeroBuyManager.BUY_BASE_STATUS_SUC -> {
                            MyDialog.showMyDialog(view.context, view!!.context.getString(R.string.string_buy_title_suc),
                                    String.format(view!!.context.getString(R.string.string_buy_content_suc), baseBuy.name, baseBuy.price), true)
                        }
                        HeroBuyManager.BUY_BASE_STATUS_FAIL -> {
                            MyDialog.showMyDialog(view.context, view!!.context.getString(R.string.string_buy_title_error),
                                    view!!.context.getString(R.string.string_buy_content_error), true)
                        }
                        HeroBuyManager.BUY_BASE_STATUS_ERROR -> {
                            ToastHelper.shortToast(view!!.context.getString(R.string.string_error))
                        }
                        else -> {
                        }
                    }
                }
            }
            // 买药
            4 -> {
                Observable.create(ObservableOnSubscribe<BuyMedicinesResp> { e ->
                    val resp = HeroBuyManager.getInstance().buyMedicines(type)
                    e.onNext(resp)
                }).subscribe({ buyMedicinesResp ->
                    when (buyMedicinesResp.buyStatus) {
                        HeroBuyManager.BUY_MEDICINES_STATUS_ERROR -> {
                            ToastHelper.shortToast(view.context.getString(R.string.string_error))
                        }
                        HeroBuyManager.BUY_MEDICINES_STATUS_FAIL -> {
                            MyDialog.showMyDialog(view.context, view.context.getString(R.string.string_buy_title_error),
                                    view.context.getString(R.string.string_buy_content_error), true)
                        }
                        HeroBuyManager.BUY_MEDICINES_STATUS_FAIL_NO_COUNT -> {
                            MyDialog.showMyDialog(view.context, view.context.getString(R.string.string_buy_title_error),
                                    view.context.getString(R.string.string_buy_content_error_no_count), true)
                        }
                        HeroBuyManager.BUY_MEDICINES_STATUS_SUC -> {
                            val content: String = if (buyMedicinesResp.lifeUp > 0) {
                                String.format(view.context.getString(R.string.string_buy_medicines_suc_and_up), buyMedicinesResp.life,
                                        buyMedicinesResp.price, buyMedicinesResp.lifeUp, buyMedicinesResp.name, buyMedicinesResp.attack, buyMedicinesResp.armor)
                            } else {
                                String.format(view.context.getString(R.string.string_buy_medicines_suc),
                                        buyMedicinesResp.life, buyMedicinesResp.price, buyMedicinesResp.name)
                            }
                            MyDialog.showMyDialog(view.context, view.context.getString(R.string.string_buy_title_suc),
                                    content, true)
                        }
                        else -> {
                        }
                    }
                }, {})
            }
            // 装备对应升级/合成
            1, 2 -> {
                BlacksmithManager.instance.goodsUpdate(type).subscribe { blacksmithResp -> MyDialog.showMyDialog(view!!.context, blacksmithResp.title, blacksmithResp.content, true) }
            }
        }
    }

    override fun subscribe(view: BusinessmanContract.IBusinessmanView) {
        this.view = view
    }

    override fun unSubscribe() {
    }

    inner class SellGoodsTemp {
        var type: String? = null
        var price: Long = 0
    }
}
