package com.mdove.levelgame.main.home.city

import android.app.ActionBar
import android.content.Context
import android.support.transition.*
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.mdove.levelgame.R
import com.mdove.levelgame.main.home.city.model.CityReps
import com.mdove.levelgame.main.home.city.ui.CityEnterLoadingView
import com.mdove.levelgame.main.home.city.ui.CityEnterView
import com.mdove.levelgame.main.home.city.ui.ICityView
import com.mdove.levelgame.view.DragRootView

/**
 * Created by MDove on 2018/12/26.
 */
class CityContainerView(context: Context) {
    private val containerView: ViewGroup = FrameLayout(context).apply {
        id = R.id.root_drag_view
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        setBackgroundResource(R.drawable.buzz_round_corner_dialog_bg)
    }

    val outerDrag: DragRootView = DragRootView(context).apply {
        layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        addView(containerView)
        setOnDragListener(object : DragRootView.OnDragListener {
            override fun canDragNow(dragView: View?): Boolean {
                return canDrag && !cityEnterView.canScrollDown()
            }

            override fun onExit(exitView: View?) {
                dismiss.invoke()
            }

        })
    }

    private lateinit var dismiss: () -> Unit
    private lateinit var loadingDismiss: (cityReps: CityReps) -> Unit
    private lateinit var scene1: Scene
    private lateinit var scene2: Scene
    private lateinit var currentScene: Scene
    private var canDrag = true
    private lateinit var transition1To2: TransitionSet
    private lateinit var transition2To1: TransitionSet
    private val cityEnterView: CityEnterView = CityEnterView(context)
    private val cityEnterLoadingView: CityEnterLoadingView = CityEnterLoadingView(context)
    private val pageControl by lazy {
        PageControl()
    }

    init {
        initCity()
        cityEnterLoadingView.registerLoadingDismiss {
            loadingDismiss.invoke(it)
        }
    }

    private fun initCity() {
        scene1 = Scene(containerView, cityEnterView)
        scene2 = Scene(containerView, cityEnterLoadingView)

        transition1To2 = TransitionSet().apply {
            addTransition(Slide(Gravity.END).apply {
                this.mode = Visibility.MODE_IN
            }.setInterpolator(AccelerateInterpolator()))
            addTransition(Slide(Gravity.START).apply {
                this.mode = Visibility.MODE_OUT
            }.setInterpolator(AccelerateInterpolator()))
        }

        transition2To1 = TransitionSet().apply {
            addTransition(Slide(Gravity.START).apply {
                this.mode = Visibility.MODE_IN
            }.setInterpolator(AccelerateInterpolator()))
            addTransition(Slide(Gravity.END).apply {
                this.mode = Visibility.MODE_OUT
            }.setInterpolator(AccelerateInterpolator()))
        }

        scene1.enter()
        currentScene = scene1

        (cityEnterView as ICityView).registerPageControl(pageControl)
        (cityEnterLoadingView as ICityView).registerPageControl(pageControl)

        pageControl.registerEnterCityLoading {
            canDrag = false
            TransitionManager.endTransitions(containerView)
            TransitionManager.go(scene2, transition1To2)
            currentScene = scene2
        }
    }

    fun registerLoadingDismiss(action: (cityReps: CityReps) -> Unit) {
        loadingDismiss = action
    }

    fun setOnDismissListener(action: () -> Unit) {
        dismiss = action
        cityEnterView.setDismissListener(action)
    }
}