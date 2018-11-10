package com.mdove.levelgame.main.hero.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mdove.levelgame.R
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.databinding.ItemHeroSkillBinding
import com.mdove.levelgame.main.hero.model.HeroSkillModelVM
import com.mdove.levelgame.main.hero.model.handler.HeroSkillHandler
import com.mdove.levelgame.main.hero.presenter.HeroSkillPresenter
import com.mdove.levelgame.utils.InflateUtils

/**
 * Created by MDove on 2018/11/10.
 */
class HeroSkillAdapter(presenter: HeroSkillPresenter) : BaseListAdapter<HeroSkillModelVM>() {
    var presenter = presenter
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(InflateUtils.bindingInflate(parent!!, R.layout.item_hero_skill))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ViewHolder).bind(data[position])
    }

    inner class ViewHolder(binding: ItemHeroSkillBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding = binding
        fun bind(model: HeroSkillModelVM) {
            binding.vm = model
            binding.handler = HeroSkillHandler(presenter)
        }
    }
}