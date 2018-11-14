package com.mdove.levelgame.main.skill.model

import com.mdove.levelgame.main.skill.presenter.HomeSkillPresenter

/**
 * Created by MDove on 2018/11/14.
 */
class HomeSkillHandler(presenterHome: HomeSkillPresenter) {
    var presenterHome: HomeSkillPresenter = presenterHome

    fun onClickStudy(vm: HomeSkillModelVM) {
        presenterHome.onClickStudy(vm)
    }
}