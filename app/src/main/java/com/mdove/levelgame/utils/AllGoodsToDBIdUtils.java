package com.mdove.levelgame.utils;

import com.mdove.levelgame.greendao.AccessoriesDao;
import com.mdove.levelgame.greendao.AllGoodsDao;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.MaterialDao;
import com.mdove.levelgame.greendao.MedicinesDao;
import com.mdove.levelgame.greendao.SkillDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.model.BaseBlacksmithModel;
import com.mdove.levelgame.model.IAttrsModel;

/**
 * @author MDove on 2018/10/23
 */
public class AllGoodsToDBIdUtils {
    public static final int DB_TYPE_IS_ATTACK = 1;
    public static final int DB_TYPE_IS_ARMOR = 2;
    public static final int DB_TYPE_IS_MATERIALS = 3;
    public static final int DB_TYPE_IS_ACCESSORIES = 4;
    public static final int DB_TYPE_IS_SKILL = 5;
    public static final int DB_TYPE_IS_MEDICINES = 6;

    private static class SingletonHolder {
        static final AllGoodsToDBIdUtils INSTANCE = new AllGoodsToDBIdUtils();
    }

    public static AllGoodsToDBIdUtils getInstance() {
        return AllGoodsToDBIdUtils.SingletonHolder.INSTANCE;
    }

    private AllGoodsDao allGoodsDao;

    private AllGoodsToDBIdUtils() {
        allGoodsDao = DatabaseManager.getInstance().getAllGoodsDao();
    }

    public IAttrsModel getAttrsModelFromType(String type) {
        IAttrsModel ob = null;
        switch (getDBType(type)) {
            case DB_TYPE_IS_ATTACK: {
                ob = DatabaseManager.getInstance().getWeaponsDao().queryBuilder().where(WeaponsDao.Properties.Type.eq(type)).unique();
                break;
            }
            case DB_TYPE_IS_ARMOR: {
                ob = DatabaseManager.getInstance().getArmorsDao().queryBuilder().where(ArmorsDao.Properties.Type.eq(type)).unique();
                break;
            }
            case DB_TYPE_IS_ACCESSORIES: {
                ob = DatabaseManager.getInstance().getAccessoriesDao().queryBuilder().where(AccessoriesDao.Properties.Type.eq(type)).unique();
                break;
            }
            case DB_TYPE_IS_MATERIALS: {
                ob = DatabaseManager.getInstance().getMaterialDao().queryBuilder().where(MaterialDao.Properties.Type.eq(type)).unique();
                break;
            }
            case DB_TYPE_IS_MEDICINES: {
                ob = DatabaseManager.getInstance().getMedicinesDao().queryBuilder().where(MedicinesDao.Properties.Type.eq(type)).unique();
                break;
            }
        }
        return ob;
    }

    public BaseBlacksmithModel getBlacksmithModelFromType(String type) {
        BaseBlacksmithModel ob = null;
        switch (getDBType(type)) {
            case DB_TYPE_IS_ATTACK: {
                ob = DatabaseManager.getInstance().getWeaponsDao().queryBuilder().where(WeaponsDao.Properties.Type.eq(type)).unique();
                break;
            }
            case DB_TYPE_IS_ARMOR: {
                ob = DatabaseManager.getInstance().getArmorsDao().queryBuilder().where(ArmorsDao.Properties.Type.eq(type)).unique();
                break;
            }
            case DB_TYPE_IS_MATERIALS: {
                ob = DatabaseManager.getInstance().getMaterialDao().queryBuilder().where(MaterialDao.Properties.Type.eq(type)).unique();
                break;
            }
            case DB_TYPE_IS_ACCESSORIES: {
                ob = DatabaseManager.getInstance().getAccessoriesDao().queryBuilder().where(AccessoriesDao.Properties.Type.eq(type)).unique();
                break;
            }
            case DB_TYPE_IS_SKILL: {
                ob = DatabaseManager.getInstance().getSkillDao().queryBuilder().where(SkillDao.Properties.Type.eq(type)).unique();
                break;
            }
            case DB_TYPE_IS_MEDICINES: {
                ob = DatabaseManager.getInstance().getMedicinesDao().queryBuilder().where(MedicinesDao.Properties.Type.eq(type)).unique();
                break;
            }
        }
        ob.constructorBlacksmithModel();
        return ob;
    }

    public int getDBType(String type) {
        int dbType = 0;
        if (type.startsWith("A")) {
            dbType = DB_TYPE_IS_ATTACK;
        } else if (type.startsWith("B")) {
            dbType = DB_TYPE_IS_ARMOR;
        } else if (type.startsWith("E")) {
            dbType = DB_TYPE_IS_MATERIALS;
        } else if (type.startsWith("G")) {
            dbType = DB_TYPE_IS_ACCESSORIES;
        } else if (type.startsWith("J")) {
            dbType = DB_TYPE_IS_SKILL;
        } else if (type.startsWith("D")) {
            dbType = DB_TYPE_IS_MEDICINES;
        }
        return dbType;
    }
}
