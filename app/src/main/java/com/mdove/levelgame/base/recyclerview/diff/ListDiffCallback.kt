package com.mdove.levelgame.base.recyclerview.diff

import android.support.v7.util.DiffUtil

abstract class ListDiffCallback<T> : DiffUtil.Callback() {

    private val oldItems = mutableListOf<IDataSnapshot<T>>()
    private val newItems = mutableListOf<IDataSnapshot<T>>()

    override fun getOldListSize() = oldItems.size
    override fun getNewListSize() = newItems.size

    fun update(newItems: List<T>?): ListDiffCallback<T> {
        this.oldItems.clear()
        this.oldItems.addAll(this.newItems)

        this.newItems.clear()
        newItems?.forEach {
            this.newItems.add(createSnapshot(it))
        }
        return this
    }

    final override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].referenceEquals(newItems[newItemPosition])
    }

    final override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].snapshotEquals(newItems[newItemPosition])
    }

    abstract fun createSnapshot(item: T): IDataSnapshot<T>
}