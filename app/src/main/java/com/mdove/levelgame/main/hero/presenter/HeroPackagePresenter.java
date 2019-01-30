package com.mdove.levelgame.main.hero.presenter;

import android.annotation.SuppressLint;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseNormalDialog;
import com.mdove.levelgame.base.RxTransformerHelper;
import com.mdove.levelgame.greendao.AccessoriesDao;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.MaterialDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Accessories;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.Material;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.model.BasePackageModelVM;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.main.hero.model.HeroPkEmptyModelVM;
import com.mdove.levelgame.main.hero.util.EquipUtils;
import com.mdove.levelgame.main.shop.manager.BlacksmithManager;
import com.mdove.levelgame.main.shop.model.StrengthenResp;
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils;
import com.mdove.levelgame.view.CustomPkDialog;
import com.mdove.levelgame.view.MyDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by MDove on 2018/10/30.
 */

public class HeroPackagePresenter implements HeroPackageContract.IHeroPackagePresenter {
    private static final int EQUIP_STATUS_TYPE_ATTACK = 1;
    private static final int EQUIP_STATUS_TYPE_ARMOR = 2;
    private static final int EQUIP_STATUS_TYPE_ACCESSORIES = 3;
    private HeroPackageContract.IHeroPackageView view;

    private List<BasePackageModelVM> packageModelVMS;

    private PackagesDao packagesDao;
    private WeaponsDao weaponsDao;
    private ArmorsDao armorsDao;
    private AccessoriesDao accessoriesDao;

    public HeroPackagePresenter() {
        weaponsDao = DatabaseManager.getInstance().getWeaponsDao();
        packagesDao = DatabaseManager.getInstance().getPackagesDao();
        armorsDao = DatabaseManager.getInstance().getArmorsDao();
        accessoriesDao = DatabaseManager.getInstance().getAccessoriesDao();
    }

