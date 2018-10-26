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

    /**
     * @param id DropGoods的id
     * @return
     */
    public DropGoodsResp getDropGoods(long id) {
        DropGoodsResp resp = new DropGoodsResp();
        DropGoods dropGood = dropGoodsDao.queryBuilder().where(DropGoodsDao.Properties.Id.eq(id)).unique();
        if (dropGood != null) {
            List<DropGoodsModel> goodsModels = JsonUtil.decode(dropGood.data, new TypeToken<List<DropGoodsModel>>() {
            }.getType());
            for (DropGoodsModel model : goodsModels) {

            }
        }
    }

    private void compute(DropGoodsResp resp, String type, float probability) {
        int typeResp = AllGoodsToDBIdUtils.getInstance().getDBType(type);
        switch (typeResp){
            case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK:{

                break;
            }
            case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR:{
                break;
            }
            default:break;
        }
    }
}
