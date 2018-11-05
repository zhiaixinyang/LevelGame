package com.mdove.levelgame.main.task.data

import com.mdove.levelgame.main.task.TaskPresenter

/**
 * @author MDove on 2018/11/5
 *
 */
class TaskItemHandler(presenter: TaskPresenter) {
    var presenter: TaskPresenter = presenter

    fun onItemBtnOnClick(modelVM: TaskModelVM) {
        presenter.onItemBtnOnClick(modelVM)
    }
}