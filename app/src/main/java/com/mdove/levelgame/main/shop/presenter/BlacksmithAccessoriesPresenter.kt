package com.mdove.levelgame.main.shop.presenter

import com.mdove.levelgame.greendao.entity.Accessories
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.shop.manager.BlacksmithManager
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM
import com.mdove.levelgame.view.MyDialog
import io.reactivex.functions.Consumer

/**
 * @author MDove on 2018/11/1
 *
 */
class BlacksmithAccessoriesPresenter : BlacksmithAccessoriesContract.IBlacksmithAccessoriesPresenter {
    override fun onItemBtnClick(type: String, id: Long?) {
        BlacksmithManager.getInstance().goodsUpdate(type).subscribe(Consumer {
            MyDialog.showMyDialog(view.context, it.title, it.content, true)
        })
    }

    lateinit var view: BlacksmithAccessoriesContract.IBlacksmithAccessoriesView
    lateinit var vmData: ArrayList<BlacksmithModelVM>
    override fun initData() {
        vmData = ArrayList()
        var data = DatabaseManager.getInstance().accessoriesDao.queryBuilder().list()
        for (items in data) {
            vmData.add(BlacksmithModelVM(items as Accessories))
        }
        view.showData(vmData)
    }

    override fun subscribe(view: BlacksmithAccessoriesContract.IBlacksmithAccessoriesView?) {
        this.view = view!!
    }

    override fun unSubscribe() {
    }

}