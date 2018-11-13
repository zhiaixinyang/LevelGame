package com.mdove.levelgame.main.monsters.manager;

import android.provider.ContactsContract;

import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.MonstersPlaceDao;
import com.mdove.levelgame.greendao.entity.Adventure;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.entity.MonstersPlace;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.view.MyDialog;

import java.util.List;

/**
 * Created by MDove on 2018/10/29.
 */

public class AdventureManager {
    private static class SingletonHolder {
        static final AdventureManager INSTANCE = new AdventureManager();
    }

    public static AdventureManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private AdventureManager() {
    }

    public boolean setAdventure() {
        boolean isShow = false;
        List<Adventure> adventures = DatabaseManager.getInstance().getAdventureDao().loadAll();
        if (adventures != null && adventures.size() > 0) {
            for (Adventure adventure : adventures) {
                int days = HeroManager.getInstance().getHeroAttributes().days;
                MonstersPlace monstersPlace = DatabaseManager.getInstance().getMonstersPlaceDao().queryBuilder()
                        .where(MonstersPlaceDao.Properties.Id.eq(adventure.monsterPlaceId)).unique();
                Monsters monsters = DatabaseManager.getInstance().getMonstersDao().queryBuilder()
                        .where(MonstersDao.Properties.Type.eq(adventure.type)).unique();
                // 奇遇出现
                if (monstersPlace != null && monsters != null && days == adventure.days && adventure.isCycle == 1 ||
                        monstersPlace != null && monsters != null && days % adventure.days == 0 && adventure.isCycle == 0) {
                    // 第一个对应，奇遇日期只有一天；第二个对应循环
                    monsters.isShow = 0;
                    monstersPlace.isShow = 0;
                    DatabaseManager.getInstance().getMonstersDao().update(monsters);
                    DatabaseManager.getInstance().getMonstersPlaceDao().update(monstersPlace);
                    isShow = true;
                }
                // 奇遇消失
                if (days > adventure.days && monstersPlace != null && monstersPlace.isShow == 0 && monsters != null && monsters.isShow == 0 && adventure.isCycle == 1 ||
                        monstersPlace != null && monsters != null && days % adventure.days != 0 && adventure.isCycle == 0) {
                    monsters.isShow = 1;
                    if (monstersPlace.isAdventure == 0) {
                        monstersPlace.isShow = 1;
                    }
                    DatabaseManager.getInstance().getMonstersDao().update(monsters);
                    DatabaseManager.getInstance().getMonstersPlaceDao().update(monstersPlace);
                    isShow = false;
                }
            }
        }
        return isShow;
    }
}
