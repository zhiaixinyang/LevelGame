package com.mdove.levelgame.main.monsters.presenter

import android.annotation.SuppressLint

import com.mdove.levelgame.R
import com.mdove.levelgame.config.AppConfig
import com.mdove.levelgame.greendao.MonstersDao
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.hero.HeroAttributesActivity
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager
import com.mdove.levelgame.main.hero.manager.HeroManager
import com.mdove.levelgame.main.home.HomeActivity
import com.mdove.levelgame.main.monsters.manager.MonsterAttackManager
import com.mdove.levelgame.main.monsters.manager.SpecialMonsterManager
import com.mdove.levelgame.main.monsters.manager.exception.AttackMonsterException
import com.mdove.levelgame.main.monsters.model.vm.BaseMonsterModelVM
import com.mdove.levelgame.main.monsters.model.vm.MenuMonsterModelVM
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM
import com.mdove.levelgame.main.shop.BusinessmanActivity
import com.mdove.levelgame.view.CustomMonsterDialog
import com.mdove.levelgame.view.FightingDialog
import com.mdove.levelgame.view.MyDialog

import java.math.BigDecimal
import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by MDove on 2018/10/21.
 */

class MonstersPresenter : MonstersContract.IMonstersPresenter {
    private lateinit var view: MonstersContract.IMonstersView
    private var realData: MutableList<BaseMonsterModelVM> = mutableListOf()
    private var monstersPlaceId: Long = 0

    override fun subscribe(view: MonstersContract.IMonstersView) {
        this.view = view
    }

    override fun unSubscribe() {}

    fun setPlaceId(monstersPlaceId: Long) {
        this.monstersPlaceId = monstersPlaceId
    }

    override fun initData(monstersPlaceId: Long) {
        // 特殊怪物出现设置
        SpecialMonsterManager.getInstance().setShowSpecialMonster()

        this.monstersPlaceId = monstersPlaceId
        val qb = DatabaseManager.getInstance().monstersDao.queryBuilder()
        val monsters = qb.whereOr(qb.and(MonstersDao.Properties.MonsterPlaceId.eq(monstersPlaceId), MonstersDao.Properties.IsShow.eq(0)),
                MonstersDao.Properties.MonsterPlaceId.eq(0)).list()
        realData = mutableListOf()
        realData.add(MenuMonsterModelVM())
        realData.addAll(monsters?.map { monster ->
            MonstersModelVM(monster)
        }?: mutableListOf())
        view.showData(realData)
    }

    override fun initPower() {
        view.showPowerText(String.format(view!!.getString(R.string.string_activity_monsters_power), HeroManager.getInstance().heroAttributes.bodyPower, 100))
    }

    override fun initMoney() {
        view.showMoneyText(String.format(view!!.getString(R.string.string_activity_monsters_money), HeroManager.getInstance().heroAttributes.money))
    }

    override fun initlife() {
        val heroAttributes = HeroManager.getInstance().heroAttributes
        val progress = heroAttributes.curLife.toFloat() / heroAttributes.maxLife
        val bg = BigDecimal(progress.toDouble())
        val real = bg.setScale(2, BigDecimal.ROUND_HALF_UP).toFloat()
        view.showLifeText((real * 100).toInt(), String.format(view!!.getString(R.string.string_activity_monsters_hero_life), heroAttributes.curLife, heroAttributes.maxLife))
    }

    override fun heroRest() {
        val restStatus = HeroAttributesManager.getInstance().heroRest()
        if (restStatus == 0 || restStatus == 2) {
            view!!.showLoadingDialog(view!!.context.getString(R.string.string_rest_loading_msg))
        } else if (restStatus == 1) {
            MyDialog.showMyDialog(view!!.context, view!!.getString(R.string.string_no_can_rest_title),
                    view!!.getString(R.string.string_no_can_rest_content),
                    view!!.getString(R.string.string_no_can_rest_nav_btn),
                    view!!.getString(R.string.string_no_can_rest_pos_btn), true) { HomeActivity.start(view!!.context) }
        }

        Observable.just(1)
                .delay(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { integer ->
                    if (restStatus == 2 && AppConfig.enableBigMonster()) {
                        MyDialog.showMyDialog(view!!.context, view!!.getString(R.string.string_big_monsters_show_title),
                                view!!.getString(R.string.string_big_monsters_show_content), true)
                    }
                    initPower()
                    initData(monstersPlaceId)
                    view.dismissLoadingDialog()
                }

    }

    override fun onItemMyPackage() {
        view.showMyPackage()
    }

    override fun onItemMyAttr() {
        HeroAttributesActivity.start(view.context)
    }

    @SuppressLint("CheckResult")
    override fun onItemBtnClick(type: String, id: Long?) {
        // 野外商人
        val monsters = DatabaseManager.getInstance().monstersDao.queryBuilder().where(MonstersDao.Properties.Id.eq(id)).unique()
        if (monsters != null && monsters.isBusinessman == 0) {
            BusinessmanActivity.start(view.context, id)
            return
        }
        // 战斗逻辑
        val dialog = FightingDialog(view.context, monsters)
        dialog.setOnDismissListener { dialog1 -> updateUI() }

        MonsterAttackManager.getInstance().attackEnemyPre(monsters)
                .subscribe(object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(aBoolean: Boolean) {
                        dialog.show()
                    }

                    override fun onError(e: Throwable) {
                        if (e is AttackMonsterException) {
                            when (e.errorCode) {
                                AttackMonsterException.ERROR_CODE_HERO_IS_NO_LIFE, AttackMonsterException.ERROR_CODE_HERO_NO_POWER, AttackMonsterException.ERROR_CODE_HERO_NO_COUNT, AttackMonsterException.ERROR_CODE_MONSTER_IS_QUICK_ATTACK, AttackMonsterException.ERROR_CODE_HERO_IS_QUICK_ATTACK_IS_DROP, AttackMonsterException.ERROR_CODE_MONSTERS_IS_DEAD -> {
                                    MyDialog.showMyDialog(view.context, e.errorTitle, e.errorMsg, true)
                                    updateUI()
                                }
                                else -> { }
                            }
                        }
                    }

                    override fun onComplete() {
                    }
                })
    }

    override fun onItemLongBtnOnClick(id: Long?) {
        CustomMonsterDialog(view.context, id!!).show()
    }

    private fun updateUI() {
        initPower()
        initMoney()
        initlife()
        initData(monstersPlaceId)
    }

    companion object {
        private val MONSTER_SHOP = "C12"
        private val MONSTER_DOCTOR = "C33"
    }
}
