package com.mdove.levelgame.main.lilian.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mdove.levelgame.R
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.databinding.ItemLilianLevelBinding
import com.mdove.levelgame.main.lilian.bean.LiLianLevelVM
import com.mdove.levelgame.utils.InflateUtils

/**
 * Created by MDove on 2019/2/2.
 */
class LiLianLevelAdapter : BaseListAdapter<LiLianLevelVM>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(InflateUtils.bindingInflate(parent!!, R.layout.item_lilian_level))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as? ViewHolder)?.let {
            it.bind(data[position])
        }
    }

    inner class ViewHolder(val binding: ItemLilianLevelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vm: LiLianLevelVM) {
            binding.vm = vm
        }
    }
}