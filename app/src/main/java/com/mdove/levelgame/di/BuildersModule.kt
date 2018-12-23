package com.mdove.levelgame.di

import com.mdove.levelgame.main.shop.ShopActivity
import com.mdove.levelgame.main.shop.di.ShopMedicainesModule
import com.mdove.levelgame.main.shop.fragment.ShopMedicinesFragment
import com.mdove.levelgame.main.task.TaskActivity
import com.mdove.levelgame.main.task.di.TaskModule
import com.ss.android.buzz.dependencies.ActivityScope
import com.ss.android.buzz.dependencies.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by MDove on 2018/12/23.
 *
 * 参考：https://blog.csdn.net/qq_17766199/article/details/73030696
 */
@Module
abstract class BuildersModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [TaskModule::class])
    abstract fun taskActivityInjector(): TaskActivity

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun shopActivityInjector(): ShopActivity

    @FragmentScope
    @ContributesAndroidInjector(modules = [ShopMedicainesModule::class])
    abstract fun medicainesFragmentInjector(): ShopMedicinesFragment
}