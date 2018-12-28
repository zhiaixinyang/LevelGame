package com.mdove.levelgame.main.fb

import android.content.Context
import android.support.transition.*
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import com.mdove.levelgame.R
import com.mdove.levelgame.main.city.PageControl
import com.mdove.levelgame.main.city.ui.CityEnterLoadingView
import com.mdove.levelgame.main.city.ui.ICityView
import com.mdove.levelgame.main.fb.ui.CityEnterView

/**
 * Created by MDove on 2018/12/26.
 */
class CityContainerView(context: Context) {
    val containerView: ViewGroup = FrameLayout(context).apply {
        setBackgroundResource(R.drawable.buzz_round_corner_dialog_bg)
    }

    private lateinit var dismiss: () -> Unit
    private lateinit var scene1: Scene
    private lateinit var scene2: Scene
    private lateinit var currentScene: Scene
    private lateinit var transition1To2: TransitionSet
    private lateinit var transition2To1: TransitionSet
    private val cityEnterView: CityEnterView = CityEnterView(context)
    private val cityEnterLoadingView: CityEnterLoadingView = CityEnterLoadingView(context)
    private val pageControl by lazy {
        PageControl()
    }

    init {
        initCity()
        cityEnterLoadingView.setDismiss {
            dismiss?.invoke()
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
            TransitionManager.endTransitions(containerView)
            TransitionManager.go(scene2, transition1To2)
            currentScene = scene2
        }
    }

    fun setDismiss(action: () -> Unit) {
        dismiss = action
    }

    fun setOnDismissListener(action: () -> Unit) {
        cityEnterView.setDismissListener(action)
    }
}