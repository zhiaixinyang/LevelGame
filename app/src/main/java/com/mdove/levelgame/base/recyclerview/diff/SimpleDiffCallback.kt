package com.mdove.levelgame.base.recyclerview.diff

import android.support.v7.util.DiffUtil

/**
 * Created by MDove on 2019/6/8.
 */
open abstract class SimpleDiffCallback<in T> : DiffUtil.Callback() {
    private var oldData = emptyList<T>()
    private var data = oldData

    fun update(data: List<T>) {
        oldData = this.data
        this.data = data
    }

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return data.size
    }

    abstract fun areItemsTheSameOuter(oldItem:T,newItem:T):Boolean
    abstract fun areContentsTheSameOuter(oldItem:T,newItem:T):Boolean
    protected open fun getChangePayloadOuter(oldItem:T,newItem:T):Any?{
        return null
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSameOuter(oldData[oldItemPosition],data[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSameOuter(oldData[oldItemPosition],data[newItemPosition])
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return getChangePayloadOuter(oldData[oldItemPosition],data[newItemPosition])
    }
}