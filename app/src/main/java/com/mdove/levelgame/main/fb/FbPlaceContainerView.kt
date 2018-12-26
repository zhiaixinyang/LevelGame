package com.mdove.levelgame.main.fb

import android.content.Context
import android.support.transition.*
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import com.mdove.levelgame.R
import com.mdove.levelgame.main.fb.ui.FbMonstersEnterView
import com.mdove.levelgame.main.fb.ui.FbPlaceEnterView

/**
 * Created by MDove on 2018/12/26.
 */
class FbPlaceContainerView(context: Context) {
    val containerView: ViewGroup = FrameLayout(context).apply {
        setBackgroundResource(R.drawable.buzz_round_corner_dialog_bg)
    }

    private lateinit var scene1: Scene
    private lateinit var scene2: Scene
    private lateinit var currentScene: Scene
    private lateinit var transition1To2: TransitionSet
    private lateinit var transition2To1: TransitionSet
    private val fbPlaceContainerView: FbPlaceEnterView = FbPlaceEnterView(context)
    private val fbMonstersEnterView: FbMonstersEnterView = FbMonstersEnterView(context)

    init {
        initScenes()
    }

    private fun initScenes() {
        scene1 = Scene(containerView, fbPlaceContainerView)
        scene2 = Scene(containerView, fbMonstersEnterView)

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
        initListener()
    }

    private fun initListener() {
        fbPlaceContainerView.setListener {
            TransitionManager.endTransitions(containerView)
            TransitionManager.go(scene2, transition1To2)
            currentScene = scene2
        }
        fbMonstersEnterView.setListener {
            TransitionManager.endTransitions(containerView)
            TransitionManager.go(scene1, transition2To1)
            currentScene = scene1
        }
    }
}