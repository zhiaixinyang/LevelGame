package com.ss.android.uilib.recyclerview

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.mdove.levelgame.utils.log.LogUtils

class LinearLayoutManagerWrapper(context: Context?) : LinearLayoutManager(context) {
    private var TAG = "LinearLayoutManagerWrapper"
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        }catch (e : Exception){
            LogUtils.e(TAG, e.message)
        }
    }
}