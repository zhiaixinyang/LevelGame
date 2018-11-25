package com.mdove.levelgame.main.task

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.mdove.levelgame.base.BaseListActivity
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.main.task.adapter.TaskAdapter
import com.mdove.levelgame.main.task.data.TaskModelVM

/**
 * Created by MDove on 2018/11/24.
 */
class TaskActivity : BaseListActivity<TaskModelVM>(), TaskContract.ITaskView {
    lateinit var presenter: TaskPresenter

    companion object {
        fun start(context: Context) {
            var intent = Intent(context, TaskActivity::class.java)
            if (context !is Activity) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    override fun showData(data: ArrayList<TaskModelVM>) {
        adapter.data = data
    }

    override fun createAdapter(): BaseListAdapter<TaskModelVM> {
        return TaskAdapter(presenter)
    }

    override fun onResume() {
        super.onResume()
        presenter?.initData()
    }

    override fun loadData() {
        presenter.initData()
    }

    override fun initData(intent: Intent?) {
        presenter = TaskPresenter()
        presenter.subscribe(this)
    }

    override fun getContext(): Context {
        return this
    }

    override fun notifyUI(position: Int) {
        adapter.notifyItemChanged(position)
    }
}