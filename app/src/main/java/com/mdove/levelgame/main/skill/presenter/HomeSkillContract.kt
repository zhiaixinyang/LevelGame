package com.mdove.levelgame.main.skill.presenter

import com.mdove.levelgame.base.BasePresenter
import com.mdove.levelgame.base.BaseView
import com.mdove.levelgame.main.skill.model.HomeSkillModelVM

/**
 * Created by MDove on 2018/11/14.
 */
class HomeSkillContract {
    interface ISkillPresenter : BasePresenter<ISkillView> {
        fun initData()
        fun onClickStudy(vm: HomeSkillModelVM)
    }

    interface ISkillView : BaseView {
        fun showData(data: ArrayList<HomeSkillModelVM>)
    }
}