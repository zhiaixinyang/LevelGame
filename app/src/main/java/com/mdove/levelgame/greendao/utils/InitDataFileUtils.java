package com.mdove.levelgame.greendao.utils;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.App;
import com.mdove.levelgame.config.ConstAssetsFileName;
import com.mdove.levelgame.greendao.AllGoods;
import com.mdove.levelgame.greendao.AllGoodsDao;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.Medicines;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.entity.MonstersPlace;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.main.monsters.model.MonstersModel;
import com.mdove.levelgame.main.monsters.model.MonstersPlaceModel;
import com.mdove.levelgame.main.shop.model.ShopArmorModel;
import com.mdove.levelgame.main.shop.model.ShopAttackModel;
import com.mdove.levelgame.utils.FileUtil;
import com.mdove.levelgame.utils.JsonUtil;

import java.util.List;

/**
 * Created by MDove on 2018/10/20.
 */

public class InitDataFileUtils {
    private static List<ShopArmorModel> shopArmorModels;
    private static List<Weapons> weapons;
    private static List<Armors> armors;
    private static List<ShopAttackModel> shopAttackModels;
    private static List<MonstersModel> monstersModels;
    private static List<Medicines> medicines;
    private static List<MonstersPlaceModel> monstersPlaceModels;

    public static void initData() {
        AllGoodsDao allGoodsDao = App.getDaoSession().getAllGoodsDao();
        long count = allGoodsDao.queryBuilder().count();
        if (shopArmorModels == null) {
            shopArmorModels = getShopArmors();
            if (count > 0) {
                for (ShopArmorModel model : shopArmorModels) {
                    AllGoods goods = new AllGoods();
                    goods.type = model.type;
                    goods.goodsId = model.id;
                    allGoodsDao.insert(goods);
                }
            }
        }
        if (weapons == null) {
            weapons = getInitWeapons();
            if (count > 0) {
                for (Weapons model : weapons) {
                    AllGoods goods = new AllGoods();
                    goods.type = model.type;
                    goods.goodsId = model.id;
                    allGoodsDao.insert(goods);
                }
            }
        }
        if (armors == null) {
            armors = getInitArmors();
            if (count > 0) {
                for (Armors model : armors) {
                    AllGoods goods = new AllGoods();
                    goods.type = model.type;
                    goods.goodsId = model.id;
                    allGoodsDao.insert(goods);
                }
            }
        }
        if (shopAttackModels == null) {
            shopAttackModels = getShopWeapons();
            if (count > 0) {
                for (ShopAttackModel model : shopAttackModels) {
                    AllGoods goods = new AllGoods();
                    goods.type = model.type;
                    goods.goodsId = model.id;
                    allGoodsDao.insert(goods);
                }
            }
        }
        if (monstersModels == null) {
            monstersModels = getInitMonsters();
        }
        if (medicines == null) {
            medicines = getInitMedicines();
        }
        if (monstersPlaceModels == null) {
            monstersPlaceModels = getInitMonstersPlace();
        }
    }

    public static List<Armors> getInitArmors() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_ARMORS);
        if (json != null && armors == null) {
            return JsonUtil.decode(json, new TypeToken<List<Armors>>() {
            }.getType());
        }
        return armors;
    }

    public static List<ShopArmorModel> getShopArmors() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_ARMORS);
        if (json != null && shopArmorModels == null) {
            return JsonUtil.decode(json, new TypeToken<List<ShopArmorModel>>() {
            }.getType());
        }
        return shopArmorModels;
    }

    public static List<Weapons> getInitWeapons() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_WEAPONS);
        if (json != null && weapons == null) {
            return JsonUtil.decode(json, new TypeToken<List<Weapons>>() {
            }.getType());
        }
        return weapons;
    }

    public static List<ShopAttackModel> getShopWeapons() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_WEAPONS);
        if (json != null && shopAttackModels == null) {
            return JsonUtil.decode(json, new TypeToken<List<ShopAttackModel>>() {
            }.getType());
        }
        return shopAttackModels;
    }

    public static List<MonstersModel> getInitMonsters() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MONSTERS);
        if (json != null && monstersModels == null) {
            return JsonUtil.decode(json, new TypeToken<List<MonstersModel>>() {
            }.getType());
        }
        return monstersModels;
    }

    public static List<MonstersPlaceModel> getInitMonstersPlace() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MONSTERS_PLACE);
        if (json != null && monstersPlaceModels == null) {
            return JsonUtil.decode(json, new TypeToken<List<MonstersPlaceModel>>() {
            }.getType());
        }
        return monstersPlaceModels;
    }

    public static List<Medicines> getInitMedicines() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MEDICINES_SHOP);
        if (json != null && medicines == null) {
            return JsonUtil.decode(json, new TypeToken<List<Medicines>>() {
            }.getType());
        }
        return medicines;
    }

//    public static List<Armors> getInitArmors() {
//        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
//                ConstAssetsFileName.ASSETS_ARMORS);
//        if (json != null) {
//            return JsonUtil.decode(json, new TypeToken<Armors>() {
//            }.getType());
//        }
//        return null;
//    }
}
