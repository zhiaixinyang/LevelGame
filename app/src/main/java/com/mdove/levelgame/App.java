package com.mdove.levelgame;

import android.app.Application;
import android.content.Context;

import com.mdove.levelgame.config.AppConfig;
import com.mdove.levelgame.greendao.DaoSession;
import com.mdove.levelgame.greendao.HeroAttributesDao;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.utils.DaoManager;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by MDove on 2018/2/9.
 */

public class App extends Application {
    public static Context mAppContext;
    private static DaoSession mDaoSession;
    private DaoManager mDaoManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;

        mDaoManager = DaoManager.getInstance();

        CrashReport.setIsDevelopmentDevice(mAppContext, true);
        CrashReport.initCrashReport(getApplicationContext(), "544aec74cc", false);

        mDaoManager.init(mAppContext);
        if (mDaoSession == null) {
            synchronized (App.class) {
                if (null == mDaoSession) {
                    mDaoSession = mDaoManager.getDaoMaster().newSession();
                }
            }
        }

        if (!AppConfig.isFirstLogin()) {
            HeroAttributesDao dao = getDaoSession().getHeroAttributesDao();
            HeroAttributes heroAttributes = new HeroAttributes();
            heroAttributes.armor = 10;
            heroAttributes.armorIncrease = 3;
            heroAttributes.attack = 20;
            heroAttributes.attackIncrease = 5;
            heroAttributes.baseExp = 100;
            heroAttributes.experience = 0;
            heroAttributes.expMultiple = 2;
            heroAttributes.money = 0;
            heroAttributes.level = 1;
            heroAttributes.curLife = 100;
            heroAttributes.maxLife = 100;
            heroAttributes.lifeIncrease = 20;
            dao.insert(heroAttributes);
            AppConfig.setFirstLogin();
        }
        if (AppConfig.getDBVersion() == 0) {
            InitDataFileUtils.initData();
        }
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

}