package com.mdove.levelgame.greendao.utils;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.App;
import com.mdove.levelgame.config.ConstAssetsFileName;
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

    public static List<Armors> getInitArmors() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_ARMORS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Armors>>() {
            }.getType());
        }
        return null;
    }

    public static List<ShopArmorModel> getShopArmors() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_ARMORS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<ShopArmorModel>>() {
            }.getType());
        }
        return null;
    }

    public static List<Weapons> getInitWeapons() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_WEAPONS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Weapons>>() {
            }.getType());
        }
        return null;
    }

    public static List<ShopAttackModel> getShopWeapons() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_WEAPONS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<ShopAttackModel>>() {
            }.getType());
        }
        return null;
    }

    public static List<MonstersModel> getInitMonsters() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MONSTERS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<MonstersModel>>() {
            }.getType());
        }
        return null;
    }

    public static List<MonstersPlaceModel> getInitMonstersPlace() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MONSTERS_PLACE);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<MonstersPlaceModel>>() {
            }.getType());
        }
        return null;
    }

    public static List<Medicines> getInitMedicines() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MEDICINES_SHOP);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Medicines>>() {
            }.getType());
        }
        return null;
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
