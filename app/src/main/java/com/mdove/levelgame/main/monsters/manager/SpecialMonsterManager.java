package com.mdove.levelgame.main.monsters.manager;

import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;

/**
 * @author MDove on 2018/10/31
 */
public class SpecialMonsterManager {
    private static class SingletonHolder {
        static final SpecialMonsterManager INSTANCE = new SpecialMonsterManager();
    }

    public static SpecialMonsterManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private SpecialMonsterManager() {
    }

    public void setShowSpecialMonster() {
        zhanShanShow();
        zhanShanHouRenShow();
    }

    private void zhanShanShow() {
        // 战神出现
        Monsters zhanShen = DatabaseManager.getInstance().getMonstersDao().queryBuilder().where(MonstersDao.Properties.Id.eq(15)).unique();
        if (zhanShen != null) {
            if (HeroManager.getInstance().getHeroAttributes().days % 2 == 0) {
                zhanShen.isShow = 0;
            } else {
                zhanShen.isShow = 1;
            }
            DatabaseManager.getInstance().getMonstersDao().update(zhanShen);
        }
    }

    private void zhanShanHouRenShow() {
        Monsters zhanShenHouRen = DatabaseManager.getInstance().getMonstersDao().queryBuilder().where(MonstersDao.Properties.Id.eq(16)).unique();
        if (zhanShenHouRen != null) {
            if (HeroManager.getInstance().getHeroAttributes().days % 5 == 0) {
                zhanShenHouRen.isShow = 0;
            } else {
                zhanShenHouRen.isShow = 1;
            }
            DatabaseManager.getInstance().getMonstersDao().update(zhanShenHouRen);
        }
    }
}
