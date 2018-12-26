package com.mdove.levelgame.base.kotlin

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import kotlinx.coroutines.*


/**
 * Created by MDove on 18/12/26.
 */
class LiveScope(
        val block: suspend CoroutineScope.() -> Unit
) : DefaultLifecycleObserver {

    private var scope : CoroutineScope? = null

    override fun onStart(owner: LifecycleOwner) {
        scope = CoroutineScope(Job() + Dispatchers.Main)
        scope?.launch {
            block()
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        scope?.coroutineContext?.cancel()
    }
}


fun LifecycleOwner.liveScope(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.addObserver(LiveScope(block))
}