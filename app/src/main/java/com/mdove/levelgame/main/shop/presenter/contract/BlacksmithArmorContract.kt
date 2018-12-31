package com.mdove.levelgame.main.shop.presenter.contract

import com.mdove.levelgame.base.BasePresenter
import com.mdove.levelgame.base.BaseView
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM

/**
 * @author MDove on 2018/11/1
 *
 */
interface BlacksmithAccessoriesContract {
    interface IBlacksmithAccessoriesPresenter : BasePresenter<IBlacksmithAccessoriesView> {
        fun initData()
        fun onItemBtnClick(type: String, id: Long?)
    }

    interface IBlacksmithAccessoriesView : BaseView {
        fun showData(data: ArrayList<BlacksmithModelVM>)
    }
}