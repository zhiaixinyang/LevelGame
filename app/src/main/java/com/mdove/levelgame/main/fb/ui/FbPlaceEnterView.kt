package com.mdove.levelgame.main.fb.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.mdove.levelgame.R
import com.mdove.levelgame.base.kotlin.setDebounceOnClickListener
import com.mdove.levelgame.main.fb.adapter.FbPlaceAdapter
import com.mdove.levelgame.main.fb.presenter.FbPlacePresenter
import com.mdove.levelgame.main.fb.presenter.contract.FbPlaceContract
import com.mdove.levelgame.main.fb.viewmodel.FbPlaceVM

/**
 * Created by MDove on 2018/12/26.
 */
class FbPlaceEnterView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        ConstraintLayout(context, attrs, defStyle), FbPlaceContract.IFbPlaceView {
    private var rlv: RecyclerView
    private var adapter: FbPlaceAdapter
    private var presenter: FbPlacePresenter

    init {
        View.inflate(context, R.layout.view_fb_place_enter_view, this)
        rlv = findViewById(R.id.rlv)
        presenter = FbPlacePresenter()
        adapter = FbPlaceAdapter(presenter)
        presenter.subscribe(this)
        presenter.initData()
    }

    fun setListener(action: () -> Unit) {
        findViewById<TextView>(R.id.tv_btn).setDebounceOnClickListener {
            action()
        }
    }

    override fun dismissLoadingDialog() {
    }

    override fun showData(data: MutableList<FbPlaceVM>?) {
        adapter.data = data
    }

    override fun getString(stringId: Int): String {
        return context.getString(stringId)
    }

    override fun showLoadingDialog(msg: String?) {
    }

}