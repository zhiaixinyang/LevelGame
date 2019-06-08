package com.mdove.levelgame.main.monsters.adapter

import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import com.mdove.levelgame.R
import com.mdove.levelgame.databinding.ItemMonstersPlaceAdventureBinding
import com.mdove.levelgame.databinding.ItemMonstersPlaceBinding
import com.mdove.levelgame.greendao.entity.MonstersPlace
import com.mdove.levelgame.main.monsters.model.MonstersPlaceModel
import com.mdove.levelgame.main.monsters.model.handler.MonstersPlaceItemHandler
import com.mdove.levelgame.main.monsters.model.vm.MonstersPlaceModelVM
import com.mdove.levelgame.main.monsters.presenter.MonstersPlacePresenter
import com.mdove.levelgame.utils.InflateUtils

/**
 * Created by MDove on 2018/10/21.
 */

class MonstersPlaceAdapter(private val context: Context, private val placePresenter: MonstersPlacePresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var data: List<MonstersPlace>

    override fun getItemViewType(position: Int): Int {
        return if (data[position].isAdventure == 0) {
            // 奇遇layout
            0
        } else {
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            AdventureViewHolder(InflateUtils.bindingInflate<ViewDataBinding>(parent, R.layout.item_monsters_place_adventure) as ItemMonstersPlaceAdventureBinding)
        } else {
            ViewHolder(InflateUtils.bindingInflate<ViewDataBinding>(parent, R.layout.item_monsters_place) as ItemMonstersPlaceBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ViewHolder)?.bind(data[position]) ?: (holder as? AdventureViewHolder)?.bind(data!![position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<MonstersPlace>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemMonstersPlaceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MonstersPlace) {
            binding.vm = MonstersPlaceModelVM(model)
            binding.handler = MonstersPlaceItemHandler(placePresenter)
        }
    }

    inner class AdventureViewHolder(private val binding: ItemMonstersPlaceAdventureBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MonstersPlace) {
            binding.vm = MonstersPlaceModelVM(model)
            binding.handler = MonstersPlaceItemHandler(placePresenter)
        }
    }
}
