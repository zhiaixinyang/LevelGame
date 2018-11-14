package com.mdove.levelgame.main.skill.model

import android.databinding.ObservableField
import com.mdove.levelgame.App
import com.mdove.levelgame.R
import com.mdove.levelgame.greendao.entity.Skill
import com.mdove.levelgame.greendao.utils.SrcIconMap

/**
 * Created by MDove on 2018/11/14.
 */
class HomeSkillModelVM(skill: Skill) {
    var skill=skill
    var pkId = ObservableField<Long>()
    var name = ObservableField<String>()
    var btnName = ObservableField<String>()
    var needSkillCount = ObservableField<String>()
    var tips = ObservableField<String>()
    var type = ObservableField<String>()
    var src = ObservableField<Int>()

    init {
        pkId.set(skill.id)
        tips.set(skill.tips)
        src.set(SrcIconMap.getInstance().getSrc(skill.type))
        type.set(skill.type)
        name.set(skill.name)
        needSkillCount.set(String.format(App.getAppContext().getString(R.string.string_need_skill_count), skill.needSkillCount))
        btnName.set("学习")
    }
}