package com.mdove.levelgame.main.skill.di

import com.mdove.levelgame.main.skill.HomeSkillActivity
import dagger.Component

/**
 * Created by MDove on 2018/12/23.
 *
 * 这种是传统写法，可以通过AndroidInjector达到更解耦
 */
@Component(modules = [SkillModule::class])
interface SkillComponent {
    // Inject的属性，会直接通过activity注入过去
    fun inject(activity: HomeSkillActivity)
}