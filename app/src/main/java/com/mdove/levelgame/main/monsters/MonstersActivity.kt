package com.mdove.levelgame.main.monsters

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.widget.TextView

import com.mdove.levelgame.R
import com.mdove.levelgame.base.BaseActivity
import com.mdove.levelgame.main.hero.fragment.HeroPackageDialogFragment
import com.mdove.levelgame.main.monsters.adapter.MonstersAdapter
import com.mdove.levelgame.main.monsters.manager.AdventureManager
import com.mdove.levelgame.main.monsters.model.MonstersViewModel
import com.mdove.levelgame.main.monsters.model.vm.BaseMonsterModelVM
import com.mdove.levelgame.main.monsters.presenter.MonstersContract
import com.mdove.levelgame.main.monsters.presenter.MonstersPresenter
import com.mdove.levelgame.view.HorizontalSmoothProgressBar
import com.mdove.levelgame.view.MyDialog

/**
 * Created by MDove on 2018/10/21.
 */

class MonstersActivity : BaseActivity(), MonstersContract.IMonstersView {
    private lateinit var rlv: RecyclerView
    private lateinit var tvPower: TextView
    private lateinit var tvLife: TextView
    private lateinit var horizontalSmoothProgressBar: HorizontalSmoothProgressBar
    private lateinit var presenter: MonstersPresenter
    private lateinit var adapter: MonstersAdapter
    private var monsterPlaceId: Long = 0
    private var title: String? = null

    override fun isNeedCustomLayout(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monsters)
        monsterPlaceId = intent.getLongExtra(EXTRA_MONSTERS_PLACE_ID, -1)
        title = intent.getStringExtra(EXTRA_MONSTERS_PLACE_NAME)
        setTitle(title)

        presenter = MonstersPresenter()
        presenter.subscribe(this)
        presenter.setPlaceId(monsterPlaceId)

        adapter = MonstersAdapter(presenter)

        rlv = findViewById(R.id.rlv)
        tvPower = findViewById(R.id.tv_power)
        tvLife = findViewById(R.id.tv_life)
        horizontalSmoothProgressBar = findViewById(R.id.pb_hero)
        rlv.layoutManager = LinearLayoutManager(this)
        rlv.adapter = adapter
        presenter.initData(monsterPlaceId)
    }

    override fun onResume() {
        super.onResume()
        // 奇遇设置
        val isShow = AdventureManager.getInstance().setAdventure()
        presenter.initPower()
        presenter.initMoney()
        presenter.initlife()
        if (isShow) {
            presenter.initData(monsterPlaceId)
        }
    }

    override fun onToolbarBack() {
        MyDialog.showMyDialog(this@MonstersActivity, "确认离开？", "你确定要离开：" + title + "吗？", "取消", "确认", false) { finish() }
    }

    override fun onBackPressed(fromKey: Boolean) {
        MyDialog.showMyDialog(this@MonstersActivity, "确认离开？", "你确定要离开：" + title + "吗？", "取消", "确认", false) { finish() }
    }

    override fun attackUI(index: Int) {
        adapter.notifyAttackUI(index)
    }

    override fun showPowerText(content: String) {
        tvPower.text = Html.fromHtml(content)
    }

    override fun showMyPackage() {
        HeroPackageDialogFragment().show(supportFragmentManager, "")
    }

    override fun showLifeText(progress: Int, content: String) {
        tvLife.text = Html.fromHtml(content)
        horizontalSmoothProgressBar.progress = progress
    }

    override fun showMoneyText(content: String) {}

    override fun showData(data: List<BaseMonsterModelVM>) {
        adapter.update(data)
    }

    companion object {
        private val EXTRA_MONSTERS_PLACE_ID = "extra_monsters_place_id"
        private val EXTRA_MONSTERS_PLACE_NAME = "extra_monsters_place_name"

        fun start(context: Context, monsterPlaceId: Long, title: String) {
            val start = Intent(context, MonstersActivity::class.java)
            if (context !is Activity) {
                start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            start.putExtra(EXTRA_MONSTERS_PLACE_ID, monsterPlaceId)
            start.putExtra(EXTRA_MONSTERS_PLACE_NAME, title)
            context.startActivity(start)
        }
    }
}
