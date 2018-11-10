package com.mdove.levelgame.main.hero.fragment

import android.os.Bundle
import com.mdove.levelgame.base.BaseListFragment
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.main.hero.adapter.HeroSkillAdapter
import com.mdove.levelgame.main.hero.model.HeroSkillModelVM
import com.mdove.levelgame.main.hero.presenter.HeroSkillContract
import com.mdove.levelgame.main.hero.presenter.HeroSkillPresenter

/**
 * Created by MDove on 2018/11/10.
 */
class HeroSkillFragment : BaseListFragment(), HeroSkillContract.IHeroSkillView {
    lateinit var presenter: HeroSkillPresenter

    companion object {
        fun newInstance(): HeroSkillFragment {
            val args: Bundle = Bundle()
            val fragment = HeroSkillFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initData() {
        presenter = HeroSkillPresenter()
        presenter.view = this
    }

    override fun loadData() {
        presenter.initData()
    }

    override fun createAdapter(): BaseListAdapter<*> {
        return HeroSkillAdapter(presenter)
    }

    override fun showData(data: ArrayList<HeroSkillModelVM>) {
        adapter.data = data as List<Any>?
    }

    override fun notifyUI(position: Int) {
        adapter.notifyItemChanged(position)
    }
}