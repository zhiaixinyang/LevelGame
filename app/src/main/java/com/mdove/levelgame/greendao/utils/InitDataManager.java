package com.mdove.levelgame.greendao.utils;

import com.mdove.levelgame.App;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.DropGoodsDao;
import com.mdove.levelgame.greendao.HeroAttributesDao;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.WeaponsDao;

/**
 * Created by MDove on 2018/10/20.
 */

public class InitDataManager {
    private ArmorsDao armorsDao;
    private DropGoodsDao dropGoodsDao;
    private HeroAttributesDao heroAttributesDao;
    private MonstersDao monstersDao;
    private WeaponsDao weaponsDao;

    public InitDataManager() {
        armorsDao = App.getDaoSession().getArmorsDao();
        // 等于null，说明没初始化数据，此时开始初始化数据。
        if (armorsDao.queryBuilder().unique() == null) {
            dropGoodsDao = App.getDaoSession().getDropGoodsDao();
            heroAttributesDao = App.getDaoSession().getHeroAttributesDao();
            monstersDao = App.getDaoSession().getMonstersDao();
            weaponsDao = App.getDaoSession().getWeaponsDao();


        }
    }
}
