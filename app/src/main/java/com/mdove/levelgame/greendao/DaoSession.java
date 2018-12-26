package com.mdove.levelgame.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.mdove.levelgame.greendao.AllGoods;
import com.mdove.levelgame.greendao.entity.Accessories;
import com.mdove.levelgame.greendao.entity.Adventure;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.BigMonsters;
import com.mdove.levelgame.greendao.entity.DropGoods;
import com.mdove.levelgame.greendao.entity.FbMonsters;
import com.mdove.levelgame.greendao.entity.FbPlace;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.MainMenu;
import com.mdove.levelgame.greendao.entity.Material;
import com.mdove.levelgame.greendao.entity.Medicines;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.entity.MonstersPlace;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Skill;
import com.mdove.levelgame.greendao.entity.Task;
import com.mdove.levelgame.greendao.entity.Weapons;

import com.mdove.levelgame.greendao.AllGoodsDao;
import com.mdove.levelgame.greendao.AccessoriesDao;
import com.mdove.levelgame.greendao.AdventureDao;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.BigMonstersDao;
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

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig allGoodsDaoConfig;
    private final DaoConfig accessoriesDaoConfig;
    private final DaoConfig adventureDaoConfig;
    private final DaoConfig armorsDaoConfig;
    private final DaoConfig bigMonstersDaoConfig;
    private final DaoConfig dropGoodsDaoConfig;
    private final DaoConfig fbMonstersDaoConfig;
    private final DaoConfig fbPlaceDaoConfig;
    private final DaoConfig heroAttributesDaoConfig;
    private final DaoConfig mainMenuDaoConfig;
    private final DaoConfig materialDaoConfig;
    private final DaoConfig medicinesDaoConfig;
    private final DaoConfig monstersDaoConfig;
    private final DaoConfig monstersPlaceDaoConfig;
    private final DaoConfig packagesDaoConfig;
    private final DaoConfig skillDaoConfig;
    private final DaoConfig taskDaoConfig;
    private final DaoConfig weaponsDaoConfig;

    private final AllGoodsDao allGoodsDao;
    private final AccessoriesDao accessoriesDao;
    private final AdventureDao adventureDao;
    private final ArmorsDao armorsDao;
    private final BigMonstersDao bigMonstersDao;
    private final DropGoodsDao dropGoodsDao;
    private final FbMonstersDao fbMonstersDao;
    private final FbPlaceDao fbPlaceDao;
    private final HeroAttributesDao heroAttributesDao;
    private final MainMenuDao mainMenuDao;
    private final MaterialDao materialDao;
    private final MedicinesDao medicinesDao;
    private final MonstersDao monstersDao;
    private final MonstersPlaceDao monstersPlaceDao;
    private final PackagesDao packagesDao;
    private final SkillDao skillDao;
    private final TaskDao taskDao;
    private final WeaponsDao weaponsDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        allGoodsDaoConfig = daoConfigMap.get(AllGoodsDao.class).clone();
        allGoodsDaoConfig.initIdentityScope(type);

        accessoriesDaoConfig = daoConfigMap.get(AccessoriesDao.class).clone();
        accessoriesDaoConfig.initIdentityScope(type);

        adventureDaoConfig = daoConfigMap.get(AdventureDao.class).clone();
        adventureDaoConfig.initIdentityScope(type);

        armorsDaoConfig = daoConfigMap.get(ArmorsDao.class).clone();
        armorsDaoConfig.initIdentityScope(type);

        bigMonstersDaoConfig = daoConfigMap.get(BigMonstersDao.class).clone();
        bigMonstersDaoConfig.initIdentityScope(type);

        dropGoodsDaoConfig = daoConfigMap.get(DropGoodsDao.class).clone();
        dropGoodsDaoConfig.initIdentityScope(type);

        fbMonstersDaoConfig = daoConfigMap.get(FbMonstersDao.class).clone();
        fbMonstersDaoConfig.initIdentityScope(type);

        fbPlaceDaoConfig = daoConfigMap.get(FbPlaceDao.class).clone();
        fbPlaceDaoConfig.initIdentityScope(type);

        heroAttributesDaoConfig = daoConfigMap.get(HeroAttributesDao.class).clone();
        heroAttributesDaoConfig.initIdentityScope(type);

        mainMenuDaoConfig = daoConfigMap.get(MainMenuDao.class).clone();
        mainMenuDaoConfig.initIdentityScope(type);

        materialDaoConfig = daoConfigMap.get(MaterialDao.class).clone();
        materialDaoConfig.initIdentityScope(type);

        medicinesDaoConfig = daoConfigMap.get(MedicinesDao.class).clone();
        medicinesDaoConfig.initIdentityScope(type);

        monstersDaoConfig = daoConfigMap.get(MonstersDao.class).clone();
        monstersDaoConfig.initIdentityScope(type);

        monstersPlaceDaoConfig = daoConfigMap.get(MonstersPlaceDao.class).clone();
        monstersPlaceDaoConfig.initIdentityScope(type);

        packagesDaoConfig = daoConfigMap.get(PackagesDao.class).clone();
        packagesDaoConfig.initIdentityScope(type);

        skillDaoConfig = daoConfigMap.get(SkillDao.class).clone();
        skillDaoConfig.initIdentityScope(type);

        taskDaoConfig = daoConfigMap.get(TaskDao.class).clone();
        taskDaoConfig.initIdentityScope(type);

        weaponsDaoConfig = daoConfigMap.get(WeaponsDao.class).clone();
        weaponsDaoConfig.initIdentityScope(type);

        allGoodsDao = new AllGoodsDao(allGoodsDaoConfig, this);
        accessoriesDao = new AccessoriesDao(accessoriesDaoConfig, this);
        adventureDao = new AdventureDao(adventureDaoConfig, this);
        armorsDao = new ArmorsDao(armorsDaoConfig, this);
        bigMonstersDao = new BigMonstersDao(bigMonstersDaoConfig, this);
        dropGoodsDao = new DropGoodsDao(dropGoodsDaoConfig, this);
        fbMonstersDao = new FbMonstersDao(fbMonstersDaoConfig, this);
        fbPlaceDao = new FbPlaceDao(fbPlaceDaoConfig, this);
        heroAttributesDao = new HeroAttributesDao(heroAttributesDaoConfig, this);
        mainMenuDao = new MainMenuDao(mainMenuDaoConfig, this);
        materialDao = new MaterialDao(materialDaoConfig, this);
        medicinesDao = new MedicinesDao(medicinesDaoConfig, this);
        monstersDao = new MonstersDao(monstersDaoConfig, this);
        monstersPlaceDao = new MonstersPlaceDao(monstersPlaceDaoConfig, this);
        packagesDao = new PackagesDao(packagesDaoConfig, this);
        skillDao = new SkillDao(skillDaoConfig, this);
        taskDao = new TaskDao(taskDaoConfig, this);
        weaponsDao = new WeaponsDao(weaponsDaoConfig, this);

        registerDao(AllGoods.class, allGoodsDao);
        registerDao(Accessories.class, accessoriesDao);
        registerDao(Adventure.class, adventureDao);
        registerDao(Armors.class, armorsDao);
        registerDao(BigMonsters.class, bigMonstersDao);
        registerDao(DropGoods.class, dropGoodsDao);
        registerDao(FbMonsters.class, fbMonstersDao);
        registerDao(FbPlace.class, fbPlaceDao);
        registerDao(HeroAttributes.class, heroAttributesDao);
        registerDao(MainMenu.class, mainMenuDao);
        registerDao(Material.class, materialDao);
        registerDao(Medicines.class, medicinesDao);
        registerDao(Monsters.class, monstersDao);
        registerDao(MonstersPlace.class, monstersPlaceDao);
        registerDao(Packages.class, packagesDao);
        registerDao(Skill.class, skillDao);
        registerDao(Task.class, taskDao);
        registerDao(Weapons.class, weaponsDao);
    }
    
    public void clear() {
        allGoodsDaoConfig.clearIdentityScope();
        accessoriesDaoConfig.clearIdentityScope();
        adventureDaoConfig.clearIdentityScope();
        armorsDaoConfig.clearIdentityScope();
        bigMonstersDaoConfig.clearIdentityScope();
        dropGoodsDaoConfig.clearIdentityScope();
        fbMonstersDaoConfig.clearIdentityScope();
        fbPlaceDaoConfig.clearIdentityScope();
        heroAttributesDaoConfig.clearIdentityScope();
        mainMenuDaoConfig.clearIdentityScope();
        materialDaoConfig.clearIdentityScope();
        medicinesDaoConfig.clearIdentityScope();
        monstersDaoConfig.clearIdentityScope();
        monstersPlaceDaoConfig.clearIdentityScope();
        packagesDaoConfig.clearIdentityScope();
        skillDaoConfig.clearIdentityScope();
        taskDaoConfig.clearIdentityScope();
        weaponsDaoConfig.clearIdentityScope();
    }

    public AllGoodsDao getAllGoodsDao() {
        return allGoodsDao;
    }

    public AccessoriesDao getAccessoriesDao() {
        return accessoriesDao;
    }

    public AdventureDao getAdventureDao() {
        return adventureDao;
    }

    public ArmorsDao getArmorsDao() {
        return armorsDao;
    }

    public BigMonstersDao getBigMonstersDao() {
        return bigMonstersDao;
    }

    public DropGoodsDao getDropGoodsDao() {
        return dropGoodsDao;
    }

    public FbMonstersDao getFbMonstersDao() {
        return fbMonstersDao;
    }

    public FbPlaceDao getFbPlaceDao() {
        return fbPlaceDao;
    }

    public HeroAttributesDao getHeroAttributesDao() {
        return heroAttributesDao;
    }

    public MainMenuDao getMainMenuDao() {
        return mainMenuDao;
    }

    public MaterialDao getMaterialDao() {
        return materialDao;
    }

    public MedicinesDao getMedicinesDao() {
        return medicinesDao;
    }

    public MonstersDao getMonstersDao() {
        return monstersDao;
    }

    public MonstersPlaceDao getMonstersPlaceDao() {
        return monstersPlaceDao;
    }

    public PackagesDao getPackagesDao() {
        return packagesDao;
    }

    public SkillDao getSkillDao() {
        return skillDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public WeaponsDao getWeaponsDao() {
        return weaponsDao;
    }

}
