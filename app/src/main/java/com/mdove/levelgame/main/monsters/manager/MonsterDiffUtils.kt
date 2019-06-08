package com.mdove.levelgame.main.monsters.manager

import com.mdove.levelgame.base.recyclerview.diff.SimpleDiffCallback
import com.mdove.levelgame.main.monsters.adapter.MonstersAdapter.Companion.PAYLOAD_MONSTER_COUNT
import com.mdove.levelgame.main.monsters.model.vm.BaseMonsterModelVM
import com.mdove.levelgame.main.monsters.model.vm.MenuMonsterModelVM
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM

/**
 * Created by MDove on 2019/6/8.
 */
class MonsterDiffUtils : SimpleDiffCallback<BaseMonsterModelVM>() {

    override fun areItemsTheSameOuter(oldItem: BaseMonsterModelVM, newItem: BaseMonsterModelVM): Boolean {
        if (oldItem is MonstersModelVM && newItem is MonstersModelVM) {
            return oldItem.id == newItem.id
        }
        if (oldItem is MenuMonsterModelVM && newItem is MenuMonsterModelVM) {
            return oldItem.type == newItem.type
        }
        return oldItem === newItem
    }

    override fun areContentsTheSameOuter(oldItem: BaseMonsterModelVM, newItem: BaseMonsterModelVM): Boolean {
        return if (oldItem is MonstersModelVM && newItem is MonstersModelVM) {
            oldItem.curCount == newItem.curCount
        } else oldItem === newItem
    }

    override fun getChangePayloadOuter(oldItem: BaseMonsterModelVM, newItem: BaseMonsterModelVM): Any? {
        return if (oldItem is MonstersModelVM && newItem is MonstersModelVM && oldItem.curCount == newItem.curCount) {
            PAYLOAD_MONSTER_COUNT
        }else super.getChangePayloadOuter(oldItem, newItem)
    }
}