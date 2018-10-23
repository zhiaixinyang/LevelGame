package com.mdove.levelgame.main.hero.manager;

import com.mdove.levelgame.App;
import com.mdove.levelgame.greendao.HeroAttributesDao;
import com.mdove.levelgame.greendao.entity.HeroAttributes;

/**
 * @author zhaojing on 2018/10/23
 */
public class HeroManager {
    private HeroAttributesDao heroAttributesDao;
    private HeroAttributes heroAttributes;

    private static class SingletonHolder {
        static final HeroManager INSTANCE = new HeroManager();
    }

    public static HeroManager getInstance() {
        return HeroManager.SingletonHolder.INSTANCE;
    }

    private HeroManager() {
        heroAttributesDao = App.getDaoSession().getHeroAttributesDao();
        heroAttributes = heroAttributesDao.queryBuilder().unique();
    }

    public HeroAttributes getHeroAttributes() {
        return heroAttributes;
    }

    public void save() {
        heroAttributesDao.update(heroAttributes);
    }
}
