package com.mdove.levelgame.base.network.utils;


import android.os.Build;
import android.webkit.CookieManager;

import com.mdove.levelgame.base.kotlin.Logger;

/**
 * Created by MDove on 2018/12/24.
 */
public class CookieManagerCompat {
    private static long sInitTimestamp = 0;
    private static boolean sFirstCall = true;
    private static boolean sDidInit = false;
    public static CookieManager getInstance() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            if (!sDidInit) {
                synchronized (CookieManagerCompat.class) {
                    if (!sDidInit) {
                        if (sFirstCall) {
                            sFirstCall = false;
                            sInitTimestamp = System.currentTimeMillis();
                        } else {
                            try {
                                Logger.d("CookieManagerCompat", "start sleep:" + Math.min(Math.max(0, 1500 - (System.currentTimeMillis() - sInitTimestamp)), 1500L));
                                Thread.sleep(Math.min(Math.max(0,1500 - (System.currentTimeMillis() - sInitTimestamp)), 1500L));
                                Logger.d("CookieManagerCompat", "Did sleep");
                            } catch (InterruptedException e) {
                            }
                            sDidInit = true;
                        }
                    }
                }
            }
        }
        Logger.d("CookieManagerCompat", "return");
        return CookieManager.getInstance();
    }

}


