package com.mdove.levelgame.utils.log;

/**
 * @author wangwei on 2016/12/7.
 *         wangwei@jiandaola.com
 */

public interface ILog {

    void d(String tag, String msg);

    void d(String tag, String msg, Throwable throwable);

    void i(String tag, String msg);

    void i(String tag, String msg, Throwable throwable);

    void w(String tag, String msg);

    void w(String tag, String msg, Throwable throwable);

    void e(String tag, String msg);

    void e(String tag, String msg, Throwable throwable);
}
