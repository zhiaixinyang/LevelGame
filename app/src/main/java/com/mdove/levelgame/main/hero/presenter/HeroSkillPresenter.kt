package com.mdove.levelgame.main.hero.presenter

import com.mdove.levelgame.greendao.PackagesDao
import com.mdove.levelgame.greendao.SkillDao
import com.mdove.levelgame.greendao.entity.Packages
import com.mdove.levelgame.greendao.entity.Skill
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.hero.model.HeroAttributesWrapper
import com.mdove.levelgame.main.hero.model.HeroSkillModelVM
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils

/**
 * Created by MDove on 2018/11/10.
 */
class HeroSkillPresenter : HeroSkillContract.IHeroSkillPresenter {
    var skillDao: SkillDao = DatabaseManager.getInstance().skillDao
    lateinit var view: HeroSkillContract.IHeroSkillView
    lateinit var data: ArrayList<HeroSkillModelVM>

    override fun subscribe(view: HeroSkillContract.IHeroSkillView?) {
        this.view = view!!
    }

    override fun unSubscribe() {
    }

    override fun onClickEquip(vm: HeroSkillModelVM) {
        var pk = DatabaseManager.getInstance().packagesDao.queryBuilder().where(PackagesDao.Properties.Type.eq(vm.type.get())).unique()
        pk.isEquip = if (pk.isEquip == 0) 1 else 0
        DatabaseManager.getInstance().packagesDao.update(pk)
        val position = data
                .firstOrNull { it.type.get().equals(pk.type) }
                ?.let {
                    it.reName(pk.isEquip == 0)
                    data.indexOf(it)
                }
                ?: -1
        if (position != -1) {
            view.notifyUI(position)
        }
        HeroAttributesWrapper.getInstance().resetSkill()
    }

    override fun initData() {
        this.data = ArrayList()
        var pkData = DatabaseManager.getInstance().packagesDao.loadAll()
        for (pk in pkData) {
            val dbTpe = AllGoodsToDBIdUtils.getInstance().getDBType(pk.type)
            if (dbTpe == AllGoodsToDBIdUtils.DB_TYPE_IS_SKILL) {
                val skill = skillDao.queryBuilder().where(SkillDao.Properties.Type.eq(pk.type)).unique()
                if (skill != null) {
                    var vm = HeroSkillModelVM(skill)
                    vm.reName(pk.isEquip == 0)
                    this.data.add(vm)
                }
            }
        }
        view.showData(this.data)
    }
}