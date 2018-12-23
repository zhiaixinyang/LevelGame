package com.mdove.levelgame.di

import com.mdove.levelgame.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by MDove on 2018/12/23.
 *
 * 先只用AndroidInjectionModule编译，然后App中继承DaggerApplication...
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, BuildersModule::class])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}