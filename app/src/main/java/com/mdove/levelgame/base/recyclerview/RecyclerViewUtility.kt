package com.mdove.levelgame.base.recyclerview

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import com.mdove.levelgame.base.recyclerview.snap.OnSnapPositionChangeListener
import com.mdove.levelgame.base.recyclerview.snap.SnapOnScrollListener

fun RecyclerView.attachSnapHelperWithListener(
        snapHelper: SnapHelper,
        behavior: SnapOnScrollListener.Behavior = SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
        onSnapPositionChangeListener: OnSnapPositionChangeListener
) {
    snapHelper.attachToRecyclerView(this)
    val snapOnScrollListener =
            SnapOnScrollListener(snapHelper, behavior, onSnapPositionChangeListener)
    addOnScrollListener(snapOnScrollListener)
}

fun DiffUtil.Callback.calculateDiff(detectMoves: Boolean = true): DiffUtil.DiffResult {
    return DiffUtil.calculateDiff(this, detectMoves)
}