    @Override
    public void subscribe(HeroPackageContract.IHeroPackageView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {
    }

    @Override
    public void initData() {
//        view.showLoadingDialog(view.getString(R.string.string_init_data_loading));
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            initPksData();
            e.onNext(1);
        }).compose(RxTransformerHelper.schedulerTransf())
                .subscribe(integer -> {
                    view.dismissLoadingDialog();
                    int addEmptyCount = 10 - packageModelVMS.size();
                    for (int i = 0; i < addEmptyCount; i++) {
                        packageModelVMS.add(new HeroPkEmptyModelVM());
                    }
                    view.showPackage(packageModelVMS);
                }, throwable -> {
                    view.dismissLoadingDialog();
                });
    }

    private void deleteById(long pkId) {
        int position = -1;
        if (packageModelVMS != null && packageModelVMS.size() > 0) {
            for (BasePackageModelVM vm : packageModelVMS) {
                if (vm instanceof HeroPackageModelVM && ((HeroPackageModelVM) vm).pkId.get() == pkId) {
                    position = packageModelVMS.indexOf(vm);
                }
            }
        }
        if (position != -1) {
            view.deleteByPosition(position);
        }
    }

    private void updateFromEquip(long holdOnId, long takeOffId) {
        int removePosition = -1;
        int equipPosition = -1;
        if (packageModelVMS != null && packageModelVMS.size() > 0) {
            for (BasePackageModelVM vm : packageModelVMS) {
                if (vm instanceof HeroPackageModelVM && ((HeroPackageModelVM) vm).pkId.get() == holdOnId) {
                    // Equip页面通过position。重新对页面进行刷新（重新加载对应的值）
                    removePosition = packageModelVMS.indexOf(vm);
                    int type = AllGoodsToDBIdUtils.getInstance().getDBType(((HeroPackageModelVM) vm).type.get());
                    switch (type) {
                        case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK: {
                            equipPosition = 1;
                            break;
                        }
                        case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR: {
                            equipPosition = 2;
                            break;
                        }
                        case AllGoodsToDBIdUtils.DB_TYPE_IS_ACCESSORIES: {
                            equipPosition = 3;
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
        }
        if (removePosition != -1 && equipPosition != -1) {
            view.deleteByPosition(removePosition);
            view.notifyEquipUpdateUI(equipPosition);
        }
        if (takeOffId != -1) {
            Packages pk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(takeOffId)).unique();
            if (pk != null) {
                addPkNotifyAdapter(pk);
            }
        }
    }

    // 通知装备Adapter，添加VM
    private void addPkNotifyAdapter(Packages pk) {
        int dbTpe = AllGoodsToDBIdUtils.getInstance().getDBType(pk.type);
        switch (dbTpe) {
            case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK: {
                Weapons attack = weaponsDao.queryBuilder().where(WeaponsDao.Properties.Type.eq(pk.type)).unique();
                if (attack != null && pk.isEquip == 1) {
                    packageModelVMS.add(new HeroPackageModelVM(pk.id, attack.tips, pk.strengthenLevel, attack.name, attack.attack, attack.armor, 0, attack.type));
                }
                break;
            }
            case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR: {
                Armors armors = armorsDao.queryBuilder().where(ArmorsDao.Properties.Type.eq(pk.type)).unique();
                if (armors != null && pk.isEquip == 1) {
                    packageModelVMS.add(new HeroPackageModelVM(pk.id, armors.tips, pk.strengthenLevel, armors.name, armors.attack, armors.armor, 0, armors.type));
                }
                break;
            }
            case AllGoodsToDBIdUtils.DB_TYPE_IS_MATERIALS: {
                Material material = DatabaseManager.getInstance().getMaterialDao().queryBuilder().where(MaterialDao.Properties.Type.eq(pk.type)).unique();
                if (material != null && pk.isEquip == 1) {
                    packageModelVMS.add(new HeroPackageModelVM(pk.id, material.tips, pk.strengthenLevel, material.name, 0, 0, 0, material.type));
                }
                break;
            }
            case AllGoodsToDBIdUtils.DB_TYPE_IS_ACCESSORIES: {
                Accessories accessories = DatabaseManager.getInstance().getAccessoriesDao().queryBuilder().where(AccessoriesDao.Properties.Type.eq(pk.type)).unique();
                if (accessories != null && pk.isEquip == 1) {
                    packageModelVMS.add(new HeroPackageModelVM(pk.id, accessories.tips, pk.strengthenLevel, accessories.name, accessories.attack, accessories.armor, accessories.life, accessories.type));
                }
                break;
            }
            default:
                break;
        }
        view.addByPosition(packageModelVMS.size() - 1);
    }

    private void initPksData() {
        List<Packages> packages = packagesDao.loadAll();
        packageModelVMS = new ArrayList<>();
        // Title的布局
        packageModelVMS.add(new HeroPackageModelVM((long) -3, "", 0, "", 0, 0, 0, ""));
        for (Packages pk : packages) {
            int dbTpe = AllGoodsToDBIdUtils.getInstance().getDBType(pk.type);
            switch (dbTpe) {
                case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK: {
                    Weapons attack = weaponsDao.queryBuilder().where(WeaponsDao.Properties.Type.eq(pk.type)).unique();
                    if (attack != null && pk.isEquip == 1) {
                        packageModelVMS.add(new HeroPackageModelVM(pk.id, attack.tips, pk.strengthenLevel, attack.name, attack.attack, attack.armor, 0, attack.type));
                    }
                    break;
                }
                case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR: {
                    Armors armors = armorsDao.queryBuilder().where(ArmorsDao.Properties.Type.eq(pk.type)).unique();
                    if (armors != null && pk.isEquip == 1) {
                        packageModelVMS.add(new HeroPackageModelVM(pk.id, armors.tips, pk.strengthenLevel, armors.name, armors.attack, armors.armor, 0, armors.type));
                    }
                    break;
                }
                case AllGoodsToDBIdUtils.DB_TYPE_IS_MATERIALS: {
                    Material material = DatabaseManager.getInstance().getMaterialDao().queryBuilder().where(MaterialDao.Properties.Type.eq(pk.type)).unique();
                    if (material != null && pk.isEquip == 1) {
                        HeroPackageModelVM model = new HeroPackageModelVM(pk.id, material.tips, pk.strengthenLevel, material.name, 0, 0, 0, material.type);
                        if (material.isCount == 0) {
                            model.setCount(pk.count);
                        }
                        packageModelVMS.add(1, model);
                    }
                    break;
                }
                case AllGoodsToDBIdUtils.DB_TYPE_IS_ACCESSORIES: {
                    Accessories accessories = DatabaseManager.getInstance().getAccessoriesDao().queryBuilder().where(AccessoriesDao.Properties.Type.eq(pk.type)).unique();
                    if (accessories != null && pk.isEquip == 1) {
                        packageModelVMS.add(new HeroPackageModelVM(pk.id, accessories.tips, pk.strengthenLevel, accessories.name, accessories.attack, accessories.armor, accessories.life, accessories.type));
                    }
                    break;
                }
                default:
                    break;
            }
        }
        view.showPackage(packageModelVMS);
    }

    // 已穿装备的封装
    public class HasEquipResp {
        public long pkId;
        public String equipType;
        public long strengthen;
    }

    // 从pk里找到装备对应的type
    private HasEquipResp getGoodsTypeFromPk(int status) {
        HasEquipResp hasEquipResp = new HasEquipResp();
        List<Packages> packages = packagesDao.queryBuilder().where(PackagesDao.Properties.IsEquip.eq(0)).list();
        if (packages == null || packages.size() <= 0) {
            return hasEquipResp;
        }
        switch (status) {
            case EQUIP_STATUS_TYPE_ATTACK: {
                for (Packages pk : packages) {
                    if (pk.isEquip == 0 && pk.type.startsWith("A")) {
                        hasEquipResp.equipType = pk.type;
                        hasEquipResp.pkId = pk.id;
                        hasEquipResp.strengthen = pk.strengthenLevel;
                        break;
                    }
                }
                break;
            }
            case EQUIP_STATUS_TYPE_ARMOR: {
                for (Packages pk : packages) {
                    if (pk.isEquip == 0 && pk.type.startsWith("B")) {
                        hasEquipResp.equipType = pk.type;
                        hasEquipResp.pkId = pk.id;
                        hasEquipResp.strengthen = pk.strengthenLevel;
                        break;
                    }
                }
                break;
            }
            case EQUIP_STATUS_TYPE_ACCESSORIES: {
                for (Packages pk : packages) {
                    if (pk.isEquip == 0 && pk.type.startsWith("G")) {
                        hasEquipResp.equipType = pk.type;
                        hasEquipResp.pkId = pk.id;
                        hasEquipResp.strengthen = pk.strengthenLevel;
                        break;
                    }
                }
                break;
            }
            default:
                break;
        }
        return hasEquipResp;
    }

    // 从pk里找到装备对应的id
    private long getGoodsIdFromPk(int status) {
        long id = -1;
        List<Packages> packages = packagesDao.queryBuilder().where(PackagesDao.Properties.IsEquip.eq(0)).list();
        if (packages == null || packages.size() <= 0) {
            return id;
        }
        switch (status) {
            case EQUIP_STATUS_TYPE_ATTACK: {
                for (Packages pk : packages) {
                    if (pk.isEquip == 0 && pk.type.startsWith("A")) {
                        id = pk.id;
                        break;
                    }
                }
                break;
            }
            case EQUIP_STATUS_TYPE_ARMOR: {
                for (Packages pk : packages) {
                    if (pk.isEquip == 0 && pk.type.startsWith("B")) {
                        id = pk.id;
                        break;
                    }
                }
                break;
            }
            case EQUIP_STATUS_TYPE_ACCESSORIES: {
                for (Packages pk : packages) {
                    if (pk.isEquip == 0 && pk.type.startsWith("G")) {
                        id = pk.id;
                        break;
                    }
                }
                break;
            }
            default:
                break;
        }
        return id;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onClickEquip(final HeroPackageModelVM vm) {
        final String type = vm.type.get();
        final Long id = vm.pkId.get();
        view.showLoadingDialog(view.getString(R.string.string_equip_loading));
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            // 获取是武器还是防具
            int type1 = AllGoodsToDBIdUtils.getInstance().getDBType(vm.type.get());
            e.onNext(type1);
        }).compose(RxTransformerHelper.<Integer>schedulerTransf())
                .map(new Function<Integer, GoodsEquipResp>() {
                    @Override
                    public GoodsEquipResp apply(Integer integer) throws Exception {
                        switch (integer) {
                            // 武器逻辑
                            case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK: {
                                GoodsEquipResp model = new GoodsEquipResp();

                                // 拿到已穿的装备（可能为null）
                                long hasHoldOnId = HeroPackagePresenter.this.getGoodsIdFromPk(EQUIP_STATUS_TYPE_ATTACK);
                                HasEquipResp hasHoldOnResp = HeroPackagePresenter.this.getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ATTACK);
                                Packages takeOffPk = null;
                                if (hasHoldOnId != -1) {
                                    model.takeOffPkId = hasHoldOnId;
                                    takeOffPk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(hasHoldOnId)
                                            , PackagesDao.Properties.IsEquip.eq(0)).unique();
                                }

                                // 拿到需要穿的装备（通过type从武器库中取）
                                Weapons holdOnWeapon = weaponsDao.queryBuilder().where(WeaponsDao.Properties.Type.eq(type)).unique();
                                if (EquipUtils.enableEquip(holdOnWeapon.needLevel, holdOnWeapon.needLiLiang, holdOnWeapon.needMinJie,
                                        holdOnWeapon.needZhiHui, holdOnWeapon.needQiangZhuang).getRespCode() == 1) {
                                    model.respStatus = 1;
                                    return model;
                                }
                                // 更新Pk穿上装备库(通过id)
                                Packages holdOnPk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(id)
                                        , PackagesDao.Properties.IsEquip.eq(1)).unique();
                                if (holdOnPk != null) {
                                    holdOnPk.isEquip = 0;
                                    model.holdOnPkId = holdOnPk.id;
                                    packagesDao.update(holdOnPk);
                                    model.holdOnType = holdOnPk.type;
                                    model.holdOnStrengthen = holdOnPk.strengthenLevel;
                                    model.holdOnAttack = holdOnWeapon;
                                    model.holdOnPk = holdOnPk;
                                } else {
                                    model.holdOnType = null;
                                    model.holdOnPk = null;
                                    model.holdOnAttack = null;
                                }

                                // 更新Pk脱掉装备库(唯一)
                                if (takeOffPk != null) {
                                    // 找准备脱掉的武器（唯一）
                                    Weapons hasHoldOnWeapon = weaponsDao.queryBuilder().where(WeaponsDao.Properties.Type.eq(hasHoldOnResp.equipType)).unique();

                                    if (hasHoldOnWeapon != null) {
                                        takeOffPk.isEquip = 1;
                                        packagesDao.update(takeOffPk);
                                        model.takeOffType = takeOffPk.type;
                                        model.takeOffStrengthen = takeOffPk.strengthenLevel;
                                        model.takeOffAttack = hasHoldOnWeapon;
                                        model.takeOffPk = takeOffPk;
                                    } else {
                                        model.takeOffType = null;
                                        model.takeOffPk = null;
                                        model.takeOffAttack = null;
                                    }
                                }
                                return model;
                            }
                            // 防具逻辑
                            case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR: {
                                GoodsEquipResp model = new GoodsEquipResp();

                                // 拿到已穿的装备（可能为null）
                                HasEquipResp hasHoldOnResp = HeroPackagePresenter.this.getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ARMOR);
                                long hasHoldOnId = HeroPackagePresenter.this.getGoodsIdFromPk(EQUIP_STATUS_TYPE_ARMOR);
                                Packages takeOffPk = null;
                                if (hasHoldOnId != -1) {
                                    model.takeOffPkId = hasHoldOnId;
                                    takeOffPk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(hasHoldOnId)
                                            , PackagesDao.Properties.IsEquip.eq(0)).unique();
                                }

                                // 拿到需要穿的装备（通过type从防具库中取）
                                Armors holdOnArmor = armorsDao.queryBuilder().where(ArmorsDao.Properties.Type.eq(type)).unique();
                                if (EquipUtils.enableEquip(holdOnArmor.needLevel, holdOnArmor.needLiLiang, holdOnArmor.needMinJie,
                                        holdOnArmor.needZhiHui, holdOnArmor.needQiangZhuang).getRespCode() == 1) {
                                    model.respStatus = 1;
                                    return model;
                                }
                                // 更新Pk穿上装备库(通过id)
                                Packages holdOnPk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(id)
                                        , PackagesDao.Properties.IsEquip.eq(1)).unique();
                                if (holdOnPk != null) {
                                    // 所穿装备
                                    holdOnPk.isEquip = 0;
                                    packagesDao.update(holdOnPk);
                                    model.holdOnPkId = holdOnPk.id;
                                    model.holdOnType = holdOnPk.type;
                                    model.holdOnPk = holdOnPk;
                                    model.holdOnStrengthen = holdOnPk.strengthenLevel;
                                    model.holdOnArmor = holdOnArmor;
                                } else {
                                    model.holdOnType = null;
                                    model.holdOnPk = null;
                                    model.holdOnArmor = null;
                                }

                                // 更新Pk脱掉装备库(唯一)
                                if (takeOffPk != null) {
                                    // 找准备脱掉的铠甲（唯一）
                                    Armors hasHoldOnArmor = armorsDao.queryBuilder().where(ArmorsDao.Properties.Type.eq(hasHoldOnResp.equipType)).unique();

                                    if (takeOffPk != null) {
                                        // 需要脱的装备
                                        takeOffPk.isEquip = 1;
                                        packagesDao.update(takeOffPk);
                                        model.takeOffType = takeOffPk.type;
                                        model.takeOffPk = takeOffPk;
                                        model.takeOffStrengthen = holdOnPk.strengthenLevel;
                                        model.takeOffArmor = hasHoldOnArmor;
                                    } else {
                                        model.takeOffType = null;
                                        model.takeOffPk = null;
                                        model.takeOffArmor = null;
                                    }
                                }
                                return model;
                            }
                            // 饰品逻辑
                            case AllGoodsToDBIdUtils.DB_TYPE_IS_ACCESSORIES: {
                                GoodsEquipResp model = new GoodsEquipResp();

                                // 拿到已穿的装备（可能为null）
                                HasEquipResp hasHoldOnType = HeroPackagePresenter.this.getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ACCESSORIES);
                                long hasHoldOnId = HeroPackagePresenter.this.getGoodsIdFromPk(EQUIP_STATUS_TYPE_ACCESSORIES);
                                Packages takeOffPk = null;
                                if (hasHoldOnId != -1) {
                                    model.takeOffPkId = hasHoldOnId;
                                    takeOffPk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(hasHoldOnId)
                                            , PackagesDao.Properties.IsEquip.eq(0)).unique();
                                }

                                // 拿到需要穿的装备（通过type从防具库中取）
                                Accessories holdOnAccessories = accessoriesDao.queryBuilder().where(AccessoriesDao.Properties.Type.eq(type)).unique();
                                if (EquipUtils.enableEquip(holdOnAccessories.needLevel, holdOnAccessories.needLiLiang, holdOnAccessories.needMinJie,
                                        holdOnAccessories.needZhiHui, holdOnAccessories.needQiangZhuang).getRespCode() == 1) {
                                    model.respStatus = 1;
                                    return model;
                                }
                                // 更新Pk穿上装备库(通过id)
                                Packages holdOnPk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(id)
                                        , PackagesDao.Properties.IsEquip.eq(1)).unique();
                                if (holdOnPk != null) {
                                    // 所穿装备
                                    holdOnPk.isEquip = 0;
                                    model.holdOnPkId = holdOnPk.id;
                                    packagesDao.update(holdOnPk);
                                    model.holdOnType = holdOnPk.type;
                                    model.holdOnStrengthen = holdOnPk.strengthenLevel;
                                    model.holdOnAccessories = holdOnAccessories;
                                    model.holdOnPk = holdOnPk;
                                } else {
                                    model.holdOnType = null;
                                    model.holdOnPk = null;
                                    model.holdOnAccessories = null;
                                }

                                // 更新Pk脱掉装备库(唯一)
                                if (takeOffPk != null) {
                                    // 找准备脱掉的饰品（唯一）
                                    Accessories hasHoldOnAccessories = accessoriesDao.queryBuilder().where(AccessoriesDao.Properties.Type.eq(hasHoldOnType.equipType)).unique();

                                    if (takeOffPk != null) {
                                        // 需要脱的装备
                                        takeOffPk.isEquip = 1;
                                        packagesDao.update(takeOffPk);
                                        model.takeOffType = takeOffPk.type;
                                        model.takeOffPk = takeOffPk;
                                        model.takeOffStrengthen = holdOnPk.strengthenLevel;
                                        model.takeOffAccessories = hasHoldOnAccessories;
                                    } else {
                                        model.takeOffType = null;
                                        model.takeOffPk = null;
                                        model.takeOffAccessories = null;
                                    }
                                }
                                return model;
                            }

                            default: {
                                return null;
                            }
                        }
                    }
                }).map(goodsEquipResp -> {
            // 更新英雄属性
            boolean attackSuc = false;
            boolean armorSuc = false;
            boolean accessoriesSuc = false;
            if (goodsEquipResp.respStatus == 1) {
                NotifyResp resp = new NotifyResp();
                resp.respCode = 1;
                return resp;
            }
            // 穿的装备是武器
            if (goodsEquipResp.holdOnAttack != null) {
                // 先减少脱掉装备的属性
                if (goodsEquipResp.takeOffAttack != null) {
                    HeroAttributesManager.getInstance().takeOff(goodsEquipResp.takeOffStrengthen, goodsEquipResp.takeOffAttack
                            , goodsEquipResp.takeOffPk.getRandomAttr());
                }
                // 增加穿装备属性
                HeroAttributesManager.getInstance().holdOn(goodsEquipResp.holdOnStrengthen, goodsEquipResp.holdOnAttack
                        , goodsEquipResp.holdOnPk.getRandomAttr());
                attackSuc = true;
            }
            // 穿的装备是护甲
            if (goodsEquipResp.holdOnArmor != null) {
                // 先减少脱掉装备的属性
                if (goodsEquipResp.takeOffArmor != null) {
                    HeroAttributesManager.getInstance().takeOff(goodsEquipResp.takeOffStrengthen, goodsEquipResp.takeOffArmor,
                            goodsEquipResp.takeOffPk.getRandomAttr());
                }
                // 增加穿装备属性
                HeroAttributesManager.getInstance().holdOn(goodsEquipResp.holdOnStrengthen, goodsEquipResp.holdOnArmor,
                        goodsEquipResp.holdOnPk.getRandomAttr());
                armorSuc = true;
            }
            // 穿的装备是饰品
            if (goodsEquipResp.holdOnAccessories != null) {
                // 先减少脱掉装备的属性
                if (goodsEquipResp.takeOffAccessories != null) {
                    HeroAttributesManager.getInstance().takeOff(goodsEquipResp.takeOffStrengthen, goodsEquipResp.takeOffAccessories,
                            goodsEquipResp.takeOffPk.getRandomAttr());
                }
                // 增加穿装备属性
                HeroAttributesManager.getInstance().holdOn(goodsEquipResp.holdOnStrengthen, goodsEquipResp.holdOnAccessories,
                        goodsEquipResp.holdOnPk.getRandomAttr());
                accessoriesSuc = true;
            }

            NotifyResp resp = null;
            if (armorSuc || attackSuc || accessoriesSuc) {
                resp = new NotifyResp();
                resp.holdOnId = goodsEquipResp.holdOnPkId;
                resp.takeOffOnId = goodsEquipResp.takeOffPkId;
            }
            return resp;
        }).subscribe(new Observer<NotifyResp>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(NotifyResp resp) {
                view.dismissLoadingDialog();
                if (resp != null && resp.respCode == 0) {
                    updateFromEquip(resp.holdOnId, resp.takeOffOnId);
                } else if (resp != null && resp.respCode == 1) {
                    MyDialog.showAlert("装备失败", "属性不足", true, view.getContext());
                } else {
                    MyDialog.showAlert("装备失败", "未知错误", true, view.getContext());
                }
            }

            @Override
            public void onError(Throwable e) {
                view.dismissLoadingDialog();
            }

            @Override
            public void onComplete() {
                view.dismissLoadingDialog();
            }
        });
    }

    @Override
    public void onClickStrengthen(HeroPackageModelVM vm) {
        StrengthenResp resp = BlacksmithManager.getInstance().strengthen(vm.pkId.get(), vm.type.get());
        switch (resp.status) {
            case BlacksmithManager.STRENGTHEN_STATUS_SUC: {
                MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_strengthen_title)
                        , view.getString(R.string.string_strengthen_suc), true);
                int position = -1;
                int strengthIdPosition = -1;
                for (BasePackageModelVM vm1 : packageModelVMS) {
                    if (vm1 instanceof HeroPackageModelVM && ((HeroPackageModelVM) vm1).pkId.get() == vm.pkId.get()) {
                        position = packageModelVMS.indexOf(vm1);
                        ((HeroPackageModelVM) vm1).reName(resp.level);
                        break;
                    }
                }
                for (BasePackageModelVM vm1 : packageModelVMS) {
                    if (vm1 instanceof HeroPackageModelVM && ((HeroPackageModelVM) vm1).pkId.get() == resp.strengthId) {
                        strengthIdPosition = packageModelVMS.indexOf(vm1);
                        break;
                    }
                }

                if (position != -1 && strengthIdPosition != -1) {
                    view.notifyByPosition(position);
                    view.deleteByPosition(strengthIdPosition);
                }
                break;
            }
            case BlacksmithManager.STRENGTHEN_STATUS_FAIL: {
                int strengthIdPosition = -1;
                for (BasePackageModelVM vm1 : packageModelVMS) {
                    if (vm1 instanceof HeroPackageModelVM && ((HeroPackageModelVM) vm1).pkId.get() == resp.strengthId) {
                        strengthIdPosition = packageModelVMS.indexOf(vm1);
                        break;
                    }
                }
                if (strengthIdPosition != -1) {
                    view.deleteByPosition(strengthIdPosition);
                }

                MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_strengthen_title)
                        , view.getString(R.string.string_strengthen_fail), true);
                break;
            }
            case BlacksmithManager.STRENGTHEN_STATUS_NO_MATERIAL: {
                MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_strengthen_title)
                        , view.getString(R.string.string_strengthen_no_material), true);
                break;
            }
            case BlacksmithManager.STRENGTHEN_STATUS_ERROR: {
                MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_strengthen_title)
                        , view.getString(R.string.string_strengthen_error), true);
                break;
            }
            default:
                return;
        }
    }

    @Override
    public void onLongClick(long pkId) {
        new CustomPkDialog(view.getContext(), pkId).show();
    }

    @Override
    public void onClickSell(final HeroPackageModelVM vm) {
        HeroAttributesManager.getInstance().sellGoods(vm.pkId.get(), true).subscribe(sellResp -> {
            if (sellResp.sellMoney == -1) {
                MyDialog.showMyDialog(view.getContext(), "注意", "装备特殊，是否出售？", "不卖", "卖！", false, new BaseNormalDialog.BaseDialogListener() {
                    @Override
                    public void onClick() {
                        HeroAttributesManager.getInstance().sellGoods(vm.pkId.get(), false).subscribe(new Consumer<HeroAttributesManager.SellResp>() {
                            @Override
                            public void accept(HeroAttributesManager.SellResp sellResp1) throws Exception {
                                if (sellResp1.sellMoney > 0) {
                                    MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_sells_suc_title)
                                            , String.format(view.getString(R.string.string_sells_suc), sellResp1.sellMoney), true);
                                    deleteById(sellResp.pkId);
                                }
                            }
                        });
                    }
                });

            } else {
                if (sellResp.sellMoney > 0) {
                    MyDialog.showMyDialog(view.getContext(), view.getString(R.string.string_sells_suc_title)
                            , String.format(view.getString(R.string.string_sells_suc), sellResp.sellMoney), true);
                    deleteById(sellResp.pkId);
                }
            }
        });
    }

    @Override
    public void notifyPackageAddUI(long pkId) {
        if (pkId != -1) {
            Packages pk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(pkId)).unique();
            if (pk != null) {
                addPkNotifyAdapter(pk);
            }
        }
    }

    // 临时封装了脱掉装备的Id和穿装备的Id
    private class GoodsEquipResp {
        // 0表示成功，1表示属性不足
        public int respStatus;
        public long holdOnPkId;
        public long takeOffPkId;
        public Packages holdOnPk;
        public Packages takeOffPk;
        public String holdOnType;
        public String takeOffType;
        // 用于查强化等级
        public long holdOnStrengthen;
        public long takeOffStrengthen;
        // 用于外部计算属性增减
        public Weapons holdOnAttack;
        public Weapons takeOffAttack;
        public Armors holdOnArmor;
        public Armors takeOffArmor;
        public Accessories holdOnAccessories;
        public Accessories takeOffAccessories;
    }

    private class NotifyResp {
        // 0 成功，1属性不足
        public int respCode = 0;
        public long holdOnId = -1;
        public long takeOffOnId = -1;
    }
}
