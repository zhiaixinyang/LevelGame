package com.mdove.levelgame.greendao.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.DaoMaster;
import com.mdove.levelgame.greendao.DropGoodsDao;
import com.mdove.levelgame.greendao.HeroAttributesDao;
import com.mdove.levelgame.greendao.MedicinesDao;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.MonstersPlaceDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.HeroAttributes;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

/**
 * Created by MDove on 2018/2/10.
 */

public class DBUpdateHelper extends DaoMaster.OpenHelper {
    Class<? extends AbstractDao<?, ?>>[] daoClasses;

    public DBUpdateHelper(Context context, String name, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        super(context, name);
        this.daoClasses = daoClasses;
    }

    public DBUpdateHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        super(context, name, factory);
        this.daoClasses = daoClasses;
    }

    /**
     * 数据库升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            MigrationHelper.migrate(db, daoClasses);
        }
    }
}
