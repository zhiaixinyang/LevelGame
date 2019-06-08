package com.mdove.levelgame.main.monsters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.mdove.levelgame.R
import com.mdove.levelgame.base.BaseActivity
import com.mdove.levelgame.greendao.entity.MonstersPlace
import com.mdove.levelgame.main.monsters.adapter.MonstersPlaceAdapter
import com.mdove.levelgame.main.monsters.model.MonstersPlaceModel
import com.mdove.levelgame.main.monsters.presenter.MonstersPlaceContract
import com.mdove.levelgame.main.monsters.presenter.MonstersPlacePresenter

/**
 * Created by MDove on 2018/10/21.
 */

class MonstersPlaceActivity : BaseActivity(), MonstersPlaceContract.IMonstersPlaceView {
    private lateinit  var rlv: RecyclerView
    private lateinit var adapter: MonstersPlaceAdapter
    private lateinit var presenter: MonstersPlacePresenter

    override fun isNeedCustomLayout(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle(R.string.activity_title_monsters_place)
        setContentView(R.layout.activity_monsters_place)
        rlv = findViewById(R.id.rlv)

        presenter = MonstersPlacePresenter()
        presenter.subscribe(this)

        adapter = MonstersPlaceAdapter(this, presenter)
        rlv.layoutManager = LinearLayoutManager(this)
        rlv.adapter = adapter

        presenter.initData()
    }

    override fun onResume() {
        super.onResume()
        presenter.initData()
    }

    override fun showData(data: List<MonstersPlace>) {
        adapter.setData(data)
    }

    companion object {
        fun start(context: Context) {
            val start = Intent(context, MonstersPlaceActivity::class.java)
            if (context !is Activity) {
                start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(start)
        }
    }
}
