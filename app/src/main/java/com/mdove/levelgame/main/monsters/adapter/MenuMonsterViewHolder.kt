package com.mdove.levelgame.main.monsters.adapter

import android.support.v7.widget.RecyclerView
import com.mdove.levelgame.databinding.ItemMonsterMenuBinding
import com.mdove.levelgame.main.monsters.model.handler.MenuMonstersItemHandler
import com.mdove.levelgame.main.monsters.presenter.MonstersPresenter

/**
 * Created by MDove on 2019/1/27.
 */
class MenuMonsterViewHolder(private val binding: ItemMonsterMenuBinding, private val presenter: MonstersPresenter) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.handler = MenuMonstersItemHandler(presenter)
    }
}