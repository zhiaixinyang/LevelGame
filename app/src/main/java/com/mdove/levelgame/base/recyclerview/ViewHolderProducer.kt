package com.ss.android.uilib.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface ViewHolderProducer {
    companion object {
        const val VIEW_TYPE_EMPTY = Int.MAX_VALUE
        const val VIEW_TYPE_HEADER = Int.MAX_VALUE shr 1
        const val VIEW_TYPE_FOOTER = Int.MAX_VALUE shr 2
    }

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder)

    fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, payloads: List<Any>) {
        onBindViewHolder(viewHolder)
    }
}