package com.mdove.levelgame;

import android.content.Context;

import com.mdove.levelgame.config.AppConfig;
import com.mdove.levelgame.di.DaggerAppComponent;
import com.mdove.levelgame.greendao.AccessoriesDao;
import com.mdove.levelgame.greendao.AdventureDao;
import com.mdove.levelgame.greendao.AllGoodsDao;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.BigMonstersDao;
import com.mdove.levelgame.greendao.DropGoodsDao;
import com.mdove.levelgame.greendao.HeroAttributesDao;
import com.mdove.levelgame.greendao.LiLianLevelDao;
import com.mdove.levelgame.greendao.MainMenuDao;
import com.mdove.levelgame.greendao.MaterialDao;
import com.mdove.levelgame.greendao.MedicinesDao;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.MonstersPlaceDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.PracticePlaceDao;
import com.mdove.levelgame.greendao.SkillDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.net.ApiServer;
import com.mdove.levelgame.net.RetrofitUtil;
import com.mdove.levelgame.net.UrlConstants;

import java.util.List;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by MDove on 2018/2/9.
 */

public class App extends DaggerApplication {
    public static Context mAppContext;
    private static ApiServer mApiServer;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;

        DatabaseManager.getInstance().init(mAppContext, HeroAttributesDao.class, ArmorsDao.class,
                DropGoodsDao.class, MedicinesDao.class, MonstersDao.class, MonstersPlaceDao.class,
                PackagesDao.class, WeaponsDao.class, AllGoodsDao.class, BigMonstersDao.class, MaterialDao.class,
                AdventureDao.class, MainMenuDao.class, SkillDao.class, AccessoriesDao.class, LiLianLevelDao.class,
                PracticePlaceDao.class);

        //CrashReport.setIsDevelopmentDevice(mAppContext, true);
        //CrashReport.initCrashReport(getApplicationContext(), "b2c7f14477", true);

//        HeroManager.getInstance().getHeroAttributes().days = 9;
//        HeroManager.getInstance().save();
        if (!AppConfig.isHasLogin()) {
            // 初始化数据库
            InitDataFileUtils.initData();
            AppConfig.setHasLogin();
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

    public static ApiServer getApiServer() {
        if (mApiServer == null) {
            mApiServer = RetrofitUtil.create(UrlConstants.HOST_API_ONLINE, ApiServer.class);
        }
        return mApiServer;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}