package com.mdove.levelgame.main.shop.presenter

import com.mdove.levelgame.R
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.hero.manager.HeroBuyManager
import com.mdove.levelgame.main.hero.model.BuyMedicinesResp
import com.mdove.levelgame.main.shop.model.mv.MedicinesModelVM
import com.mdove.levelgame.utils.ToastHelper
import com.mdove.levelgame.view.MyDialog
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Created by MDove on 2018/10/30.
 */
class ShopMedicinesPresenter : ShopMedicinesContract.IShopMedicinesPresenter {
    lateinit var mView: ShopMedicinesContract.IShopMedicinesView
    private lateinit var data: MutableList<MedicinesModelVM>

    override fun subscribe(view: ShopMedicinesContract.IShopMedicinesView) {
        mView = view
    }

    override fun unSubscribe() {

    }

    override fun initData() {
        data = mutableListOf()
        val medicines = DatabaseManager.getInstance().medicinesDao.loadAll()
        for (model in medicines) {
            data.add(MedicinesModelVM(model))
        }

        mView.showData(data)
    }

    override fun onItemBtnClick(id: Long) {
        Observable.create(ObservableOnSubscribe<BuyMedicinesResp> { e ->
            val resp = HeroBuyManager.getInstance().buyMedicines(id)
            e.onNext(resp)
        }).subscribe({ buyMedicinesResp ->
            when (buyMedicinesResp.buyStatus) {
                HeroBuyManager.BUY_MEDICINES_STATUS_ERROR -> {
                    ToastHelper.shortToast(mView.context.getString(R.string.string_error))
                }
                HeroBuyManager.BUY_MEDICINES_STATUS_FAIL -> {
                    MyDialog.showMyDialog(mView.context, mView.context.getString(R.string.string_buy_title_error),
                            mView.context.getString(R.string.string_buy_content_error), true)
                }
                HeroBuyManager.BUY_MEDICINES_STATUS_FAIL_NO_COUNT -> {
                    MyDialog.showMyDialog(mView.context, mView.context.getString(R.string.string_buy_title_error),
                            mView.context.getString(R.string.string_buy_content_error_no_count), true)
                }
                HeroBuyManager.BUY_MEDICINES_STATUS_SUC -> {
                    val content: String = if (buyMedicinesResp.lifeUp > 0) {
                        String.format(mView.context.getString(R.string.string_buy_medicines_suc_and_up), buyMedicinesResp.life,
                                buyMedicinesResp.price, buyMedicinesResp.lifeUp, buyMedicinesResp.name, buyMedicinesResp.attack, buyMedicinesResp.armor)
                    } else {
                        String.format(mView.context.getString(R.string.string_buy_medicines_suc),
                                buyMedicinesResp.life, buyMedicinesResp.price, buyMedicinesResp.name)
                    }
                    notifyUI(id)
                    MyDialog.showMyDialog(mView.context, mView.context.getString(R.string.string_buy_title_suc),
                            content, true)
                }
                else -> {
                }
            }
        }, {})
    }

    private fun notifyUI(id: Long) {
        var position = -1
        for (vm in data) {
            if (vm.id.get() == id) {
                vm.resetLimitCount()
                position = data.indexOf(vm)
                break
            }
        }
        if (position != -1) {
            mView.notifyUI(position)
        }
    }
}
