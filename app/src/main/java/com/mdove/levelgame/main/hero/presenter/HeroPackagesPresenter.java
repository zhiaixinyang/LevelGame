package com.mdove.levelgame.main.hero.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.RxTransformerHelper;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.MaterialDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.Material;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.model.HasEquipModelVM;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils;
import com.mdove.levelgame.view.MyDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by MDove on 2018/10/23.
 */

public class HeroPackagesPresenter implements HeroPackagesContract.IHeroPackagesPresenter {
    private static final int EQUIP_STATUS_TYPE_ATTACK = 1;
    private static final int EQUIP_STATUS_TYPE_ARMOR = 2;
    private HeroPackagesContract.IHeroPackagesView view;
    private PackagesDao packagesDao;
    private WeaponsDao weaponsDao;
    private ArmorsDao armorsDao;

    public HeroPackagesPresenter() {
        weaponsDao = DatabaseManager.getInstance().getWeaponsDao();
        packagesDao = DatabaseManager.getInstance().getPackagesDao();
        armorsDao = DatabaseManager.getInstance().getArmorsDao();
    }

    @Override
    public void subscribe(HeroPackagesContract.IHeroPackagesView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
        initEquipData();

        initPksData();
    }

    private void initPksData() {
        List<Packages> packages = packagesDao.loadAll();
        List<HeroPackageModelVM> packageModelVMS = new ArrayList<>();
        for (Packages pk : packages) {
            int dbTpe = AllGoodsToDBIdUtils.getInstance().getDBType(pk.type);
            switch (dbTpe) {
                case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK: {
                    Weapons attack = weaponsDao.queryBuilder().where(WeaponsDao.Properties.Type.eq(pk.type)).unique();
                    if (attack != null && pk.isEquip == 1) {
                        packageModelVMS.add(new HeroPackageModelVM(pk.id, attack.name, attack.attack, attack.armor, attack.type));
                    }
                    break;
                }
                case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR: {
                    Armors armors = armorsDao.queryBuilder().where(ArmorsDao.Properties.Type.eq(pk.type)).unique();
                    if (armors != null && pk.isEquip == 1) {
                        packageModelVMS.add(new HeroPackageModelVM(pk.id, armors.name, armors.attack, armors.armor, armors.type));
                    }
                    break;
                }
                case AllGoodsToDBIdUtils.DB_TYPE_IS_MATERIALS: {
                    Material material = DatabaseManager.getInstance().getMaterialDao().queryBuilder().where(MaterialDao.Properties.Type.eq(pk.type)).unique();
                    if (material != null && pk.isEquip == 1) {
                        packageModelVMS.add(new HeroPackageModelVM(pk.id, material.name, 0, 0, material.type));
                    }
                    break;
                }
                default:
                    break;
            }
        }
        view.showPackage(packageModelVMS);
    }

    private void initEquipData() {
        List<HasEquipModelVM> equipData = new ArrayList<>();
        // 查询装备的武器
        String attackType = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ATTACK);
        if (!TextUtils.isEmpty(attackType)) {
            Weapons weapons = weaponsDao.queryBuilder().where(WeaponsDao.Properties.Type.eq(attackType)).unique();
            if (weapons != null) {
                equipData.add(new HasEquipModelVM(weapons.id, weapons.name, weapons.attack + "", weapons.armor + "", weapons.type));
            }
        }

