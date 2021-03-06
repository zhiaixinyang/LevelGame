package com.mdove.levelgame.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * Master of DAO (schema version 1): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Database db, boolean ifNotExists) {
        AllGoodsDao.createTable(db, ifNotExists);
        AccessoriesDao.createTable(db, ifNotExists);
        AdventureDao.createTable(db, ifNotExists);
        ArmorsDao.createTable(db, ifNotExists);
        BigMonstersDao.createTable(db, ifNotExists);
        CityDao.createTable(db, ifNotExists);
        DropGoodsDao.createTable(db, ifNotExists);
        FbMonstersDao.createTable(db, ifNotExists);
        FbPlaceDao.createTable(db, ifNotExists);
        HeroAttributesDao.createTable(db, ifNotExists);
        LiLianLevelDao.createTable(db, ifNotExists);
        MainMenuDao.createTable(db, ifNotExists);
        MaterialDao.createTable(db, ifNotExists);
        MedicinesDao.createTable(db, ifNotExists);
        MonstersDao.createTable(db, ifNotExists);
        MonstersPlaceDao.createTable(db, ifNotExists);
        PackagesDao.createTable(db, ifNotExists);
        PracticePlaceDao.createTable(db, ifNotExists);
        RandomAttrDao.createTable(db, ifNotExists);
        SkillDao.createTable(db, ifNotExists);
        TaskDao.createTable(db, ifNotExists);
        WeaponsDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        AllGoodsDao.dropTable(db, ifExists);
        AccessoriesDao.dropTable(db, ifExists);
        AdventureDao.dropTable(db, ifExists);
        ArmorsDao.dropTable(db, ifExists);
        BigMonstersDao.dropTable(db, ifExists);
        CityDao.dropTable(db, ifExists);
        DropGoodsDao.dropTable(db, ifExists);
        FbMonstersDao.dropTable(db, ifExists);
        FbPlaceDao.dropTable(db, ifExists);
        HeroAttributesDao.dropTable(db, ifExists);
        LiLianLevelDao.dropTable(db, ifExists);
        MainMenuDao.dropTable(db, ifExists);
        MaterialDao.dropTable(db, ifExists);
        MedicinesDao.dropTable(db, ifExists);
        MonstersDao.dropTable(db, ifExists);
        MonstersPlaceDao.dropTable(db, ifExists);
        PackagesDao.dropTable(db, ifExists);
        PracticePlaceDao.dropTable(db, ifExists);
        RandomAttrDao.dropTable(db, ifExists);
        SkillDao.dropTable(db, ifExists);
        TaskDao.dropTable(db, ifExists);
        WeaponsDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(AllGoodsDao.class);
        registerDaoClass(AccessoriesDao.class);
        registerDaoClass(AdventureDao.class);
        registerDaoClass(ArmorsDao.class);
        registerDaoClass(BigMonstersDao.class);
        registerDaoClass(CityDao.class);
        registerDaoClass(DropGoodsDao.class);
        registerDaoClass(FbMonstersDao.class);
        registerDaoClass(FbPlaceDao.class);
        registerDaoClass(HeroAttributesDao.class);
        registerDaoClass(LiLianLevelDao.class);
        registerDaoClass(MainMenuDao.class);
        registerDaoClass(MaterialDao.class);
        registerDaoClass(MedicinesDao.class);
        registerDaoClass(MonstersDao.class);
        registerDaoClass(MonstersPlaceDao.class);
        registerDaoClass(PackagesDao.class);
        registerDaoClass(PracticePlaceDao.class);
        registerDaoClass(RandomAttrDao.class);
        registerDaoClass(SkillDao.class);
        registerDaoClass(TaskDao.class);
        registerDaoClass(WeaponsDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

}
