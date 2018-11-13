package com.mdove.levelgame.main.home.presenter

import com.mdove.levelgame.base.BasePresenter
import com.mdove.levelgame.base.BaseView

/**
 * Created by MDove on 2018/11/13.
 */
class SettingContract {
    interface ISettingPresenter : BasePresenter<ISettingView> {
        fun initData()
    }

    interface ISettingView : BaseView {
        fun showData()
    }
}