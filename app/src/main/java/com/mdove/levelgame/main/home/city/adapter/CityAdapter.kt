package com.mdove.levelgame.main.fb.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mdove.levelgame.R
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.databinding.ItemCityBinding
import com.mdove.levelgame.main.home.city.model.CityItemHandler
import com.mdove.levelgame.main.home.city.model.CityVM
import com.mdove.levelgame.main.home.city.presenter.CityPresenter
import com.mdove.levelgame.utils.InflateUtils

/**
 * Created by MDove on 2018/12/28.
 */
class CityAdapter(val presenter: CityPresenter) : BaseListAdapter<CityVM>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(InflateUtils.bindingInflate(parent!!, R.layout.item_city))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ViewHolder).bind(data[position])
    }

    inner class ViewHolder(binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding = binding
        fun bind(model: CityVM) {
            binding.vm = model
            binding.handler = CityItemHandler(presenter)
        }

        fun refreshBtnName(btnName: String) {
        }
    }

}
