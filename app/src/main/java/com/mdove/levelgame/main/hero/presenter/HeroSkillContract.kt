package com.mdove.levelgame.main.hero.presenter

import com.mdove.levelgame.base.BasePresenter
import com.mdove.levelgame.base.BaseView
import com.mdove.levelgame.greendao.entity.Skill
import com.mdove.levelgame.main.hero.model.HeroSkillModelVM

/**
 * Created by MDove on 2018/11/10.
 */
class HeroSkillContract {
    interface IHeroSkillPresenter : BasePresenter<IHeroSkillView> {
        fun initData()
        fun onClickEquip(vm: HeroSkillModelVM)
    }

    interface IHeroSkillView : BaseView {
        fun showData(data: ArrayList<HeroSkillModelVM>)
        fun notifyUI(position: Int)
    }
}