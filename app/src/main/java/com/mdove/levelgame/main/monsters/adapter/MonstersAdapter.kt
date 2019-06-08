package com.mdove.levelgame.main.monsters.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mdove.levelgame.R
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.base.recyclerview.diff.SimpleDiffCallback
import com.mdove.levelgame.main.monsters.manager.MonsterDiffUtils
import com.mdove.levelgame.main.monsters.model.vm.BaseMonsterModelVM
import com.mdove.levelgame.main.monsters.model.vm.MenuMonsterModelVM
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM
import com.mdove.levelgame.main.monsters.presenter.MonstersPresenter
import com.mdove.levelgame.utils.InflateUtils

/**
 * Created by MDove on 2018/12/23.
 */

class MonstersAdapter(private val presenter: MonstersPresenter) : BaseListAdapter<BaseMonsterModelVM>() {

    companion object {
        val PAYLOAD_MONSTER_COUNT = Any()
    }

    override fun createDiffUtils(): SimpleDiffCallback<BaseMonsterModelVM> {
        return MonsterDiffUtils()
    }

    fun update(items: List<BaseMonsterModelVM>) {
        updateDataByDiffUtil(items)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getData()[position] is MenuMonsterModelVM) {
            0
        } else {
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            MonsterViewHolder(InflateUtils.bindingInflate(parent, R.layout.item_monsters), presenter)
        } else {
            MenuMonsterViewHolder(InflateUtils.bindingInflate(parent, R.layout.item_monster_menu), presenter)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MonsterViewHolder)?.let { holder ->
            data[position]?.let { vm ->
                holder.bind(vm as MonstersModelVM)
            }
        }
        (holder as? MenuMonsterViewHolder)?.let { menuHolder ->
            data[position]?.let {
                menuHolder.bind()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(PAYLOAD_MONSTER_COUNT)) {
            (holder as? MonsterViewHolder)?.let { holder ->
                data[position]?.let {
                    holder.refreshCount(it as MonstersModelVM)
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun notifyAttackUI(index: Int) {
        notifyItemChanged(index, PAYLOAD_MONSTER_COUNT)
    }
}