        // 添加武器占位
        if (equipData.size() == 0) {
            equipData.add(new HasEquipModelVM((long) -1, view.getString(R.string.string_no_hold_on_attack), view.getString(R.string.string_no_hold_on_attack), view.getString(R.string.string_no_hold_on_attack), "-1"));
        }
        // 查询装备的防具
        String armorType = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ARMOR);
        if (!TextUtils.isEmpty(armorType)) {
            Armors armor = armorsDao.queryBuilder().where(ArmorsDao.Properties.Type.eq(armorType)).unique();
            if (armor != null) {
                equipData.add(new HasEquipModelVM(armor.id, armor.name, armor.attack + "", armor.armor + "", armor.type));
            }
        }
        // 添加防具占位
        if (equipData.size() == 1) {
            equipData.add(new HasEquipModelVM((long) -1, view.getString(R.string.string_no_hold_on_armor), view.getString(R.string.string_no_hold_on_armor), view.getString(R.string.string_no_hold_on_armor), "-1"));
        }
        view.showEquipData(equipData);
    }

    // 从pk里找到装备对应的type
    private String getGoodsTypeFromPk(int status) {
        String type = null;
        List<Packages> packages = packagesDao.queryBuilder().where(PackagesDao.Properties.IsEquip.eq(0)).list();
        if (packages == null || packages.size() <= 0) {
            return type;
        }
        switch (status) {
            case EQUIP_STATUS_TYPE_ATTACK: {
                for (Packages pk : packages) {
                    if (pk.isEquip == 0 && pk.type.startsWith("A")) {
                        type = pk.type;
                        break;
                    }
                }
                break;
            }
            case EQUIP_STATUS_TYPE_ARMOR: {
                for (Packages pk : packages) {
                    if (pk.isEquip == 0 && pk.type.startsWith("B")) {
                        type = pk.type;
                        break;
                    }
                }
                break;
            }
            default:
                break;
        }
        return type;
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
            default:
                break;
        }
        return id;
    }

    @Override
    public void onClickTakeOff(final HasEquipModelVM vm) {
        final String type = vm.type.get();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 获取是武器还是防具
                int type = AllGoodsToDBIdUtils.getInstance().getDBType(vm.type.get());
                e.onNext(type);
            }
        }).compose(RxTransformerHelper.<Integer>schedulerTransf())
                .map(new Function<Integer, GoodsEquipResp>() {
                    @Override
                    public GoodsEquipResp apply(Integer integer) throws Exception {
                        switch (integer) {
                            // 武器逻辑
                            case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK: {
                                GoodsEquipResp model = new GoodsEquipResp();
                                // 从库中找到所脱武器
                                Weapons takeOffAttack = weaponsDao.queryBuilder().where(WeaponsDao.Properties.Type.eq(type)).unique();
                                // 从所有持有的装备(Packages)中，重置isEquip的状态
                                Packages pk = packagesDao.queryBuilder().where(PackagesDao.Properties.Type.eq(vm.type.get())
                                        , PackagesDao.Properties.IsEquip.eq(Integer.valueOf(0))).unique();
                                if (pk != null) {
                                    // 更新脱装备的状态
                                    pk.isEquip = 1;
                                    packagesDao.update(pk);
                                    model.takeOffType = pk.type;
                                    model.takeOffAttack = takeOffAttack;
                                }
                                return model;
                            }
                            // 防具逻辑
                            case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR: {
                                GoodsEquipResp model = new GoodsEquipResp();
                                // 从库中找到脱的铠甲
                                Armors takeOffArmor = armorsDao.queryBuilder().where(ArmorsDao.Properties.Type.eq(type)).unique();
                                // 从所有持有的装备(Packages)中，重置isEquip的状态
                                Packages pk = packagesDao.queryBuilder().where(PackagesDao.Properties.Type.eq(vm.type.get())
                                        , PackagesDao.Properties.IsEquip.eq(Integer.valueOf(0))).unique();
                                if (pk != null) {
                                    // 更新脱装备的状态
                                    pk.isEquip = 1;
                                    packagesDao.update(pk);
                                    model.takeOffType = pk.type;
                                    model.takeOffArmor = takeOffArmor;
                                }
                                return model;
                            }
                            default:
                                break;
                        }
                        return null;
                    }
                }).map(new Function<GoodsEquipResp, String>() {
            @Override
            public String apply(GoodsEquipResp goodsEquipResp) throws Exception {
                // 更新英雄属性
                boolean attackSuc = false;
                boolean armorSuc = false;
                // 减少脱掉武器的属性
                if (goodsEquipResp.takeOffAttack != null) {
                    HeroAttributesManager.getInstance().takeOffAttack(goodsEquipResp.takeOffAttack);
                    attackSuc = true;
                }
                // 减少脱掉护甲的属性
                if (goodsEquipResp.takeOffArmor != null) {
                    HeroAttributesManager.getInstance().takeOffArmor(goodsEquipResp.takeOffArmor);
                    armorSuc = true;
                }
                // 返回值是pk页面更新的type
                String type = null;
                if (armorSuc || attackSuc) {
                    type = goodsEquipResp.takeOffType;
                }
                return type;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String type) {
                view.dismissLoadingDialog();
                if (!TextUtils.isEmpty(type)) {
                    initData();
                } else {
                    MyDialog.showAlert("脱下装备失败", "未知错误", true, view.getContext());
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

    @SuppressLint("CheckResult")
    @Override
    public void onClickEquip(final HeroPackageModelVM vm) {
        final String type = vm.type.get();
        final Long id = vm.pkId.get();
        view.showLoadingDialog(view.getString(R.string.string_equip_loading));
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 获取是武器还是防具
                int type = AllGoodsToDBIdUtils.getInstance().getDBType(vm.type.get());
                e.onNext(type);
            }
        }).compose(RxTransformerHelper.<Integer>schedulerTransf())
                .map(new Function<Integer, GoodsEquipResp>() {
                    @Override
                    public GoodsEquipResp apply(Integer integer) throws Exception {
                        switch (integer) {
                            // 武器逻辑
                            case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK: {
                                GoodsEquipResp model = new GoodsEquipResp();

                                // 拿到已穿的装备（可能为null）
                                long hasHoldOnId = getGoodsIdFromPk(EQUIP_STATUS_TYPE_ATTACK);
                                String hasHoldOnType = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ATTACK);
                                Packages takeOffPk = null;
                                if (hasHoldOnId != -1) {
                                    takeOffPk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(hasHoldOnId)
                                            , PackagesDao.Properties.IsEquip.eq(0)).unique();
                                }

                                // 拿到需要穿的装备（通过type从武器库中取）
                                Weapons holdOnWeapon = weaponsDao.queryBuilder().where(WeaponsDao.Properties.Type.eq(type)).unique();
                                // 更新Pk穿上装备库(通过id)
                                Packages holdOnPk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(id)
                                        , PackagesDao.Properties.IsEquip.eq(1)).unique();
                                if (holdOnPk != null) {
                                    holdOnPk.isEquip = 0;
                                    packagesDao.update(holdOnPk);
                                    model.holdOnType = holdOnPk.type;
                                    model.holdOnAttack = holdOnWeapon;
                                } else {
                                    model.holdOnType = null;
                                    model.holdOnAttack = null;
                                }

                                // 更新Pk脱掉装备库(唯一)
                                if (takeOffPk != null) {
                                    // 找准备脱掉的武器（唯一）
                                    Weapons hasHoldOnWeapon = weaponsDao.queryBuilder().where(WeaponsDao.Properties.Type.eq(hasHoldOnType)).unique();

                                    if (hasHoldOnWeapon != null) {
                                        takeOffPk.isEquip = 1;
                                        packagesDao.update(takeOffPk);
                                        model.takeOffType = takeOffPk.type;
                                        model.takeOffAttack = hasHoldOnWeapon;
                                    } else {
                                        model.takeOffType = null;
                                        model.takeOffAttack = null;
                                    }
                                }
                                return model;
                            }
                            // 防具逻辑
                            case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR: {
                                GoodsEquipResp model = new GoodsEquipResp();

                                // 拿到已穿的装备（可能为null）
                                String hasHoldOnType = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ARMOR);
                                long hasHoldOnId = getGoodsIdFromPk(EQUIP_STATUS_TYPE_ARMOR);
                                Packages takeOffPk = null;
                                if (hasHoldOnId != -1) {
                                    takeOffPk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(hasHoldOnId)
                                            , PackagesDao.Properties.IsEquip.eq(0)).unique();
                                }

                                // 拿到需要穿的装备（通过type从防具库中取）
                                Armors holdOnArmor = armorsDao.queryBuilder().where(ArmorsDao.Properties.Type.eq(type)).unique();
                                // 更新Pk穿上装备库(通过id)
                                Packages holdOnPk = packagesDao.queryBuilder().where(PackagesDao.Properties.Id.eq(id)
                                        , PackagesDao.Properties.IsEquip.eq(1)).unique();
                                if (holdOnPk != null) {
                                    // 所穿装备
                                    holdOnPk.isEquip = 0;
                                    packagesDao.update(holdOnPk);
                                    model.holdOnType = holdOnPk.type;
                                    model.holdOnArmor = holdOnArmor;
                                } else {
                                    model.holdOnType = null;
                                    model.holdOnArmor = null;
                                }

                                // 更新Pk脱掉装备库(唯一)
                                if (takeOffPk != null) {
                                    // 找准备脱掉的铠甲（唯一）
                                    Armors hasHoldOnArmor = armorsDao.queryBuilder().where(ArmorsDao.Properties.Type.eq(hasHoldOnType)).unique();

                                    if (takeOffPk != null) {
                                        // 需要脱的装备
                                        takeOffPk.isEquip = 1;
                                        packagesDao.update(takeOffPk);
                                        model.takeOffType = takeOffPk.type;
                                        model.takeOffArmor = hasHoldOnArmor;
                                    } else {
                                        model.takeOffType = null;
                                        model.takeOffArmor = null;
                                    }
                                }
                                return model;
                            }

                            default:
                                break;
                        }
                        return null;
                    }
                }).map(new Function<GoodsEquipResp, String>() {
            @Override
            public String apply(GoodsEquipResp goodsEquipResp) throws Exception {
                // 更新英雄属性
                boolean attackSuc = false;
                boolean armorSuc = false;
                // 穿的装备是武器
                if (goodsEquipResp.holdOnAttack != null) {
                    // 先减少脱掉装备的属性
                    if (goodsEquipResp.takeOffAttack != null) {
                        HeroAttributesManager.getInstance().takeOffAttack(goodsEquipResp.takeOffAttack);
                    }
                    // 增加穿装备属性
                    HeroAttributesManager.getInstance().holdOnAttack(goodsEquipResp.holdOnAttack);
                    attackSuc = true;
                }
                // 穿的装备是护甲
                if (goodsEquipResp.holdOnArmor != null) {
                    // 先减少脱掉装备的属性
                    if (goodsEquipResp.takeOffArmor != null) {
                        HeroAttributesManager.getInstance().takeOffArmor(goodsEquipResp.takeOffArmor);
                    }
                    // 增加穿装备属性
                    HeroAttributesManager.getInstance().holdOnArmor(goodsEquipResp.holdOnArmor);
                    armorSuc = true;
                }
                // 返回值是pk页面更新的type
                String type = null;
                if (armorSuc || attackSuc) {
                    type = goodsEquipResp.holdOnType;
                }
                return type;
            }
        }).

                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String type) {
                        view.dismissLoadingDialog();
                        if (!TextUtils.isEmpty(type)) {
//                    view.deleteUIByType(type);
                            initData();
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

    // 临时封装了脱掉装备的Id和穿装备的Id
    private class GoodsEquipResp {
        public String holdOnType;
        public String takeOffType;
        // 用于外部计算属性增减
        public Weapons holdOnAttack;
        public Weapons takeOffAttack;
        public Armors holdOnArmor;
        public Armors takeOffArmor;
    }
}
