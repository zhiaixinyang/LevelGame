package com.mdove.levelgame;

import android.app.Application;
import android.content.Context;

import com.mdove.levelgame.config.AppConfig;
import com.mdove.levelgame.greendao.AccessoriesDao;
import com.mdove.levelgame.greendao.AdventureDao;
import com.mdove.levelgame.greendao.AllGoodsDao;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.BigMonstersDao;
import com.mdove.levelgame.greendao.DaoSession;
import com.mdove.levelgame.greendao.DropGoodsDao;
import com.mdove.levelgame.greendao.HeroAttributesDao;
import com.mdove.levelgame.greendao.MainMenuDao;
import com.mdove.levelgame.greendao.MaterialDao;
import com.mdove.levelgame.greendao.MedicinesDao;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.MonstersPlaceDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.SkillDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.MainMenu;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.List;

/**
 * Created by MDove on 2018/2/9.
 */

public class App extends Application {
    public static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;

        DatabaseManager.getInstance().init(mAppContext, HeroAttributesDao.class, ArmorsDao.class,
                DropGoodsDao.class, MedicinesDao.class, MonstersDao.class, MonstersPlaceDao.class,
                PackagesDao.class, WeaponsDao.class, AllGoodsDao.class, BigMonstersDao.class, MaterialDao.class,
                AdventureDao.class, MainMenuDao.class, SkillDao.class, AccessoriesDao.class);

        CrashReport.setIsDevelopmentDevice(mAppContext, true);
        CrashReport.initCrashReport(getApplicationContext(), "544aec74cc", false);

//        HeroManager.getInstance().getHeroAttributes().days = 9;
//        HeroManager.getInstance().save();
        if (!AppConfig.isFirstLogin()) {
            // 初始化数据库
            InitDataFileUtils.initData();
            AppConfig.setFirstLogin();
        }
        resetPkSelectStatus();
    }

    // 没次重启，重置材料选择状态
    public void resetPkSelectStatus() {
        List<Packages> packages = DatabaseManager.getInstance().getPackagesDao().loadAll();
        if (packages != null && packages.size() > 0) {
            for (Packages pk : packages) {
                pk.isSelect = 1;
                DatabaseManager.getInstance().getPackagesDao().update(pk);
            }
        }
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

}