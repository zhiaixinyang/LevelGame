package com.mdove.levelgame.main.monsters.presenter

import com.mdove.levelgame.greendao.MonstersPlaceDao
import com.mdove.levelgame.greendao.entity.MonstersPlace
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.greendao.utils.InitDataFileUtils
import com.mdove.levelgame.main.monsters.MonstersActivity

/**
 * Created by MDove on 2018/10/21.
 */

class MonstersPlacePresenter : MonstersPlaceContract.IMonstersPlacePresenter {
    private lateinit var view: MonstersPlaceContract.IMonstersPlaceView

    override fun subscribe(view: MonstersPlaceContract.IMonstersPlaceView) {
        this.view = view
    }

    override fun unSubscribe() {
    }

    override fun initData() {
        val places = DatabaseManager.getInstance().monstersPlaceDao.queryBuilder()
                .where(MonstersPlaceDao.Properties.IsShow.eq(0)).list()
        if (places == null || places.size == 0) {
            return
        }
        view.showData(places)
    }

    override fun onItemBtnClick(id: Long, title: String) {
        MonstersActivity.start(view.context, id, title)
    }
}
