package com.mdove.levelgame.main.task

import com.mdove.levelgame.base.BasePresenter
import com.mdove.levelgame.base.BaseView
import com.mdove.levelgame.main.task.data.TaskModelVM

/**
 * @author MDove on 2018/11/5
 *
 */
class TaskContract {
    interface ITaskPresenter : BasePresenter<ITaskView> {
        fun initData()
        fun onItemBtnOnClick(modelVM: TaskModelVM)
    }

    interface ITaskView : BaseView {
        fun showData(data: ArrayList<TaskModelVM>)
    }
}