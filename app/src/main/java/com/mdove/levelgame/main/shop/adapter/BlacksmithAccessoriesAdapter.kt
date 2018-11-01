package com.mdove.levelgame.main.shop.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mdove.levelgame.R
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.databinding.ItemBlacksmithAccessoriesBinding
import com.mdove.levelgame.main.shop.model.handler.BlacksmithAccessoriesItemHandler
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM
import com.mdove.levelgame.main.shop.presenter.BlacksmithAccessoriesPresenter
import com.mdove.levelgame.utils.InflateUtils

/**
 * @author MDove on 2018/11/1
 *
 */
class BlacksmithAccessoriesAdapter constructor(presenter: BlacksmithAccessoriesPresenter) : BaseListAdapter<BlacksmithModelVM>() {
    var presenter: BlacksmithAccessoriesPresenter =presenter

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(InflateUtils.bindingInflate(parent!!, R.layout.item_blacksmith_accessories))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ViewHolder).bind(data[position])
    }

    inner class ViewHolder constructor(binding: ItemBlacksmithAccessoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ItemBlacksmithAccessoriesBinding = binding

        fun bind(vm: BlacksmithModelVM) {
            binding.vm = vm
            binding.handler = BlacksmithAccessoriesItemHandler(this@BlacksmithAccessoriesAdapter.presenter)
        }
    }

}