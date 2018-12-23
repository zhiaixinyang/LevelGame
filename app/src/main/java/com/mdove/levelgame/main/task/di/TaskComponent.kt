package com.mdove.levelgame.main.task.di

import com.mdove.levelgame.main.task.TaskPresenter
import dagger.Component

/**
 * Created by MDove on 2018/12/23.
 */
@Component
interface TaskComponent {
    fun getPresenter(): TaskPresenter
}
