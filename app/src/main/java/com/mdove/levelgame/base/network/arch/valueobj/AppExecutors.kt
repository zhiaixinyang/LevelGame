package com.mdove.levelgame.base.network.arch.valueobj

import java.util.concurrent.Executor

interface AppExecutors {
    val diskIO: Executor
    val networkIO: Executor
    val mainThread: Executor
}