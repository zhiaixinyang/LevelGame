package com.mdove.levelgame.main.hero.presenter;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.mdove.levelgame.App;
import com.mdove.levelgame.R;
import com.mdove.levelgame.base.RxTransformerHelper;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.model.HasEquipModelVM;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.main.shop.model.ShopArmorModel;
import com.mdove.levelgame.main.shop.model.ShopAttackModel;
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

    public HeroPackagesPresenter() {
        packagesDao = App.getDaoSession().getPackagesDao();
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
        for (Packages packages1 : packages) {
            int dbTpe = AllGoodsToDBIdUtils.getInstance().getDBType(packages1.type);
            switch (dbTpe) {
                case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK: {
                    for (ShopAttackModel model : InitDataFileUtils.getShopWeapons()) {
                        if (TextUtils.equals(model.type, packages1.type) && packages1.isEquip == 1) {
                            packageModelVMS.add(new HeroPackageModelVM(packages1.id, model.name, model.attack, model.armor, model.type));
                            break;
                        }
                    }
                    break;
                }
                case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR: {
                    for (ShopArmorModel model : InitDataFileUtils.getShopArmors()) {
                        if (TextUtils.equals(model.type, packages1.type) && packages1.isEquip == 1) {
                            packageModelVMS.add(new HeroPackageModelVM(packages1.id, model.name, model.attack, model.armor, model.type));
                            break;
                        }
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
        String attackType = getGoodsType(EQUIP_STATUS_TYPE_ATTACK);
        List<ShopAttackModel> attacks = InitDataFileUtils.getShopWeapons();
        if (!TextUtils.isEmpty(attackType)) {
            for (ShopAttackModel model : attacks) {
                if (TextUtils.equals(model.type, attackType)) {
                    equipData.add(new HasEquipModelVM(model.id, model.name, model.attack + "", model.armor + "", model.type));
                    break;
                }
            }
        }

        // 添加武器占位
        if (equipData.size() == 0) {
            equipData.add(new HasEquipModelVM((long) -1, view.getString(R.string.string_no_hold_on_attack), view.getString(R.string.string_no_hold_on_attack), view.getString(R.string.string_no_hold_on_attack), "-1"));
        }
        // 查询装备的防具
        String armorType = getGoodsType(EQUIP_STATUS_TYPE_ARMOR);
        List<ShopArmorModel> armors = InitDataFileUtils.getShopArmors();
        if (!TextUtils.isEmpty(armorType)) {
            for (ShopArmorModel model : armors) {
                if (model.type == attackType) {
                    equipData.add(new HasEquipModelVM(model.id, model.name, model.attack + "", model.armor + "", model.type));
                    break;
                }
            }
        }
        // 添加防具占位
        if (equipData.size() == 1) {
            equipData.add(new HasEquipModelVM((long) -1, view.getString(R.string.string_no_hold_on_armor), view.getString(R.string.string_no_hold_on_armor), view.getString(R.string.string_no_hold_on_armor), "-1"));
        }
        view.showEquipData(equipData);
    }

    private String getGoodsType(int status) {
        String type = null;
        List<Packages> packages = packagesDao.queryBuilder().list();
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

    @Override
    public void onClickTakeOff(final HasEquipModelVM vm) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 获取是武器还是防具
                int type = AllGoodsToDBIdUtils.getInstance().getDBType(vm.type.get());
                e.onNext(type);
            }
        }).compose(RxTransformerHelper.<Integer>schedulerTransf())
                .map(new Function<Integer, GoodsTempModel>() {
                    @Override
                    public GoodsTempModel apply(Integer integer) throws Exception {
                        switch (integer) {
                            // 武器逻辑
                            case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK: {
                                GoodsTempModel model = new GoodsTempModel();
                                Weapons takeOffAttack = null;
                                // 从库中找到所脱武器
                                List<Weapons> weapons = InitDataFileUtils.getInitWeapons();
                                for (Weapons weapon : weapons) {
                                    if (TextUtils.equals(vm.type.get(), weapon.type)) {
                                        takeOffAttack = weapon;
                                    }
                                }
                                // 更新Pk库
                                List<Packages> packages = packagesDao.loadAll();
                                // 从所有持有的装备(Packages)中，重置isEquip的状态
                                if (packages != null && packages.size() > 0) {
                                    Packages pk = packagesDao.queryBuilder().where(PackagesDao.Properties.Type.eq(vm.type.get())
                                            ,PackagesDao.Properties.IsEquip.eq(Integer.valueOf(0))).unique();
                                    if (pk != null) {
                                        // 更新脱装备的状态
                                        pk.isEquip = 1;
                                        packagesDao.update(pk);
                                        model.takeOffType = pk.type;
                                        model.takeOffAttack = takeOffAttack;
                                    }
                                }
                                return model;
                            }
                            // 防具逻辑
                            case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR: {
                                GoodsTempModel model = new GoodsTempModel();
                                Armors takeOffArmors = null;
                                // 从库中找到所穿防具，及所脱防具（可能为null）
                                List<Armors> armors = InitDataFileUtils.getInitArmors();
                                for (Armors armor : armors) {
                                    if (TextUtils.equals(vm.type.get(), armor.type)) {
                                        takeOffArmors = armor;
                                    }
                                }
                                // 更新Pk库
                                List<Packages> packages = packagesDao.loadAll();
                                // 从所有持有的装备(Packages)中，重置isEquip的状态
                                if (packages != null && packages.size() > 0) {
                                    Packages pk = packagesDao.queryBuilder().where(PackagesDao.Properties.Type.eq(vm.type.get())
                                            ,PackagesDao.Properties.IsEquip.eq(Integer.valueOf(0))).unique();
                                    // 更新脱装备的状态
                                    pk.isEquip = 1;
                                    packagesDao.update(pk);
                                    model.takeOffType = pk.type;
                                    model.takeOffArmor = takeOffArmors;
                                }
                                return model;
                            }
                            default:
                                break;
                        }
                        return null;
                    }
                }).map(new Function<GoodsTempModel, String>() {
            @Override
            public String apply(GoodsTempModel goodsTempModel) throws Exception {
                // 更新英雄属性
                boolean attackSuc = false;
                boolean armorSuc = false;
                // 减少脱掉武器的属性
                if (goodsTempModel.takeOffAttack != null) {
                    HeroAttributesManager.getInstance().takeOffAttack(goodsTempModel.takeOffAttack);
                    attackSuc = true;
                }
                // 减少脱掉护甲的属性
                if (goodsTempModel.takeOffArmor != null) {
                    HeroAttributesManager.getInstance().takeOffArmor(goodsTempModel.takeOffArmor);
                    armorSuc = true;
                }
                // 返回值是pk页面更新的type
                String type = null;
                if (armorSuc || attackSuc) {
                    type = goodsTempModel.takeOffType;
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
        view.showLoadingDialog(view.getString(R.string.string_equip_loading));
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 获取是武器还是防具
                int type = AllGoodsToDBIdUtils.getInstance().getDBType(vm.type.get());
                e.onNext(type);
            }
        }).compose(RxTransformerHelper.<Integer>schedulerTransf())
                .map(new Function<Integer, GoodsTempModel>() {
                    @Override
                    public GoodsTempModel apply(Integer integer) throws Exception {
                        switch (integer) {
                            // 武器逻辑
                            case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK: {
                                GoodsTempModel model = new GoodsTempModel();
                                Weapons holdOnAttack = null;
                                Weapons hasHoldOnAttack = null;//脱
                                String hasHoldOnType = getGoodsType(EQUIP_STATUS_TYPE_ATTACK);
                                // 从库中找到所穿武器，及所脱武器（可能为null）
                                List<Weapons> weapons = InitDataFileUtils.getInitWeapons();
                                for (Weapons weapon : weapons) {
                                    if (TextUtils.equals(vm.type.get(), weapon.type)) {
                                        holdOnAttack = weapon;
                                    }
                                    if (TextUtils.equals(weapon.type, hasHoldOnType)) {
                                        hasHoldOnAttack = weapon;
                                    }
                                }
                                // 更新Pk库
                                Packages pkTakeOff = null;
                                Packages pkHoldOn = null;
                                PackagesDao packagesDao = App.getDaoSession().getPackagesDao();
                                List<Packages> packages = packagesDao.loadAll();
                                // 从所有持有的装备(Packages)中，重置isEquip的状态
                                if (packages != null && packages.size() > 0) {
                                    for (Packages pk : packages) {
                                        if (TextUtils.equals(pk.type, hasHoldOnType)) {
                                            // 需要脱的装备
                                            pkTakeOff = pk;
                                            pkTakeOff.isEquip = 1;
                                        }
                                        if (TextUtils.equals(pk.type, vm.type.get())) {
                                            // 所穿装备
                                            pkHoldOn = pk;
                                            pkHoldOn.isEquip = 0;
                                        }
                                    }
                                    // 更新脱装备的状态
                                    if (pkTakeOff != null) {
                                        packagesDao.update(pkTakeOff);
                                        model.takeOffType = pkTakeOff.type;
                                        model.takeOffAttack = hasHoldOnAttack;
                                    } else {
                                        model.takeOffType = null;
                                        model.takeOffAttack = null;
                                    }
                                    // 更新穿装备的状态
                                    if (pkHoldOn != null) {
                                        packagesDao.update(pkHoldOn);
                                        model.holdOnType = pkHoldOn.type;
                                        model.holdOnAttack = holdOnAttack;
                                    } else {
                                        model.holdOnType = null;
                                        model.holdOnAttack = null;
                                    }
                                }
                                return model;
                            }
                            // 防具逻辑
                            case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR: {
                                GoodsTempModel model = new GoodsTempModel();
                                Armors holdOnArmors = null;
                                Armors hasHoldOnArmors = null;//脱
                                String hasHoldOnType = getGoodsType(EQUIP_STATUS_TYPE_ARMOR);
                                // 从库中找到所穿防具，及所脱防具（可能为null）
                                List<Armors> armors = InitDataFileUtils.getInitArmors();
                                for (Armors armor : armors) {
                                    if (TextUtils.equals(vm.type.get(), armor.type)) {
                                        holdOnArmors = armor;
                                    }
                                    if (TextUtils.equals(armor.type, hasHoldOnType)) {
                                        hasHoldOnArmors = armor;
                                    }
                                }
                                // 更新Pk库
                                Packages pkTakeOff = null;
                                Packages pkHoldOn = null;
                                PackagesDao packagesDao = App.getDaoSession().getPackagesDao();
                                List<Packages> packages = packagesDao.loadAll();
                                // 从所有持有的装备(Packages)中，重置isEquip的状态
                                if (packages != null && packages.size() > 0) {
                                    for (Packages pk : packages) {
                                        if (TextUtils.equals(pk.type, hasHoldOnType)) {
                                            // 需要脱的装备
                                            pkTakeOff = pk;
                                            pkTakeOff.isEquip = 1;
                                        }
                                        if (TextUtils.equals(pk.type, vm.type.get())) {
                                            // 所穿装备
                                            pkHoldOn = pk;
                                            pkHoldOn.isEquip = 0;
                                        }
                                    }
                                    // 更新脱装备的状态
                                    if (pkTakeOff != null) {
                                        packagesDao.update(pkTakeOff);
                                        model.takeOffType = pkTakeOff.type;
                                        model.takeOffArmor = hasHoldOnArmors;
                                    } else {
                                        model.takeOffType = null;
                                        model.takeOffArmor = null;
                                    }
                                    // 更新穿装备的状态
                                    if (pkHoldOn != null) {
                                        packagesDao.update(pkHoldOn);
                                        model.holdOnType = pkHoldOn.type;
                                        model.holdOnArmor = holdOnArmors;
                                    } else {
                                        model.holdOnType = null;
                                        model.holdOnArmor = null;
                                    }
                                }
                                return model;
                            }
                            default:
                                break;
                        }
                        return null;
                    }
                }).map(new Function<GoodsTempModel, String>() {
            @Override
            public String apply(GoodsTempModel goodsTempModel) throws Exception {
                // 更新英雄属性
                boolean attackSuc = false;
                boolean armorSuc = false;
                // 穿的装备是武器
                if (goodsTempModel.holdOnAttack != null) {
                    // 先减少脱掉装备的属性
                    if (goodsTempModel.takeOffAttack != null) {
                        HeroAttributesManager.getInstance().takeOffAttack(goodsTempModel.takeOffAttack);
                    }
                    // 增加穿装备属性
                    HeroAttributesManager.getInstance().holdOnAttack(goodsTempModel.holdOnAttack);
                    attackSuc = true;
                }
                // 穿的装备是护甲
                if (goodsTempModel.holdOnArmor != null) {
                    // 先减少脱掉装备的属性
                    if (goodsTempModel.takeOffArmor != null) {
                        HeroAttributesManager.getInstance().takeOffArmor(goodsTempModel.takeOffArmor);
                    }
                    // 增加穿装备属性
                    HeroAttributesManager.getInstance().holdOnArmor(goodsTempModel.holdOnArmor);
                    armorSuc = true;
                }
                // 返回值是pk页面更新的type
                String type = null;
                if (armorSuc || attackSuc) {
                    type = goodsTempModel.holdOnType;
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
    private class GoodsTempModel {
        public String holdOnType;
        public String takeOffType;
        public Weapons holdOnAttack;
        public Weapons takeOffAttack;
        public Armors holdOnArmor;
        public Armors takeOffArmor;
    }
}
