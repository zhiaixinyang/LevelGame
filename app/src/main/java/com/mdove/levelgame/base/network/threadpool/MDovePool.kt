package com.ss.android.network.threadpool

import android.os.Build
import android.os.Handler
import android.os.Looper
import com.mdove.levelgame.base.network.threadpool.SSThreadPoolProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.android.asCoroutineDispatcher
import java.lang.reflect.Constructor
import java.util.concurrent.ExecutorService
import java.util.concurrent.RejectedExecutionException
import kotlin.coroutines.CoroutineContext

/**
 * Created by MDove on 2018/12/24.
 */
val MDoveApiPool = MDovePool(SSThreadPoolProvider.API_EXECUTOR)
val MDoveBackgroundPool = MDovePool(SSThreadPoolProvider.BACKGROUND_EXECUTOR)
val MDoveImmediatePool = MDovePool(SSThreadPoolProvider.IMMEDIATE_EXECUTOR)
val MDoveCommonPool = MDovePool(SSThreadPoolProvider.COMMON_EXECUTOR)
val FastMain = Looper.getMainLooper().asHandler(true).asCoroutineDispatcher("fast-main")

class MDovePool internal constructor(val executor: ExecutorService) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) =
            try {
                executor.execute(block)
            } catch (ignore: RejectedExecutionException) {
                // IGNORE
            }
}

internal fun Looper.asHandler(async: Boolean): Handler {
    // Async support was added in API 16.
    if (!async || Build.VERSION.SDK_INT < 16) {
        return Handler(this)
    }

    if (Build.VERSION.SDK_INT >= 28) {
        // TODO compile against API 28 so this can be invoked without reflection.
        val factoryMethod = Handler::class.java.getDeclaredMethod("createAsync", Looper::class.java)
        return factoryMethod.invoke(null, this) as Handler
    }

    val constructor: Constructor<Handler>
    try {
        constructor = Handler::class.java.getDeclaredConstructor(
                Looper::class.java,
                Handler.Callback::class.java, Boolean::class.javaPrimitiveType)
    } catch (ignored: NoSuchMethodException) {
        // Hidden constructor absent. Fall back to non-async constructor.
        return Handler(this)
    }
    return constructor.newInstance(this, null, true)
}
