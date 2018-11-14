package com.mdove.levelgame.main.skill

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.mdove.levelgame.R
import com.mdove.levelgame.base.BaseListActivity
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.main.skill.adapter.HomeSkillAdapter
import com.mdove.levelgame.main.skill.model.HomeSkillModelVM
import com.mdove.levelgame.main.skill.presenter.HomeSkillContract
import com.mdove.levelgame.main.skill.presenter.HomeSkillPresenter

/**
 * Created by MDove on 2018/11/14.
 */

class HomeSkillActivity : BaseListActivity<HomeSkillModelVM>(), HomeSkillContract.ISkillView {
    lateinit var presenter: HomeSkillPresenter

    companion object {
        fun start(context: Context) {
            var intent = Intent(context, HomeSkillActivity::class.java)
            if (context !is Activity) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    override fun createAdapter(): BaseListAdapter<HomeSkillModelVM> {
        return HomeSkillAdapter(presenter)
    }

    override fun loadData() {
        title=getString(R.string.string_activity_home_skill)
        presenter.initData()
    }

    override fun initData(intent: Intent?) {
        presenter = HomeSkillPresenter()
        presenter.subscribe(this)
    }

    override fun showData(data: ArrayList<HomeSkillModelVM>) {
        adapter.data = data
    }
}
