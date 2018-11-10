package com.mdove.levelgame.main.hero.model.handler

import com.mdove.levelgame.main.hero.model.HeroSkillModelVM
import com.mdove.levelgame.main.hero.presenter.HeroSkillPresenter

/**
 * Created by MDove on 2018/11/10.
 */
class HeroSkillHandler(presenter: HeroSkillPresenter) {
    var presenter = presenter

    fun onClickEquip(vm: HeroSkillModelVM) {
        presenter.onClickEquip(vm)
    }
}