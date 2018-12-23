package com.mdove.levelgame.main.task

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import com.mdove.levelgame.base.BaseListActivity
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.main.task.adapter.TaskAdapter
import com.mdove.levelgame.main.task.data.TaskModelVM
import com.mdove.levelgame.main.task.data.TaskViewModel
import javax.inject.Inject

/**
 * Created by MDove on 2018/11/24.
 *
 * Dagger文档：https://google.github.io/dagger/users-guide
 */
class TaskActivity : BaseListActivity<TaskModelVM>(), TaskContract.ITaskView {
    @Inject
    lateinit var presenter: TaskPresenter
    @Inject
    lateinit var adapter: TaskAdapter

    lateinit var viewModel: TaskViewModel

    companion object {
        fun start(context: Context) {
            var intent = Intent(context, TaskActivity::class.java)
            if (context !is Activity) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    override fun createAdapter(): BaseListAdapter<TaskModelVM> {
        return adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel?.initData()
    }

    override fun loadData() {
        viewModel?.initData()
    }

    override fun initData(intent: Intent?) {
        presenter.subscribe(this)
        viewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)

        val tasksObserver = Observer<MutableList<TaskModelVM>> { data ->
            adapter?.let {
                it.data = data
            }
        }

        viewModel.taskData.observe(this, tasksObserver)
    }

    override fun getContext(): Context {
        return this
    }

    override fun notifyUI(position: Int) {
        adapter.notifyItemChanged(position)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.let {
            presenter.unSubscribe()
        }
    }
}