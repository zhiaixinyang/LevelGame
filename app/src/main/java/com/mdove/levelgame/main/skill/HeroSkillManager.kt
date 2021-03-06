package com.mdove.levelgame.main.skill

import com.mdove.levelgame.greendao.PackagesDao
import com.mdove.levelgame.greendao.entity.Packages
import com.mdove.levelgame.greendao.entity.Skill
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.hero.manager.HeroManager
import io.reactivex.Observable

/**
 * Created by MDove on 2018/11/14.
 */
class HeroSkillManager private constructor() {
    private object Holder {
        val INSTANCE = HeroSkillManager()
    }

    companion object {
        val CODE_STATUS_STUDY_SUC = 1
        val CODE_STATUS_STUDY_ERROR = 2
        val CODE_STATUS_STUDY_ERROR_HAS_STUDY = 3
        val instance: HeroSkillManager by lazy { Holder.INSTANCE }
    }

    private fun hasStudy(type: String): Boolean {
        var hasStudy = false
        var pk = DatabaseManager.getInstance().packagesDao.queryBuilder().where(PackagesDao.Properties.Type.eq(type)).unique()
        if (pk != null) {
            hasStudy = true
        }
        return hasStudy
    }

    fun studySkill(skill: Skill): Observable<StudySkillResp> {
        var resp = StudySkillResp()
        var heroAttributes = HeroManager.getInstance().heroAttributes
        resp.name = skill.name
        if (hasStudy(skill.type)) {
            resp.status = CODE_STATUS_STUDY_ERROR_HAS_STUDY
            return Observable.create {
                it.onNext(resp)
            }
        }
        // 学习技能
        if (heroAttributes.skillCount >= skill.needSkillCount) {
            skill.hasStudy = 1
            DatabaseManager.getInstance().skillDao.update(skill)

            resp.status = CODE_STATUS_STUDY_SUC
            resp.skillId = skill.id
            resp.needSkillCount = skill.needSkillCount
            heroAttributes.skillCount -= skill.needSkillCount
            var pk = Packages()
            pk.isSelect = 1
            pk.isEquip = 1
            pk.strengthenLevel = 0
            pk.type = skill.type
            DatabaseManager.getInstance().packagesDao.insert(pk)
            HeroManager.getInstance().save()
        } else {
            resp.status = CODE_STATUS_STUDY_ERROR
        }
        return Observable.create {
            it.onNext(resp)
        }
    }

    inner class StudySkillResp {
        lateinit var name: String
        var skillId = 0L
        var status = 0
        var needSkillCount = 0
    }
}