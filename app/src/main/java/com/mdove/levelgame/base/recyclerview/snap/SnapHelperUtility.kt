package com.mdove.levelgame.base.recyclerview.snap

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper

fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}