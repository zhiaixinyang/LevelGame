package com.mdove.levelgame.base.kotlin

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by MDove on 18/12/24.
 */
interface JobHandler {
    val job: Job
}

val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->

}

val Fragment.contextJob: CoroutineContext
    get() = ((this as? JobHandler)?.job ?: (context as? JobHandler)?.job ?: Dispatchers.Default) + exceptionHandler

val Activity.contextJob: CoroutineContext
    get() = ((this as? JobHandler)?.job ?: Dispatchers.Default) + exceptionHandler

val Context?.contextJob: CoroutineContext
    get() = ((this as? JobHandler)?.job ?: Dispatchers.Default) + exceptionHandler
