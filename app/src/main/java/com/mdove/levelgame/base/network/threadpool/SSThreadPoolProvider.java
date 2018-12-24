package com.mdove.levelgame.base.network.threadpool;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by MDove on 2018/12/24.
 */
public class SSThreadPoolProvider {




    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 10;

    public static final ExecutorService IMMEDIATE_EXECUTOR = new SSThreadPoolExecutor(0, Integer.MAX_VALUE,
                                                                0L, TimeUnit.SECONDS,
                                                                new SynchronousQueue<Runnable>(),
                                                                new SSThreadFactory("SS-immediate"));

    public static final ExecutorService API_EXECUTOR = new SSThreadPoolExecutor(3, 3,
                                                                KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                                                                new LinkedBlockingQueue<Runnable>(),
                                                                new SSThreadFactory("SS-api"));

    public static final ExecutorService COMMON_EXECUTOR = new SSThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                                                                KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                                                                new LinkedBlockingQueue<Runnable>(),
                                                                new SSThreadFactory("SS-common"));

    public static final ExecutorService BACKGROUND_EXECUTOR = Executors.newSingleThreadExecutor(new SSThreadFactory("SS-low", SSThreadPriority.LOW));

    private static final Scheduler IMMEDIATE_SCHEDULER = Schedulers.from(IMMEDIATE_EXECUTOR);
    private static final Scheduler COMMON_SCHEDULER = Schedulers.from(COMMON_EXECUTOR);
    private static final Scheduler BACKGROUND_SCHEDULER = Schedulers.from(BACKGROUND_EXECUTOR);
    private static final Scheduler API_SCHEDULER = Schedulers.from(API_EXECUTOR);

    private static final Scheduler EVENT_SCHEDULER = Schedulers.from(Executors.newSingleThreadExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncEventIo");
        }
    }));

    private static final HandlerThread SINGLE_THREAD = new HandlerThread("SS-global-single-thread");

    static {
        SINGLE_THREAD.start();
    }

    private static Handler THREAD_HANDLER = new Handler(SINGLE_THREAD.getLooper());

    public static Scheduler getImmediateScheduler() {
        return IMMEDIATE_SCHEDULER;
    }

    public static Scheduler getApiScheduler() {
        return API_SCHEDULER;
    }

    public static Scheduler getCommonScheduler() {
        return COMMON_SCHEDULER;
    }

    public static Scheduler getBackgroundScheduler() {
        return BACKGROUND_SCHEDULER;
    }

    public static ExecutorService getCommonExecutor() {
        return COMMON_EXECUTOR;
    }

    public static Future<?> immediateSubmit(Runnable r) {
        if ( r != null ) {
            return IMMEDIATE_EXECUTOR.submit(r);
        }

        return null;
    }

    public static Future<?> commonSubmit(Runnable r) {
        if ( r != null ) {
            return COMMON_EXECUTOR.submit(r);
        }

        return null;
    }

    public static Future<?> backgroundSubmit(Runnable r) {
        if ( r != null ) {
            return BACKGROUND_EXECUTOR.submit(r);
        }

        return null;
    }

   public static Future<?> apiSubmit(Runnable r) {
        if ( r != null ) {
            return API_EXECUTOR.submit(r);
        }

        return null;
    }

    public static Looper getThreadLooper() {
        return SINGLE_THREAD.getLooper();
    }

    public static Handler getThreadHandler() {
        return THREAD_HANDLER;
    }

    public static Scheduler getEventScheduler () {
        return EVENT_SCHEDULER;
    }

}
