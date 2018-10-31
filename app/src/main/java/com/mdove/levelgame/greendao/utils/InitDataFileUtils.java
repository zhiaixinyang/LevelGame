package com.mdove.levelgame.greendao.utils;

import android.databinding.DataBindingUtil;

import com.google.gson.reflect.TypeToken;
import com.mdove.levelgame.App;
import com.mdove.levelgame.config.AppConfig;
import com.mdove.levelgame.config.ConstAssetsFileName;
import com.mdove.levelgame.greendao.AccessoriesDao;
import com.mdove.levelgame.greendao.AdventureDao;
import com.mdove.levelgame.greendao.AllGoods;
import com.mdove.levelgame.greendao.AllGoodsDao;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.BigMonstersDao;
import com.mdove.levelgame.greendao.DropGoodsDao;
import com.mdove.levelgame.greendao.MainMenuDao;
import com.mdove.levelgame.greendao.MaterialDao;
import com.mdove.levelgame.greendao.MedicinesDao;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.MonstersPlaceDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Accessories;
import com.mdove.levelgame.greendao.entity.Adventure;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.BigMonsters;
import com.mdove.levelgame.greendao.entity.DropGoods;
import com.mdove.levelgame.greendao.entity.MainMenu;
import com.mdove.levelgame.greendao.entity.Material;
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
    private static List<Monsters> monsters;
    private static List<Medicines> medicines;
    private static List<MonstersPlaceModel> monstersPlaceModels;
    private static List<MonstersPlace> monstersPlaces;
    private static List<Adventure> adventures;

    public static void initData() {
        AllGoodsDao allGoodsDao = DatabaseManager.getInstance().getAllGoodsDao();
        allGoodsDao.deleteAll();
        WeaponsDao weaponsDao = DatabaseManager.getInstance().getWeaponsDao();
        weaponsDao.deleteAll();
        ArmorsDao armorsDao = DatabaseManager.getInstance().getArmorsDao();
        armorsDao.deleteAll();
        MonstersDao monstersDao = DatabaseManager.getInstance().getMonstersDao();
        monstersDao.deleteAll();
        MonstersPlaceDao monstersPlaceDao = DatabaseManager.getInstance().getMonstersPlaceDao();
        monstersPlaceDao.deleteAll();
        MedicinesDao medicinesDao = DatabaseManager.getInstance().getMedicinesDao();
        medicinesDao.deleteAll();
        BigMonstersDao bigMonstersDao = DatabaseManager.getInstance().getBigMonstersDao();
        AdventureDao adventureDao = DatabaseManager.getInstance().getAdventureDao();

        MainMenuDao mainMenuDao = DatabaseManager.getInstance().getMainMenuDao();
        mainMenuDao.deleteAll();

        AccessoriesDao accessoriesDao = DatabaseManager.getInstance().getAccessoriesDao();
        accessoriesDao.deleteAll();

        List<Accessories> accessories = getInitAccessories();
        for (Accessories accessorie : accessories) {
            accessoriesDao.insert(accessorie);
        }

        if (!AppConfig.isFirstLogin()) {
            bigMonstersDao.deleteAll();
            adventureDao.deleteAll();
        }
        DropGoodsDao dropGoodsDao = DatabaseManager.getInstance().getDropGoodsDao();
        dropGoodsDao.deleteAll();
        MaterialDao materialDao = DatabaseManager.getInstance().getMaterialDao();
        materialDao.deleteAll();

        List<DropGoods> dropGoods = getInitDropGoods();
        for (DropGoods dropGood : dropGoods) {
            dropGoodsDao.insert(dropGood);
        }

        List<MainMenu> mainMenus = getInitmainMenu();
        for (MainMenu mainMenu : mainMenus) {
            mainMenuDao.insert(mainMenu);
        }

        shopArmorModels = getShopArmors();
        for (ShopArmorModel model : shopArmorModels) {
            AllGoods goods = new AllGoods();
            goods.type = model.type;
            goods.goodsId = model.id;
            allGoodsDao.insert(goods);
        }

        weapons = getInitWeapons();
        for (Weapons model : weapons) {
            AllGoods goods = new AllGoods();
            goods.type = model.type;
            goods.goodsId = model.id;
            allGoodsDao.insert(goods);
            weaponsDao.insert(model);
        }

        armors = getInitArmors();
        for (Armors model : armors) {
            AllGoods goods = new AllGoods();
            goods.type = model.type;
            goods.goodsId = model.id;
            allGoodsDao.insert(goods);
            armorsDao.insert(model);
        }

        shopAttackModels = getShopWeapons();
        for (ShopAttackModel model : shopAttackModels) {
            AllGoods goods = new AllGoods();
            goods.type = model.type;
            goods.goodsId = model.id;
            allGoodsDao.insert(goods);
        }

        monsters = getInitMonstersBase();
        for (Monsters monsters : monsters) {
            monstersDao.insert(monsters);
        }

        medicines = getInitMedicines();
        for (Medicines medicine : medicines) {
            medicinesDao.insert(medicine);
        }

        if (monstersPlaceModels == null) {
            monstersPlaceModels = getInitMonstersPlace();
        }

        monstersPlaces = getInitMonstersPlaceBase();
        for (MonstersPlace monstersPlace : monstersPlaces) {
            monstersPlaceDao.insert(monstersPlace);
        }

        for (Material material : getInitMaterials()) {
            materialDao.insert(material);
        }
        if (!AppConfig.isFirstLogin()) {
            List<BigMonsters> bigMonsters = getInitBigMonsters();
            for (BigMonsters b : bigMonsters) {
                bigMonstersDao.insert(b);
            }
            List<Adventure> adventures = getInitAdventure();
            for (Adventure adventure : adventures) {
                adventureDao.insert(adventure);
            }
        }
    }

    public static List<Armors> getInitArmors() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_ARMORS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Armors>>() {
            }.getType());
        }
        return armors;
    }

    public static List<ShopArmorModel> getShopArmors() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_ARMORS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<ShopArmorModel>>() {
            }.getType());
        }
        return shopArmorModels;
    }

    public static List<Weapons> getInitWeapons() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_WEAPONS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Weapons>>() {
            }.getType());
        }
        return weapons;
    }

    public static List<ShopAttackModel> getShopWeapons() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_WEAPONS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<ShopAttackModel>>() {
            }.getType());
        }
        return shopAttackModels;
    }

    public static List<MonstersModel> getInitMonsters() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MONSTERS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<MonstersModel>>() {
            }.getType());
        }
        return monstersModels;
    }

    public static List<Monsters> getInitMonstersBase() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MONSTERS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Monsters>>() {
            }.getType());
        }
        return monsters;
    }

    public static List<MonstersPlaceModel> getInitMonstersPlace() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MONSTERS_PLACE);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<MonstersPlaceModel>>() {
            }.getType());
        }
        return monstersPlaceModels;
    }

    public static List<BigMonsters> getInitBigMonsters() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_BIG_MONSTERS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<BigMonsters>>() {
            }.getType());
        }
        return null;
    }

    public static List<DropGoods> getInitDropGoods() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_DROP_GOODS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<DropGoods>>() {
            }.getType());
        }
        return null;
    }

    public static List<Accessories> getInitAccessories() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_ACCESSORIES);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Accessories>>() {
            }.getType());
        }
        return null;
    }

    public static List<MainMenu> getInitmainMenu() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MAIN_MENU);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<MainMenu>>() {
            }.getType());
        }
        return null;
    }

    public static List<Adventure> getInitAdventure() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_ADVENTURE);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Adventure>>() {
            }.getType());
        }
        return null;
    }

    public static List<MonstersPlace> getInitMonstersPlaceBase() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MONSTERS_PLACE);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<MonstersPlace>>() {
            }.getType());
        }
        return monstersPlaces;
    }

    public static List<Medicines> getInitMedicines() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MEDICINES_SHOP);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Medicines>>() {
            }.getType());
        }
        return medicines;
    }

    public static List<Material> getInitMaterials() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MATERIALS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Material>>() {
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
