package com.ss.android.network.threadpool

import com.mdove.levelgame.base.network.threadpool.SSThreadPoolProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
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

class MDovePool internal constructor(val executor: ExecutorService) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) =
            try {
                executor.execute(block)
            } catch (ignore: RejectedExecutionException) {
                // IGNORE
            }
}
