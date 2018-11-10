package com.mdove.levelgame.main.monsters.utils;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.greendao.DropGoodsDao;
import com.mdove.levelgame.greendao.entity.DropGoods;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.monsters.model.DropGoodsResp;
import com.mdove.levelgame.model.DropGoodsModel;
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils;
import com.mdove.levelgame.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

/**
 * @author MDove on 2018/10/26
 */
public class DropGoodsManager {
    private static class SingletonHolder {
        static final DropGoodsManager INSTANCE = new DropGoodsManager();
    }

    public static DropGoodsManager getInstance() {
        return DropGoodsManager.SingletonHolder.INSTANCE;
    }

    private DropGoodsDao dropGoodsDao;

    private DropGoodsManager() {
        dropGoodsDao = DatabaseManager.getInstance().getDropGoodsDao();
    }

    public boolean isHitProbability(float probability) {
        float probabilityInt = probability * 100;
        int random = new Random(System.currentTimeMillis()).nextInt(100);
        if (random <= probabilityInt) {
            return true;
        } else {
            return false;
        }
    }
}
