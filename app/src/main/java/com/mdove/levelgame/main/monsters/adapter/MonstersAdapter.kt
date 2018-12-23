package com.mdove.levelgame.main.monsters.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mdove.levelgame.R
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.databinding.ItemMonstersBinding
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM
import com.mdove.levelgame.main.monsters.presenter.MonstersPresenter
import com.mdove.levelgame.utils.InflateUtils

/**
 * Created by MDove on 2018/12/23.
 */

class MonstersAdapter(private val presenter: MonstersPresenter) : BaseListAdapter<MonstersModelVM>() {
    companion object {
        val PAYLOAD_MONSTER_COUNT = Any()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MonsterViewHolder(InflateUtils.bindingInflate(parent, R.layout.item_monsters), presenter)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MonsterViewHolder)?.let { holder ->
            data[position]?.let {
                holder.bind(it)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(PAYLOAD_MONSTER_COUNT)) {
            (holder as? MonsterViewHolder)?.let { holder ->
                data[position]?.let {
                    holder.refreshCount(it)
                }
            }
        }else{
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun notifyAttackUI(index: Int) {
        notifyItemChanged(index, PAYLOAD_MONSTER_COUNT)
    }
}
