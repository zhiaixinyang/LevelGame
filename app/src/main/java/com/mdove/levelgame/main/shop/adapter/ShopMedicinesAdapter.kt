package com.mdove.levelgame.main.shop.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mdove.levelgame.R
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.main.shop.adapter.viewholder.ShopMedicinesViewHolder
import com.mdove.levelgame.main.shop.model.mv.MedicinesModelVM
import com.mdove.levelgame.main.shop.presenter.ShopMedicinesPresenter
import com.mdove.levelgame.utils.InflateUtils

/**
 * @author MDove on 2018/12/23
 */
class ShopMedicinesAdapter(private val presenter: ShopMedicinesPresenter) : BaseListAdapter<MedicinesModelVM>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ShopMedicinesViewHolder(InflateUtils.bindingInflate(parent, R.layout.item_medicines))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(REFRESH_COUNT)) {
            (holder as? ShopMedicinesViewHolder)?.let { holder ->
                data[position]?.let {
                    holder.refreshCount(it, presenter)
                }
            }
        }else{
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as? ShopMedicinesViewHolder)?.let { holder ->
            data[position]?.let {
                holder.bind(it, presenter)
            }
        }
    }

    companion object {
        val REFRESH_COUNT = Any()
    }

    fun refreshCount(position: Int) {
        if (position > -1) {
            notifyItemChanged(position, REFRESH_COUNT)
        }
    }
}
