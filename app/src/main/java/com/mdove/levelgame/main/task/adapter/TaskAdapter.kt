package com.mdove.levelgame.main.task.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mdove.levelgame.R
import com.mdove.levelgame.base.adapter.BaseListAdapter
import com.mdove.levelgame.databinding.ItemTaskBinding
import com.mdove.levelgame.main.task.TaskPresenter
import com.mdove.levelgame.main.task.data.TaskItemHandler
import com.mdove.levelgame.main.task.data.TaskModelVM
import com.mdove.levelgame.utils.InflateUtils

/**
 * Created by MDove on 2018/11/24.
 */
class TaskAdapter(presenter: TaskPresenter) : BaseListAdapter<TaskModelVM>() {
    var presenter = presenter
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(InflateUtils.bindingInflate(parent!!, R.layout.item_task))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as ViewHolder).bind(data[position])
    }

    inner class ViewHolder(binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding = binding
        fun bind(model: TaskModelVM) {
            binding.vm = model
            binding.handler = TaskItemHandler(presenter)
        }
    }
}