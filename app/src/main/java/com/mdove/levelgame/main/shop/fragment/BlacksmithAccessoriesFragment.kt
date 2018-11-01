package com.mdove.levelgame.main.shop.fragment

import android.os.Bundle
import com.mdove.levelgame.base.BaseListFragment
import com.mdove.levelgame.base.BasePresenter
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.main.shop.adapter.BlacksmithAccessoriesAdapter
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM
import com.mdove.levelgame.main.shop.presenter.BlacksmithAccessoriesContract
import com.mdove.levelgame.main.shop.presenter.BlacksmithAccessoriesPresenter

/**
 * @author MDove on 2018/11/1
 *
 */
class BlacksmithAccessoriesFragment : BaseListFragment(), BlacksmithAccessoriesContract.IBlacksmithAccessoriesView {
    override fun showData(data: ArrayList<BlacksmithModelVM>) {
        adapter.data = data as List<Any>?
    }

    var presenter: BlacksmithAccessoriesPresenter = BlacksmithAccessoriesPresenter()

    companion object {
        fun newInstance(): BlacksmithAccessoriesFragment {
            val args: Bundle = Bundle()
            val fragment = BlacksmithAccessoriesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initData() {
        presenter.subscribe(this)
    }

    override fun loadData() {
        presenter.initData()

    }

    override fun createAdapter(): BaseListAdapter<BlacksmithModelVM> {
        return BlacksmithAccessoriesAdapter(presenter)
    }

}