package com.mdove.levelgame.base.network.utils;

/**
 * Created by MDove on 2018/12/24.
 */
public class ApiDeniedException extends IllegalStateException {

    public static final int CODE_NO_PERMISSION = 403;

    private final int code;

    public ApiDeniedException(int code) {
        this.code = code;
    }

    public ApiDeniedException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
