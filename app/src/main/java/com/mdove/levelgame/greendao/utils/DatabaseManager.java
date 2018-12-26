package com.mdove.levelgame.greendao.utils;

import android.content.Context;

import com.mdove.levelgame.greendao.AccessoriesDao;
import com.mdove.levelgame.greendao.AdventureDao;
import com.mdove.levelgame.greendao.AllGoodsDao;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.BigMonstersDao;
import com.mdove.levelgame.greendao.DaoMaster;
import com.mdove.levelgame.greendao.DaoSession;
import com.mdove.levelgame.greendao.DropGoodsDao;
import com.mdove.levelgame.greendao.FbMonstersDao;
import com.mdove.levelgame.greendao.FbPlaceDao;
import com.mdove.levelgame.greendao.HeroAttributesDao;
import com.mdove.levelgame.greendao.MainMenuDao;
import com.mdove.levelgame.greendao.MaterialDao;
import com.mdove.levelgame.greendao.MedicinesDao;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.MonstersPlaceDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.SkillDao;
import com.mdove.levelgame.greendao.TaskDao;
import com.mdove.levelgame.greendao.WeaponsDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by MDove on 2018/2/10.
 */

public class DatabaseManager {
    private static final String TAG = DatabaseManager.class.getSimpleName();
    private static final String DB_NAME = "PasswordGuard.db";//数据库名称
    private volatile static DatabaseManager mDatabaseManager;//多线程访问
    private static DaoMaster.OpenHelper mHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private HeroAttributesDao heroAttributesDao;
    private ArmorsDao armorsDao;
    private DropGoodsDao dropGoodsDao;
    private MedicinesDao medicinesDao;
    private MonstersDao monstersDao;
    private MonstersPlaceDao monstersPlaceDao;
    private PackagesDao packagesDao;
    private WeaponsDao weaponsDao;
    private AllGoodsDao allGoodsDao;
    private BigMonstersDao bigMonstersDao;
    private MaterialDao materialDao;
    private AdventureDao adventureDao;
    private MainMenuDao mainMenuDao;
    private AccessoriesDao accessoriesDao;
    private TaskDao taskDao;
    private SkillDao skillDao;
    private FbPlaceDao mFbPlaceDao;
    private FbMonstersDao mFbMonstersDao;

    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    /**
     * 使用单例模式获得操作数据库的对象
     *
     * @return
     */
    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    private DatabaseManager() {
    }

    public DatabaseManager init(Context context, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        initDao(context, daoClasses);
        return this;
    }

    private void initDao(Context context, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        final DBUpdateHelper helper = new DBUpdateHelper(context, DB_NAME, daoClasses);
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();

        heroAttributesDao = mDaoSession.getHeroAttributesDao();
        armorsDao = mDaoSession.getArmorsDao();
        dropGoodsDao = mDaoSession.getDropGoodsDao();
        medicinesDao = mDaoSession.getMedicinesDao();
        monstersDao = mDaoSession.getMonstersDao();
        monstersPlaceDao = mDaoSession.getMonstersPlaceDao();
        packagesDao = mDaoSession.getPackagesDao();
        weaponsDao = mDaoSession.getWeaponsDao();
        allGoodsDao = mDaoSession.getAllGoodsDao();
        bigMonstersDao = mDaoSession.getBigMonstersDao();
        materialDao = mDaoSession.getMaterialDao();
        adventureDao = mDaoSession.getAdventureDao();
        mainMenuDao = mDaoSession.getMainMenuDao();
        accessoriesDao = mDaoSession.getAccessoriesDao();
        taskDao = mDaoSession.getTaskDao();
        skillDao = mDaoSession.getSkillDao();
        mFbPlaceDao = mDaoSession.getFbPlaceDao();
        mFbMonstersDao = mDaoSession.getFbMonstersDao();
    }

    public MaterialDao getMaterialDao() {
        return materialDao;
    }

    /**
     * 判断数据库是否存在，如果不存在则创建
     *
     * @return
     */
    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public SkillDao getSkillDao() {
        return skillDao;
    }

    public BigMonstersDao getBigMonstersDao() {
        return bigMonstersDao;
    }

    public HeroAttributesDao getHeroAttributesDao() {
        return heroAttributesDao;
    }

    public ArmorsDao getArmorsDao() {
        return armorsDao;
    }

    public DropGoodsDao getDropGoodsDao() {
        return dropGoodsDao;
    }

    public MedicinesDao getMedicinesDao() {
        return medicinesDao;
    }

    public MonstersDao getMonstersDao() {
        return monstersDao;
    }

    public MainMenuDao getMainMenuDao() {
        return mainMenuDao;
    }

    public MonstersPlaceDao getMonstersPlaceDao() {
        return monstersPlaceDao;
    }

    public PackagesDao getPackagesDao() {
        return packagesDao;
    }

    public WeaponsDao getWeaponsDao() {
        return weaponsDao;
    }

    public AllGoodsDao getAllGoodsDao() {
        return allGoodsDao;
    }

    public AdventureDao getAdventureDao() {
        return adventureDao;
    }

    public FbPlaceDao getFbPlaceDao() {
        return mFbPlaceDao;
    }

    public FbMonstersDao getFbMonstersDao() {
        return mFbMonstersDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public AccessoriesDao getAccessoriesDao() {
        return accessoriesDao;
    }

    /**
     * 设置debug模式开启或关闭，默认关闭
     *
     * @param flag
     */
    public void setDebug(boolean flag) {
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }

    /**
     * 关闭数据库
     */
    public void closeDataBase() {
        closeHelper();
        closeDaoSession();
    }

    public void closeDaoSession() {
        if (null != mDaoSession) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    public void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }
}
