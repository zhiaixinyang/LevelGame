package com.mdove.levelgame.main.monsters.adapter

import android.support.v7.widget.RecyclerView
import com.mdove.levelgame.databinding.ItemMonstersBinding
import com.mdove.levelgame.main.monsters.model.handler.MonstersItemHandler
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM
import com.mdove.levelgame.main.monsters.presenter.MonstersPresenter

/**
 * Created by MDove on 2018/12/23.
 */
class MonsterViewHolder(private val binding: ItemMonstersBinding, private val presenter: MonstersPresenter) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vm: MonstersModelVM) {
        binding.vm = vm
        binding.handler = MonstersItemHandler(presenter)
    }

    fun refreshCount(vm: MonstersModelVM) {
        binding.tvLimit.text = vm.limitCount.get()
        binding.handler = MonstersItemHandler(presenter)
    }
}
