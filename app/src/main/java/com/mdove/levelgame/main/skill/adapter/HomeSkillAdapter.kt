package com.mdove.levelgame.main.skill.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mdove.levelgame.R
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.databinding.ItemHomeSkillBinding
import com.mdove.levelgame.main.skill.presenter.HomeSkillPresenter
import com.mdove.levelgame.main.skill.model.HomeSkillHandler
import com.mdove.levelgame.main.skill.model.HomeSkillModelVM
import com.mdove.levelgame.utils.InflateUtils

/**
 * Created by MDove on 2018/11/14.
 */
class HomeSkillAdapter(presenter: HomeSkillPresenter) : BaseListAdapter<HomeSkillModelVM>() {
    var presenter = presenter
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(InflateUtils.bindingInflate(parent!!, R.layout.item_home_skill))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ViewHolder).bind(data[position])
    }

    inner class ViewHolder(binding: ItemHomeSkillBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding = binding
        fun bind(model: HomeSkillModelVM) {
            binding.vm = model
            binding.handler = HomeSkillHandler(presenter)
        }
    }
}