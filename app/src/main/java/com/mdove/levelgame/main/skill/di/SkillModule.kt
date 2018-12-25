package com.mdove.levelgame.main.skill.di

import com.mdove.levelgame.main.skill.adapter.HomeSkillAdapter
import com.mdove.levelgame.main.skill.presenter.HomeSkillPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by MDove on 2018/12/23.
 */
@Module
class SkillModule {

    @Provides
    @Singleton
    fun providesSkillPresenter(): HomeSkillPresenter {
        return HomeSkillPresenter()
    }

    // 此场景完全不需要module，Inject能完成对HomeSkillAdapter的实例（仅为展现效果）
    @Provides
    fun providesSkillAdapter(presenter: HomeSkillPresenter): HomeSkillAdapter {
        return HomeSkillAdapter(presenter)
    }
}