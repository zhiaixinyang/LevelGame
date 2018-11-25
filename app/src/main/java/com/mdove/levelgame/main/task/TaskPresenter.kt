package com.mdove.levelgame.main.task

import com.mdove.levelgame.R
import com.mdove.levelgame.base.RxTransformerHelper
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.task.data.TaskModelVM
import com.mdove.levelgame.view.MyDialog

/**
 * @author MDove on 2018/11/5
 *
 */
class TaskPresenter : TaskContract.ITaskPresenter {
    lateinit var view: TaskContract.ITaskView
    lateinit var data: ArrayList<TaskModelVM>
    override fun onItemBtnOnClick(modelVM: TaskModelVM) {
        var position: Int = -1
        TaskManager.instance.startTask(modelVM.id.get())
                .compose(RxTransformerHelper.schedulerTransf())
                .subscribe {
                    when (it.status) {
                        TaskManager.STATUS_CODE_START_TASK -> {
                            MyDialog.showMyDialog(view.context, view.context.getString(R.string.string_task_start_title),
                                    it.tips, true)
                            modelVM.resetTaskStatus(1)
                            data.filter { model -> model.id.get() == it.id }
                                    .forEach { position = data.indexOf(it) }
                            if (position != -1) {
                                view.notifyUI(position)
                            }
                        }
                        TaskManager.STATUS_CODE_HAS_START -> {
                            modelVM.taskStatus.set(1)
                            MyDialog.showMyDialog(view.context, view.context.getString(R.string.string_task_has_start_title),
                                    it.tips, true)
                        }
                        TaskManager.STATUS_CODE_START_TASK_ERROR -> {
                            MyDialog.showMyDialog(view.context, view.context.getString(R.string.string_task_has_start_title),
                                    it.tips, true)
                        }
                        TaskManager.STATUS_CODE_SUC_TASK -> {
                            initData()
                            MyDialog.showMyDialog(view.context, view.context.getString(R.string.string_task_suc_title),
                                    it.tips, true)
                        }
                    }
                }
    }

    override fun initData() {
        data = ArrayList()
        var data = DatabaseManager.getInstance().taskDao.queryBuilder().list()
        for (it in data) {
            this.data.add(TaskModelVM(it))
        }
        view.showData(this.data)
    }

    override fun subscribe(view: TaskContract.ITaskView?) {
        this.view = view!!
    }

    override fun unSubscribe() {
    }
}