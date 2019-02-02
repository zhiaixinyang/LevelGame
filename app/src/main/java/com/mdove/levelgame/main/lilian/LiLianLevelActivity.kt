package com.mdove.levelgame.main.lilian

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.mdove.levelgame.base.BaseListActivity
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.base.recyclerview.ViewPagerLayoutManager
import com.mdove.levelgame.main.lilian.adapter.LiLianLevelAdapter
import com.mdove.levelgame.main.lilian.bean.LiLianLevelVM
import com.mdove.levelgame.main.lilian.viewmodel.LiLianViewModel

/**
 * Created by MDove on 2019/2/2.
 */
class LiLianLevelActivity : BaseListActivity<LiLianLevelVM>() {
    private lateinit var liLianViewModel: LiLianViewModel

    companion object {
        fun start(context: Context) {
            val start = Intent(context, LiLianLevelActivity::class.java)
            if (context !is Activity) {
                start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(start)
        }
    }

    override fun customLayoutManager(): RecyclerView.LayoutManager {
        return ViewPagerLayoutManager(this, LinearLayoutManager.VERTICAL)
    }

    override fun createAdapter(): BaseListAdapter<LiLianLevelVM> {
        return LiLianLevelAdapter()
    }

    override fun loadData() {
        liLianViewModel.initData()
    }

    override fun initData(intent: Intent?) {
        title = "历练晋升"

        liLianViewModel = ViewModelProviders.of(this).get(LiLianViewModel::class.java)
        liLianViewModel.dataLiveData.observe(this, Observer {
            adapter.data = it
        })
    }
}