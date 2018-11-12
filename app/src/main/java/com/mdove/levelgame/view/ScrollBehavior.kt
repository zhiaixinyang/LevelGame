package com.mdove.levelgame.view

import android.support.design.widget.CoordinatorLayout
import android.view.View
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener

/**
 * Created by MDove on 2018/11/12.
 */
class ScrollBehavior : CoordinatorLayout.Behavior<View>() {
    var isAnimating: Boolean = false
    var listener: ViewPropertyAnimatorListener = object : ViewPropertyAnimatorListener {
        override fun onAnimationEnd(view: View?) {
            isAnimating = false
        }

        override fun onAnimationCancel(view: View?) {
            isAnimating = false
        }

        override fun onAnimationStart(view: View?) {
            isAnimating = true
        }
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View,
                                target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        if ((dyConsumed > 0 || dyUnconsumed > 0) && !isAnimating) {
            ViewCompat.animate(child).translationY(child.height.toFloat())
                    .setDuration(500)
                    .setListener(listener)
                    .start()
        } else if ((dyConsumed < 0 || dyUnconsumed < 0)) {
            ViewCompat.animate(child).translationY(0F)
                    .setDuration(500)
                    .setListener(listener)
                    .start()
        }
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

}