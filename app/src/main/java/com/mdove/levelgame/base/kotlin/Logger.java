package com.mdove.levelgame.base.kotlin;

import android.util.Log;

/**
 * Log wrapper
 */
public class Logger {

    static volatile int mLevel = Log.INFO;

    public static void setLogLevel(int level) {
        mLevel = level;
    }

    public static int getLogLevel() {
        return mLevel;
    }

    public static boolean debug() {
        return mLevel <= Log.DEBUG;
    }

    public static void v(String tag, String msg) {
        if (mLevel <= Log.VERBOSE)
            Log.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (mLevel <= Log.VERBOSE)
            Log.v(tag, msg, tr);
    }

    public static void d(String tag, String msg) {
        if (mLevel <= Log.DEBUG)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (mLevel <= Log.DEBUG)
            Log.d(tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        if (mLevel <= Log.INFO)
            Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (mLevel <= Log.INFO)
            Log.i(tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        if (mLevel <= Log.WARN)
            Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (mLevel <= Log.WARN)
            Log.w(tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        if (mLevel <= Log.ERROR)
            Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (mLevel <= Log.ERROR)
            Log.e(tag, msg, tr);
    }

    public static void st(String tag, int depth) {
        try {
            throw new Exception();
        } catch (Exception e) {
            StackTraceElement[] elems = e.getStackTrace();
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < Math.min(depth, elems.length); i++) {
                if (i > 1) {
                    sb.append("\n");
                }
                sb.append(getSimpleClassName(elems[i].getClassName()));
                sb.append(".");
                sb.append(elems[i].getMethodName());
            }
            v(tag, sb.toString());
        }
    }

    private static String getSimpleClassName(String fullClassName) {
        int index = fullClassName.lastIndexOf('.');
        if (index < 0) {
            return fullClassName;
        }
        return fullClassName.substring(index + 1);
    }

    public static void throwException(Throwable exception) {
        if (Logger.debug()) {
            w("", "", exception);
        }
    }

    public static void alertErrorInfo(String errorMsg) {
        if (Logger.debug()) {
            throw new IllegalStateException(errorMsg);
        }
    }
}
