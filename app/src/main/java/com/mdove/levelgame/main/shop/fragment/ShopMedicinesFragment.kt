package com.mdove.levelgame.main.shop.fragment

import android.content.Context
import android.os.Bundle
import com.mdove.levelgame.base.BaseListFragment
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.main.shop.adapter.ShopMedicinesAdapter
import com.mdove.levelgame.main.shop.model.mv.MedicinesModelVM
import com.mdove.levelgame.main.shop.presenter.ShopMedicinesContract
import com.mdove.levelgame.main.shop.presenter.ShopMedicinesPresenter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by MDove on 2018/12/23.
 */
class ShopMedicinesFragment : BaseListFragment(), ShopMedicinesContract.IShopMedicinesView {

    @Inject
    lateinit var presenter: ShopMedicinesPresenter

    @Inject
    lateinit var adapter: ShopMedicinesAdapter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)

        super.onAttach(context)
    }

    override fun initData() {
        presenter.subscribe(this)
    }

    override fun loadData() {
        presenter.initData()
    }

    override fun createAdapter(): BaseListAdapter<MedicinesModelVM> {
        return adapter
    }

    override fun showData(data: List<MedicinesModelVM>) {
        adapter.data = data
    }

    override fun notifyUI(position: Int) {
        adapter.refreshCount(position)
    }

    companion object {

        fun newInstance(): ShopMedicinesFragment {
            val args = Bundle()

            val fragment = ShopMedicinesFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
