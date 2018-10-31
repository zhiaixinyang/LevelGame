package com.mdove.levelgame.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.mdove.levelgame.greendao.AllGoods;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.BigMonsters;
import com.mdove.levelgame.greendao.entity.DropGoods;
import com.mdove.levelgame.greendao.entity.HeroAttributes;
import com.mdove.levelgame.greendao.entity.Material;
import com.mdove.levelgame.greendao.entity.Medicines;
import com.mdove.levelgame.greendao.entity.Monsters;
import com.mdove.levelgame.greendao.entity.MonstersPlace;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.entity.Adventure;
import com.mdove.levelgame.greendao.entity.MainMenu;
import com.mdove.levelgame.greendao.entity.Accessories;

import com.mdove.levelgame.greendao.AllGoodsDao;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.BigMonstersDao;
import com.mdove.levelgame.greendao.DropGoodsDao;
import com.mdove.levelgame.greendao.HeroAttributesDao;
import com.mdove.levelgame.greendao.MaterialDao;
import com.mdove.levelgame.greendao.MedicinesDao;
import com.mdove.levelgame.greendao.MonstersDao;
import com.mdove.levelgame.greendao.MonstersPlaceDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.AdventureDao;
import com.mdove.levelgame.greendao.MainMenuDao;
import com.mdove.levelgame.greendao.AccessoriesDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig allGoodsDaoConfig;
    private final DaoConfig armorsDaoConfig;
    private final DaoConfig bigMonstersDaoConfig;
    private final DaoConfig dropGoodsDaoConfig;
    private final DaoConfig heroAttributesDaoConfig;
    private final DaoConfig materialDaoConfig;
    private final DaoConfig medicinesDaoConfig;
    private final DaoConfig monstersDaoConfig;
    private final DaoConfig monstersPlaceDaoConfig;
    private final DaoConfig packagesDaoConfig;
    private final DaoConfig weaponsDaoConfig;
    private final DaoConfig adventureDaoConfig;
    private final DaoConfig mainMenuDaoConfig;
    private final DaoConfig accessoriesDaoConfig;

    private final AllGoodsDao allGoodsDao;
    private final ArmorsDao armorsDao;
    private final BigMonstersDao bigMonstersDao;
    private final DropGoodsDao dropGoodsDao;
    private final HeroAttributesDao heroAttributesDao;
    private final MaterialDao materialDao;
    private final MedicinesDao medicinesDao;
    private final MonstersDao monstersDao;
    private final MonstersPlaceDao monstersPlaceDao;
    private final PackagesDao packagesDao;
    private final WeaponsDao weaponsDao;
    private final AdventureDao adventureDao;
    private final MainMenuDao mainMenuDao;
    private final AccessoriesDao accessoriesDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        allGoodsDaoConfig = daoConfigMap.get(AllGoodsDao.class).clone();
        allGoodsDaoConfig.initIdentityScope(type);

        armorsDaoConfig = daoConfigMap.get(ArmorsDao.class).clone();
        armorsDaoConfig.initIdentityScope(type);

        bigMonstersDaoConfig = daoConfigMap.get(BigMonstersDao.class).clone();
        bigMonstersDaoConfig.initIdentityScope(type);

        dropGoodsDaoConfig = daoConfigMap.get(DropGoodsDao.class).clone();
        dropGoodsDaoConfig.initIdentityScope(type);

        heroAttributesDaoConfig = daoConfigMap.get(HeroAttributesDao.class).clone();
        heroAttributesDaoConfig.initIdentityScope(type);

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

        weaponsDaoConfig = daoConfigMap.get(WeaponsDao.class).clone();
        weaponsDaoConfig.initIdentityScope(type);

        adventureDaoConfig = daoConfigMap.get(AdventureDao.class).clone();
        adventureDaoConfig.initIdentityScope(type);

        mainMenuDaoConfig = daoConfigMap.get(MainMenuDao.class).clone();
        mainMenuDaoConfig.initIdentityScope(type);

        accessoriesDaoConfig = daoConfigMap.get(AccessoriesDao.class).clone();
        accessoriesDaoConfig.initIdentityScope(type);

        allGoodsDao = new AllGoodsDao(allGoodsDaoConfig, this);
        armorsDao = new ArmorsDao(armorsDaoConfig, this);
        bigMonstersDao = new BigMonstersDao(bigMonstersDaoConfig, this);
        dropGoodsDao = new DropGoodsDao(dropGoodsDaoConfig, this);
        heroAttributesDao = new HeroAttributesDao(heroAttributesDaoConfig, this);
        materialDao = new MaterialDao(materialDaoConfig, this);
        medicinesDao = new MedicinesDao(medicinesDaoConfig, this);
        monstersDao = new MonstersDao(monstersDaoConfig, this);
        monstersPlaceDao = new MonstersPlaceDao(monstersPlaceDaoConfig, this);
        packagesDao = new PackagesDao(packagesDaoConfig, this);
        weaponsDao = new WeaponsDao(weaponsDaoConfig, this);
        adventureDao = new AdventureDao(adventureDaoConfig, this);
        mainMenuDao = new MainMenuDao(mainMenuDaoConfig, this);
        accessoriesDao = new AccessoriesDao(accessoriesDaoConfig, this);

        registerDao(AllGoods.class, allGoodsDao);
        registerDao(Armors.class, armorsDao);
        registerDao(BigMonsters.class, bigMonstersDao);
        registerDao(DropGoods.class, dropGoodsDao);
        registerDao(HeroAttributes.class, heroAttributesDao);
        registerDao(Material.class, materialDao);
        registerDao(Medicines.class, medicinesDao);
        registerDao(Monsters.class, monstersDao);
        registerDao(MonstersPlace.class, monstersPlaceDao);
        registerDao(Packages.class, packagesDao);
        registerDao(Weapons.class, weaponsDao);
        registerDao(Adventure.class, adventureDao);
        registerDao(MainMenu.class, mainMenuDao);
        registerDao(Accessories.class, accessoriesDao);
    }
    
    public void clear() {
        allGoodsDaoConfig.clearIdentityScope();
        armorsDaoConfig.clearIdentityScope();
        bigMonstersDaoConfig.clearIdentityScope();
        dropGoodsDaoConfig.clearIdentityScope();
        heroAttributesDaoConfig.clearIdentityScope();
        materialDaoConfig.clearIdentityScope();
        medicinesDaoConfig.clearIdentityScope();
        monstersDaoConfig.clearIdentityScope();
        monstersPlaceDaoConfig.clearIdentityScope();
        packagesDaoConfig.clearIdentityScope();
        weaponsDaoConfig.clearIdentityScope();
        adventureDaoConfig.clearIdentityScope();
        mainMenuDaoConfig.clearIdentityScope();
        accessoriesDaoConfig.clearIdentityScope();
    }

    public AllGoodsDao getAllGoodsDao() {
        return allGoodsDao;
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

    public HeroAttributesDao getHeroAttributesDao() {
        return heroAttributesDao;
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

    public WeaponsDao getWeaponsDao() {
        return weaponsDao;
    }

    public AdventureDao getAdventureDao() {
        return adventureDao;
    }

    public MainMenuDao getMainMenuDao() {
        return mainMenuDao;
    }

    public AccessoriesDao getAccessoriesDao() {
        return accessoriesDao;
    }

}
