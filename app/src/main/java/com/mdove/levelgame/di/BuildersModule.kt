package com.mdove.levelgame.di

import com.mdove.levelgame.main.task.TaskActivity
import com.mdove.levelgame.main.task.di.TaskModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by MDove on 2018/12/23.
 */
@Module
abstract class BuildersModule {
    @ContributesAndroidInjector(modules = [TaskModule::class])
    abstract fun taskActivityInjector(): TaskActivity
}