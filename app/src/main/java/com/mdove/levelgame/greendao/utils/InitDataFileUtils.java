package com.mdove.levelgame.greendao.utils;

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
import com.mdove.levelgame.greendao.CityDao;
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
import com.mdove.levelgame.greendao.entity.Accessories;
import com.mdove.levelgame.greendao.entity.Adventure;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.BigMonsters;
import com.mdove.levelgame.greendao.entity.City;
import com.mdove.levelgame.greendao.entity.DropGoods;
import com.mdove.levelgame.greendao.entity.FbMonsters;
import com.mdove.levelgame.greendao.entity.FbPlace;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.MainMenu;
import com.mdove.levelgame.greendao.entity.Material;
import com.mdove.levelgame.greendao.entity.Medicines;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.entity.MonstersPlace;
import com.mdove.levelgame.greendao.entity.Skill;
import com.mdove.levelgame.greendao.entity.Task;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.monsters.model.MonstersPlaceModel;
import com.mdove.levelgame.utils.FileUtil;
import com.mdove.levelgame.utils.JsonUtil;

import java.util.List;

/**
 * Created by MDove on 2018/10/20.
 */

public class InitDataFileUtils {
    private static List<MonstersPlaceModel> monstersPlaceModels;

    public static void initData() {
        HeroAttributesDao dao = DatabaseManager.getInstance().getHeroAttributesDao();
        dao.deleteAll();
        HeroAttributes heroAttributes = new HeroAttributes();
        heroAttributes.armor = 10;
        heroAttributes.armorIncrease = 3;
        heroAttributes.attack = 20;
        heroAttributes.attackIncrease = 5;
        heroAttributes.baseExp = 100;
        heroAttributes.experience = 0;
        heroAttributes.expMultiple = 2;
        heroAttributes.money = 0;
        heroAttributes.level = 1;
        heroAttributes.curLife = 100;
        heroAttributes.maxLife = 100;
        heroAttributes.lifeIncrease = 20;
        heroAttributes.bodyPower = 100;
        heroAttributes.attackSpeed = 1500;
        heroAttributes.skillCount = 1;
        heroAttributes.days = 1;
        heroAttributes.liLiang = 1;
        heroAttributes.liLiangExp = 0;
        heroAttributes.liLiangBaseExp = 100;
        heroAttributes.liLiangExpMultiple = 2;
        heroAttributes.minJie = 1;
        heroAttributes.minJieExp = 0;
        heroAttributes.minJieBaseExp = 100;
        heroAttributes.minJieExpMultiple = 2;
        heroAttributes.zhiHui = 1;
        heroAttributes.zhiHuiExp = 0;
        heroAttributes.zhiHuiBaseExp = 100;
        heroAttributes.zhiHuiExpMultiple = 2;
        heroAttributes.qiangZhuang = 1;
        heroAttributes.qiangZhuangExp = 0;
        heroAttributes.qiangZhuangBaseExp = 100;
        heroAttributes.qiangZhuangExpMultiple = 2;

        dao.insert(heroAttributes);

        HeroManager.getInstance().resetAttr();
        HeroAttributesManager.getInstance().resetPower();

        AllGoodsDao allGoodsDao = DatabaseManager.getInstance().getAllGoodsDao();
        allGoodsDao.deleteAll();
        WeaponsDao weaponsDao = DatabaseManager.getInstance().getWeaponsDao();
        weaponsDao.deleteAll();
        ArmorsDao armorsDao = DatabaseManager.getInstance().getArmorsDao();
        armorsDao.deleteAll();
        MonstersDao monstersDao = DatabaseManager.getInstance().getMonstersDao();
        monstersDao.deleteAll();
        MonstersPlaceDao monstersPlaceDao = DatabaseManager.getInstance().getMonstersPlaceDao();
        MedicinesDao medicinesDao = DatabaseManager.getInstance().getMedicinesDao();
        medicinesDao.deleteAll();
        BigMonstersDao bigMonstersDao = DatabaseManager.getInstance().getBigMonstersDao();
        AdventureDao adventureDao = DatabaseManager.getInstance().getAdventureDao();
        adventureDao.deleteAll();
        FbPlaceDao fbPlaceDao = DatabaseManager.getInstance().getFbPlaceDao();
        fbPlaceDao.deleteAll();
        FbMonstersDao fbMonstersDao = DatabaseManager.getInstance().getFbMonstersDao();
        fbMonstersDao.deleteAll();
        MainMenuDao mainMenuDao = DatabaseManager.getInstance().getMainMenuDao();
        mainMenuDao.deleteAll();

        AccessoriesDao accessoriesDao = DatabaseManager.getInstance().getAccessoriesDao();
        accessoriesDao.deleteAll();
        TaskDao taskDao = DatabaseManager.getInstance().getTaskDao();
        taskDao.deleteAll();
        SkillDao skillDao = DatabaseManager.getInstance().getSkillDao();
        skillDao.deleteAll();
        CityDao cityDao = DatabaseManager.getInstance().getCityDao();
        cityDao.deleteAll();

        if (!AppConfig.isHasLogin()) {
            bigMonstersDao.deleteAll();
            monstersPlaceDao.deleteAll();
        }
        DropGoodsDao dropGoodsDao = DatabaseManager.getInstance().getDropGoodsDao();
        dropGoodsDao.deleteAll();
        MaterialDao materialDao = DatabaseManager.getInstance().getMaterialDao();
        materialDao.deleteAll();
        PackagesDao packagesDao = DatabaseManager.getInstance().getPackagesDao();
        packagesDao.deleteAll();

        List<DropGoods> dropGoods = getInitDropGoods();
        for (DropGoods dropGood : dropGoods) {
            dropGoodsDao.insert(dropGood);
        }

        List<Task> tasks = getInitTask();
        for (Task task : tasks) {
            taskDao.insert(task);
        }
        List<MainMenu> mainMenus = getInitmainMenu();
        for (MainMenu mainMenu : mainMenus) {
            mainMenuDao.insert(mainMenu);
        }

        for (City city : getInitCity()) {
            cityDao.insert(city);
        }

        for (Weapons model : getInitWeapons()) {
            AllGoods goods = new AllGoods();
            goods.type = model.type;
            goods.goodsId = model.id;
            allGoodsDao.insert(goods);
            weaponsDao.insert(model);
        }

        for (Accessories accessorie : getInitAccessories()) {
            AllGoods goods = new AllGoods();
            goods.type = accessorie.type;
            goods.goodsId = accessorie.id;
            allGoodsDao.insert(goods);
            accessoriesDao.insert(accessorie);
        }

        for (Material material : getInitMaterials()) {
            AllGoods goods = new AllGoods();
            goods.type = material.type;
            goods.goodsId = material.id;
            allGoodsDao.insert(goods);
            materialDao.insert(material);
        }

        for (Skill skill : getInitSkill()) {
            AllGoods goods = new AllGoods();
            goods.type = skill.type;
            goods.goodsId = skill.id;
            allGoodsDao.insert(goods);
            skillDao.insert(skill);
        }

        for (Armors model : getInitArmors()) {
            AllGoods goods = new AllGoods();
            goods.type = model.type;
            goods.goodsId = model.id;
            allGoodsDao.insert(goods);
            armorsDao.insert(model);
        }

        for (Monsters monsters : getInitMonsters()) {
            monstersDao.insert(monsters);
        }

        for (Medicines medicine : getInitMedicines()) {
            medicinesDao.insert(medicine);
        }

        for (FbMonsters fbMonsters : getInitFbMonsters()) {
            fbMonstersDao.insert(fbMonsters);
        }

        for (FbPlace fbPlace : getInitFbPlace()) {
            fbPlaceDao.insert(fbPlace);
        }

        if (monstersPlaceModels == null) {
            monstersPlaceModels = getInitMonstersPlace();
        }

        for (Adventure adventure : getInitAdventure()) {
            adventureDao.insert(adventure);
        }
        if (!AppConfig.isHasLogin()) {
            for (BigMonsters b : getInitBigMonsters()) {
                bigMonstersDao.insert(b);
            }
            for (MonstersPlace monstersPlace : getInitMonstersPlaceBase()) {
                monstersPlaceDao.insert(monstersPlace);
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

    public static List<Skill> getInitSkill() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_SKILL);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Skill>>() {
            }.getType());
        }
        return null;
    }

    public static List<Monsters> getInitMonsters() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_MONSTERS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Monsters>>() {
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

    public static List<FbMonsters> getInitFbMonsters() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_FB_MONSTER);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<FbMonsters>>() {
            }.getType());
        }
        return null;
    }

    public static List<FbPlace> getInitFbPlace() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_FB_PLACE);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<FbPlace>>() {
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

    public static List<City> getInitCity() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_CITY);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<City>>() {
            }.getType());
        }
        return null;
    }

    public static List<Task> getInitTask() {
        String json = FileUtil.loadJsonFromAssets(App.getAppContext(),
                ConstAssetsFileName.ASSETS_TASKS);
        if (json != null) {
            return JsonUtil.decode(json, new TypeToken<List<Task>>() {
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
