package com.mdove.levelgame.utils

import androidx.recyclerview.widget.DiffUtil
import com.ss.android.article.ugc.ui.adapter.viewhodler.MusicStoreSongItemBinder.Companion.PAYLOAD_PLAYING_ICON
import com.ss.android.article.ugc.ui.bean.*

/**
 * Created by zhaojing on 2019/1/10.
 */
class UgcMusicStoreSongsDiffCallback : DiffUtil.Callback() {

    private var oldData = emptyList<Any>()
    private var data = oldData

    fun update(data: List<Any>) {
        oldData = this.data
        this.data = data
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition]
        val newItem = data[newItemPosition]

        if (oldItem is MusicStoreSongItem && newItem is MusicStoreSongItem) {
            return oldItem.songItem.id == newItem.songItem.id
        }

        if (oldItem is MusicStoreCategoryItem && newItem is MusicStoreCategoryItem) {
            return oldItem.category.id == newItem.category.id
        }

        if (oldItem is MusicStoreLoadStatusLoading && newItem is MusicStoreLoadStatusLoading) {
            return true
        }

        if (oldItem is MusicStoreLoadStatusLoadingError && newItem is MusicStoreLoadStatusLoadingError) {
            return true
        }

        if (oldItem is BaseMusicStoreListItem && newItem is BaseMusicStoreListItem) {
            return oldItem == newItem
        }
        return oldItem === newItem
    }

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return data.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition]
        val newItem = data[newItemPosition]
        return if (oldItem is MusicStoreSongItem && newItem is MusicStoreSongItem) {
            oldItem.musicStatus == newItem.musicStatus
        } else {
            return true
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldData[oldItemPosition]
        val newItem = data[newItemPosition]
        return if (oldItem is MusicStoreSongItem && newItem is MusicStoreSongItem && oldItem.musicStatus != newItem.musicStatus
        ) {
            PAYLOAD_PLAYING_ICON
        } else {
            null
        }
    }
}