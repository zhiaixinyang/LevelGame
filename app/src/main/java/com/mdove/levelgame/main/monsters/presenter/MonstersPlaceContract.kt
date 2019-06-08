package com.mdove.levelgame.main.monsters.presenter

import com.mdove.levelgame.base.BasePresenter
import com.mdove.levelgame.base.BaseView
import com.mdove.levelgame.greendao.entity.MonstersPlace
import com.mdove.levelgame.main.monsters.model.MonstersPlaceModel

/**
 * Created by MDove on 2018/10/21.
 */

interface MonstersPlaceContract {
    interface IMonstersPlacePresenter : BasePresenter<IMonstersPlaceView> {
        fun initData()

        fun onItemBtnClick(id: Long, title: String)
    }

    interface IMonstersPlaceView : BaseView {
        fun showData(data: List<MonstersPlace>)
    }
}
