package com.mdove.levelgame.main.lilian.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mdove.levelgame.R
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.databinding.ItemPracticePlaceBinding
import com.mdove.levelgame.main.lilian.bean.PracticePlaceVM
import com.mdove.levelgame.utils.InflateUtils

/**
 * Created by MDove on 2019/2/2.
 */
class PracticePlaceAdapter(val click: (vm: PracticePlaceVM) -> Unit) : BaseListAdapter<PracticePlaceVM>() {
    private val diffCallback = PracticePlaceDiffCallback()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(InflateUtils.bindingInflate(parent!!, R.layout.item_practice_place))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as? ViewHolder)?.let {
            it.bind(data[position])
        }
    }

    inner class ViewHolder(val binding: ItemPracticePlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vm: PracticePlaceVM) {
            binding.vm = vm
            binding.btn.setOnClickListener {
                click.invoke(vm)
            }
        }
    }

    fun updateData(newData: List<PracticePlaceVM>) {
        data = newData
        diffCallback.update(newData)
        DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(this)
    }
}