package com.mdove.levelgame.utils;

import com.mdove.levelgame.App;
import com.mdove.levelgame.greendao.AllGoodsDao;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;

/**
 * @author MDove on 2018/10/23
 */
public class AllGoodsToDBIdUtils {
    public static final int DB_TYPE_IS_ATTACK = 1;
    public static final int DB_TYPE_IS_ARMOR = 2;
    public static final int DB_TYPE_IS_MATERIALS = 3;

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

    public int getDBType(String type) {
        int dbType = 0;
        if (type.startsWith("A")) {
            dbType = DB_TYPE_IS_ATTACK;
        } else if (type.startsWith("B")) {
            dbType = DB_TYPE_IS_ARMOR;
        }else if (type.startsWith("E")) {
            dbType = DB_TYPE_IS_MATERIALS;
        }

        return dbType;
    }
}
