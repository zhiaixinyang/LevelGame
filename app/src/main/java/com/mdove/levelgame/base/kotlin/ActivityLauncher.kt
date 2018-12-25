package com.mdove.levelgame.base.kotlin

import android.arch.lifecycle.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by MDove on 18/12/24.
 */
interface ActivityLauncher : LifecycleOwner {

    fun startActForResult(
            intent: Intent,
            requestCode: Int = REQUEST_CODE_NONE,
            options: Bundle? = null
    )

    fun addResultHandler(requestCode: Int, handler: IResultHandler)

    fun removeResultHandler(handler: IResultHandler): Boolean

    fun removeResultHandler(requestCode: Int): IResultHandler?

    interface IResultHandler {
        fun onActResult(
                launcher: ActivityLauncher,
                context: Context,
                requestCode: Int,
                resultCode: Int,
                data: Intent?
        ): Boolean
    }

    class ResultHandleHelper(private val launcher: ActivityLauncher) {
        private val resultHandlerMap = mutableMapOf<Int, IResultHandler>()
        private val isHandled = AtomicBoolean(false)

        fun onCreate() {
            launcher.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    resultHandlerMap.clear()
                    isHandled.set(false)
                }
            })
        }

        fun addHandler(requestCode: Int, handler: IResultHandler) {
            resultHandlerMap[requestCode] = handler
        }

        fun removeHandler(handler: IResultHandler): Boolean {
            val key = resultHandlerMap.keys.find { resultHandlerMap[it] == handler }
            return key?.let { resultHandlerMap.remove(it) != null } ?: false
        }

        fun removeHandler(requestCode: Int): IResultHandler? {
            return resultHandlerMap.remove(requestCode)
        }

        fun handleActivityResult(
                context: Context,
                requestCode: Int,
                resultCode: Int,
                data: Intent?
        ): Boolean {
            val handler = resultHandlerMap[requestCode] ?: return false

            if (handler.onActResult(launcher, context, requestCode, resultCode, data)) {
                isHandled.set(true)
                return true
            }

            isHandled.set(false)
            return false
        }
    }

    companion object {
        const val REQUEST_CODE_NONE = -1
    }
}
