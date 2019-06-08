package com.mdove.levelgame.main.lilian

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import com.mdove.levelgame.base.BaseListActivity
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.main.lilian.adapter.PracticePlaceAdapter
import com.mdove.levelgame.main.lilian.bean.PracticePlaceVM
import com.mdove.levelgame.main.lilian.viewmodel.PracticePlaceViewModel
import com.mdove.levelgame.view.MyDialog

/**
 * Created by MDove on 2019/2/2.
 */
class PracticePlaceActivity : BaseListActivity<PracticePlaceVM>() {
    private lateinit var viewModel: PracticePlaceViewModel

    companion object {
        fun start(context: Context) {
            val start = Intent(context, PracticePlaceActivity::class.java)
            if (context !is Activity) {
                start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(start)
        }
    }

    override fun createAdapter(): BaseListAdapter<PracticePlaceVM> {
        return PracticePlaceAdapter { vm ->
            viewModel.clickPlace.value=vm
        }
    }

    override fun loadData() {
        viewModel.initData()
    }

    override fun initData(intent: Intent?) {
        title = "秘境之地"
        viewModel = ViewModelProviders.of(this).get(PracticePlaceViewModel::class.java)
        viewModel.errorToast.observe(this, Observer {
            it?.let {
                MyDialog.showMyDialog(this,"修炼失败",it,true)
            }
        })
        viewModel.dataLiveData.observe(this, Observer { data ->
            data?.let {
                (adapter as? PracticePlaceAdapter)?.let { adapter ->
                    adapter.updateData(data)
                }
            }
        })
    }
}