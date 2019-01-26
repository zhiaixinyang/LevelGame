package com.mdove.levelgame.main.home.city.ui

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
import com.mdove.levelgame.main.home.city.adapter.CityAdapter
import com.mdove.levelgame.main.home.city.model.CityReps
import com.mdove.levelgame.main.home.city.model.CityVM
import com.mdove.levelgame.main.home.city.presenter.CityPresenter
import com.mdove.levelgame.main.home.city.presenter.contract.CityContract
import com.mdove.levelgame.view.MyDialog

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

    override fun onClickCity(cityReps: CityReps) {
        pageControl.invokeActionCityLoadingClick(cityReps)
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

    override fun enterPlaceIsCur(placeTitle: String) {
        MyDialog.showMyDialog(context, getString(R.string.string_enter_place_error_title),
                String.format(getString(R.string.string_enter_place_error_content), placeTitle), true)
    }
}