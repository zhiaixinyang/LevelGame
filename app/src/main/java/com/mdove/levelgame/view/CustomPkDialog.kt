package com.mdove.levelgame.view

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.mdove.levelgame.R
import com.mdove.levelgame.base.adapter.CustomInfoAdapter
import com.mdove.levelgame.databinding.DialogCustomPkBinding
import com.mdove.levelgame.greendao.PackagesDao
import com.mdove.levelgame.greendao.entity.Packages
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.hero.manager.HeroManager
import com.mdove.levelgame.main.hero.model.CustomPkBaseViewModel
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils
import com.mdove.levelgame.utils.SystemUtils

/**
 * Created by MDove on 2018/12/31.
 */
class CustomPkDialog(context: Context, val pkId: Long) : AppCompatDialog(context, R.style.BaseDialog) {
    private var mBinding: DialogCustomPkBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_custom_pk,
            null, false)
    private var packages: Packages = DatabaseManager.getInstance().packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(pkId)).unique()

    init {
        setContentView(mBinding.getRoot())
        val paramsWindow = window!!.attributes
        paramsWindow.width = getWindowWidth()
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val allAttrInfos = mutableListOf<String>()
        AllGoodsToDBIdUtils.getInstance().getAttrsModelFromType(packages.type)?.attrsModel?.let {
            var name = it.name

            mBinding.vm = CustomPkBaseViewModel(name, it.tips, packages.type)
            it.baseAttack.takeIf {
                it != 0
            }?.let { attack ->
                var strengthenLevel = ""
                if (packages.strengthenLevel > 0) {
                    strengthenLevel += " + " + attack * (0.2 * packages.strengthenLevel) + attack
                } else {
                    strengthenLevel = attack.toString()
                }
                allAttrInfos.add("增加攻击：$strengthenLevel")
            }
            it.baseArmor.takeIf {
                it != 0
            }?.let { arrmor ->
                var strengthenLevel = ""
                if (packages.strengthenLevel > 0) {
                    strengthenLevel += " + " + arrmor * (0.2 * packages.strengthenLevel)
                } else {
                    strengthenLevel = arrmor.toString()
                }
                allAttrInfos.add("增加防御：$strengthenLevel")
            }
            it.baseLife.takeIf {
                it != 0
            }?.let { life ->
                var strengthenLevel = ""
                if (packages.strengthenLevel > 0) {
                    strengthenLevel += " + " + life * (0.2 * packages.strengthenLevel)
                } else {
                    strengthenLevel = life.toString()
                }
                allAttrInfos.add("增加血上限：$strengthenLevel")
            }
            it.baseAttackSpeed.takeIf {
                it > 0
            }?.let {
                var strengthenLevel = ""
                if (packages.strengthenLevel > 0) {
                    strengthenLevel += " + " + it * (0.1 * packages.strengthenLevel)
                } else {
                    strengthenLevel = it.toString()
                }
                allAttrInfos.add("降低攻击间隔：$strengthenLevel")
            }

            it.baseNeedLevel.takeIf {
                it > 0
            }?.let {
                val string = if (HeroManager.getInstance().heroAttributes.level >= it) {
                    "<font color=\"#4CAF50\">需要等级：$it</font>"
                } else {
                    "<font color=\"#F44336\">需要等级：$it</font>"
                }
                allAttrInfos.add(string)
            }
            it.baseNeedLiLiang.takeIf {
                it > 0
            }?.let {
                val string = if (HeroManager.getInstance().heroAttributes.liLiang >= it) {
                    "<font color=\"#4CAF50\">需要力量：$it</font>"
                } else {
                    "<font color=\"#F44336\">需要力量：$it</font>"
                }
                allAttrInfos.add(string)
            }
            it.baseNeedMinJie.takeIf {
                it > 0
            }?.let {
                val string = if (HeroManager.getInstance().heroAttributes.minJie >= it) {
                    "<font color=\"#4CAF50\">需要敏捷：$it</font>"
                } else {
                    "<font color=\"#F44336\">需要敏捷：$it</font>"
                }
                allAttrInfos.add(string)
            }
            it.baseNeedZhiHui.takeIf {
                it > 0
            }?.let {
                val string = if (HeroManager.getInstance().heroAttributes.zhiHui >= it) {
                    "<font color=\"#4CAF50\">需要智慧：$it</font>"
                } else {
                    "<font color=\"#F44336\">需要智慧：$it</font>"
                }
                allAttrInfos.add(string)
            }
            it.baseNeedQiangZhuang.takeIf {
                it > 0
            }?.let {
                val string = if (HeroManager.getInstance().heroAttributes.qiangZhuang >= it) {
                    "<font color=\"#4CAF50\">需要强壮：$it</font>"
                } else {
                    "<font color=\"#F44336\">需要强壮：$it</font>"
                }
                allAttrInfos.add(string)
            }
        }

        packages.getRandomAttr()?.let {
            it.randomAttack.takeIf {
                it > 0
            }?.let {
                allAttrInfos.add("<font color=\"#3D5AFE\">额外增加攻击：$it</font>")
            }
            it.randomArmor.takeIf {
                it > 0
            }?.let {
                allAttrInfos.add("<font color=\"#3D5AFE\">额外增加防御：$it</font>")
            }
            it.randomLife.takeIf {
                it > 0
            }?.let {
                allAttrInfos.add("<font color=\"#3D5AFE\">额外增加生命上限：$it</font>")
            }
            it.randomLiLiang.takeIf {
                it > 0
            }?.let {
                allAttrInfos.add("<font color=\"#3D5AFE\">额外增加力量：$it</font>")
            }
            it.randomMinJie.takeIf {
                it > 0
            }?.let {
                allAttrInfos.add("<font color=\"#3D5AFE\">额外增加敏捷：$it</font>")
            }
            it.randomZhiHui.takeIf {
                it > 0
            }?.let {
                allAttrInfos.add("<font color=\"#3D5AFE\">额外增加智慧：$it</font>")
            }
            it.randomQiangZhuang.takeIf {
                it > 0
            }?.let {
                allAttrInfos.add("<font color=\"#3D5AFE\">额外增加强壮：$it</font>")
            }
        }

        mBinding.rlv.layoutManager = LinearLayoutManager(context)
        val adapter = CustomInfoAdapter()
        mBinding.rlv.adapter = adapter
        adapter.data = allAttrInfos
    }

    private fun getWindowWidth(): Int {
        val percent = 0.9f
        val wm = this.window!!.windowManager
        val screenWidth = SystemUtils.getScreenWidth(wm)
        val screenHeight = SystemUtils.getScreenHeight(wm)
        return (if (screenWidth > screenHeight)
            screenHeight * percent
        else
            screenWidth * percent).toInt()
    }
}