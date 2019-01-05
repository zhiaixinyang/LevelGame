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
import com.mdove.levelgame.greendao.MonstersDao
import com.mdove.levelgame.greendao.PackagesDao
import com.mdove.levelgame.greendao.entity.Monsters
import com.mdove.levelgame.greendao.entity.Packages
import com.mdove.levelgame.greendao.utils.DatabaseManager
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager
import com.mdove.levelgame.main.hero.manager.HeroManager
import com.mdove.levelgame.main.hero.model.CustomPkBaseViewModel
import com.mdove.levelgame.main.monsters.manager.MonsterAttackManager
import com.mdove.levelgame.main.monsters.utils.DropGoodsManager
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils
import com.mdove.levelgame.utils.SystemUtils

/**
 * Created by MDove on 2019/1/4.
 */
class CustomMonsterDialog(context: Context, val monsterId: Long) : AppCompatDialog(context, R.style.BaseDialog) {
    private var mBinding: DialogCustomPkBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_custom_pk,
            null, false)

    init {
        setContentView(mBinding.getRoot())
        val paramsWindow = window!!.attributes
        paramsWindow.width = getWindowWidth()
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val allInfos = mutableListOf<String>()
        val monsters = DatabaseManager.getInstance().monstersDao.queryBuilder().where(MonstersDao.Properties.Id.eq(monsterId)).unique()
        monsters?.let { monster ->
            mBinding.vm = CustomPkBaseViewModel(monster.name, monster.tips, monster.type)
            allInfos.add("攻击力：${monster.attack}")
            allInfos.add("防御力：${monster.armor}")
            allInfos.add("总血量：${monster.life}")
            monster.isLimitCount.takeIf { isLimit ->
                isLimit >= 0
            }?.let {
                allInfos.add("还剩击杀次数：${monster.curCount} / ${monster.limitCount}")
            }
            monster.expLiLiang?.let {
                allInfos.add("击杀增加力量经验：${it[0]} ~ ${it[1]}")
            }
            monster.expMinJie?.let {
                allInfos.add("击杀增加敏捷经验：${it[0]} ~ ${it[1]}")
            }
            monster.expZhiHui?.let {
                allInfos.add("击杀增加智慧经验：${it[0]} ~ ${it[1]}")
            }
            monster.expQiangZhuang?.let {
                allInfos.add("击杀增加强壮经验：${it[0]} ~ ${it[1]}")
            }
            HeroAttributesManager.getInstance().dropGoods(monsters.dropGoodsId)?.let {
                it.forEach {
                    allInfos.add("击杀掉落装备：$it")
                }
            }
        }

        mBinding.rlv.layoutManager = LinearLayoutManager(context)
        val adapter = CustomInfoAdapter()
        mBinding.rlv.adapter = adapter
        adapter.data = allInfos
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