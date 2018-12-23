package com.mdove.levelgame.main.task.di

import com.mdove.levelgame.main.task.TaskPresenter
import com.mdove.levelgame.main.task.adapter.TaskAdapter
import dagger.Module
import dagger.Provides

/**
 * Created by MDove on 2018/12/23.
 */
@Module
class TaskModule {
    @Provides
    fun providesTaskAdapter(presenter: TaskPresenter): TaskAdapter {
        return TaskAdapter(presenter)
    }
}