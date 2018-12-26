package com.mdove.levelgame.main.fb.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mdove.levelgame.R
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.databinding.ItemFbPlaceBinding
import com.mdove.levelgame.main.fb.presenter.FbPlacePresenter
import com.mdove.levelgame.main.fb.viewmodel.FbPlaceVM
import com.mdove.levelgame.main.skill.adapter.HomeSkillAdapter
import com.mdove.levelgame.utils.InflateUtils

/**
 * Created by MDove on 2018/12/26.
 */
class FbPlaceAdapter(val presenter:FbPlacePresenter) :BaseListAdapter<FbPlaceVM>(){

    companion object {
        val PAYLOAD_HAS_STUDY = Any()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(InflateUtils.bindingInflate(parent!!, R.layout.item_fb_place))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ViewHolder).bind(data[position])
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(PAYLOAD_HAS_STUDY)) {
            (holder as? HomeSkillAdapter.ViewHolder)?.let { holder ->
                data[position]?.let {
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun refreshBtnName(position: Int) {
        if (position != -1) {
            notifyItemChanged(position, PAYLOAD_HAS_STUDY)
        }
    }

    inner class ViewHolder(binding: ItemFbPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding = binding
        fun bind(model: FbPlaceVM) {
            binding.vm = model
        }

        fun refreshBtnName(btnName: String) {
        }
    }

}
