package com.mdove.levelgame.main.fb.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.mdove.levelgame.R
import com.mdove.levelgame.base.kotlin.setDebounceOnClickListener
import com.mdove.levelgame.main.home.city.PageControl
import com.mdove.levelgame.main.home.city.model.CityVM
import com.mdove.levelgame.main.home.city.presenter.CityPresenter
import com.mdove.levelgame.main.home.city.presenter.contract.CityContract
import com.mdove.levelgame.main.home.city.ui.ICityView
import com.mdove.levelgame.main.fb.adapter.CityAdapter

/**
 * Created by MDove on 2018/12/28.
 */
class CityEnterView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        ConstraintLayout(context, attrs, defStyle), CityContract.ICityView, ICityView {

    private var rlv: RecyclerView
    private var mAdapter: CityAdapter
    private var presenter: CityPresenter
    private lateinit var pageControl: PageControl

    init {
        View.inflate(context, R.layout.view_city_enter_view, this)
        rlv = findViewById(R.id.rlv)
        rlv.layoutManager = LinearLayoutManager(context)
        presenter = CityPresenter()
        mAdapter = CityAdapter(presenter)
        rlv.adapter = mAdapter
        presenter.subscribe(this)
        presenter.initData()
    }

    fun setDismissListener(action: () -> Unit) {
        findViewById<TextView>(R.id.btn_close).setDebounceOnClickListener {
            action()
        }
    }

    override fun registerPageControl(pageControl: PageControl) {
        this.pageControl = pageControl
    }

    override fun onClickCity(placeId: Long) {
        pageControl.invokeActionCityLoadingClick(placeId)
    }

    override fun dismissLoadingDialog() {
    }

    override fun showData(data: MutableList<CityVM>?) {
        mAdapter.data = data
    }

    override fun getString(stringId: Int): String {
        return context.getString(stringId)
    }

    override fun showLoadingDialog(msg: String?) {
    }

}