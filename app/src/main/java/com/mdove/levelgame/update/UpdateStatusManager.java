package com.mdove.levelgame.update;

import com.mdove.levelgame.config.AppConfig;
import com.mdove.levelgame.utils.DateUtils;

/**
 * Created by MDove on 2018/11/14.
 */

public class UpdateStatusManager {
    public static boolean isShowUpdateDialog() {
        long curTime = System.currentTimeMillis();
        if (AppConfig.getAppOrderTodayTime()==0){
            AppConfig.setAppOrderTodayTime(curTime);
            return true;
        }
        boolean isSame= DateUtils.isSameDay(curTime,AppConfig.getAppOrderTodayTime());
        if (!isSame){
            AppConfig.setAppOrderTodayTime(curTime);
            return true;
        }
        return false;
    }
}
