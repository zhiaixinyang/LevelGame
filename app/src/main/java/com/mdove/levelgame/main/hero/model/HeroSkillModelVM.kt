package com.mdove.levelgame.main.hero.model

import android.databinding.ObservableField

import com.mdove.levelgame.greendao.entity.Skill
import com.mdove.levelgame.greendao.utils.SrcIconMap

/**
 * @author MDove on 2018/11/10
 */
class HeroSkillModelVM(skill: Skill) {
    var pkId = ObservableField<Long>()
    var name = ObservableField<String>()
    var btnName = ObservableField<String>()
    var tips = ObservableField<String>()
    var type = ObservableField<String>()
    var src = ObservableField<Int>()

    init {
        pkId.set(skill.id)
        this.tips.set(skill.tips)
        src.set(SrcIconMap.getInstance().getSrc(skill.type))
        this.type.set(skill.type)
        this.name.set(skill.name)
    }

    fun reName(isEquip: Boolean) {
        if (isEquip) {
            btnName.set("卸下技能")
        } else {
            btnName.set("装备技能")
        }
    }
}
