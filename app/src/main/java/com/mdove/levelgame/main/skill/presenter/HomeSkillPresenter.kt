package com.mdove.levelgame.main.skill.presenter

import com.mdove.levelgame.R
import com.mdove.levelgame.base.RxTransformerHelper
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.skill.HeroSkillManager
import com.mdove.levelgame.main.skill.model.HomeSkillModelVM
import com.mdove.levelgame.utils.log.LogUtils
import com.mdove.levelgame.view.MyDialog

/**
 * Created by MDove on 2018/11/14.
 */
class HomeSkillPresenter : HomeSkillContract.ISkillPresenter {
    lateinit var view: HomeSkillContract.ISkillView
    lateinit var data: ArrayList<HomeSkillModelVM>
    override fun subscribe(view: HomeSkillContract.ISkillView?) {
        this.view = view!!
    }

    override fun unSubscribe() {
    }

    override fun initData() {
        data = ArrayList()
        var list = DatabaseManager.getInstance().skillDao.loadAll()
        list.filter { it -> it.belongType == "main6" }
                .forEach { it -> data.add(HomeSkillModelVM(it)) }
        view.showData(data)
    }

    override fun onClickStudy(vm: HomeSkillModelVM) {
        HeroSkillManager.instance.studySkill(vm.skill)
                .compose(RxTransformerHelper.schedulerTransf<HeroSkillManager.StudySkillResp>())
                .subscribe({ t ->
                    when (t.status) {
                        HeroSkillManager.CODE_STATUS_STUDY_SUC -> {
                            MyDialog.showMyDialog(view.context, view.getString(R.string.string_study_suc_title)
                                    , String.format(view.getString(R.string.string_study_suc_content), t.name, t.needSkillCount), true)
                        }
                        HeroSkillManager.CODE_STATUS_STUDY_ERROR -> {
                            MyDialog.showMyDialog(view.context, view.getString(R.string.string_study_err_title)
                                    , String.format(view.getString(R.string.string_study_err_content), t.name), true)
                        }
                        HeroSkillManager.CODE_STATUS_STUDY_ERROR_HAS_STUDY -> {
                            MyDialog.showMyDialog(view.context, view.getString(R.string.string_study_err_title)
                                    , String.format(view.getString(R.string.string_study_err_has_study_content), t.name), true)
                        }
                    }
                }, {
                })
    }
}