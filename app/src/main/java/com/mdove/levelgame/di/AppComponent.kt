package com.mdove.levelgame.di

import com.mdove.levelgame.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by MDove on 2018/12/23.
 *
 * 先只用AndroidInjectionModule编译，然后App中继承DaggerApplication...
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, BuildersModule::class])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}