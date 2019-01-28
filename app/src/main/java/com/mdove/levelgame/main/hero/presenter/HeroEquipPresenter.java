package com.mdove.levelgame.main.hero.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.RxTransformerHelper;
import com.mdove.levelgame.greendao.AccessoriesDao;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
import com.mdove.levelgame.greendao.entity.Accessories;
import com.mdove.levelgame.greendao.entity.Armors;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.entity.Weapons;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.model.HeroEquipModelVM;
import com.mdove.levelgame.model.IAttrsModel;
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils;
import com.mdove.levelgame.view.MyDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by MDove on 2018/10/30.
 */

public class HeroEquipPresenter implements HeroEquipContract.IHeroEquipPresenter {
    private static final int EQUIP_STATUS_TYPE_ATTACK = 1;
    private static final int EQUIP_STATUS_TYPE_ARMOR = 2;
    private static final int EQUIP_STATUS_TYPE_ACCESSORIES = 3;
    private HeroEquipContract.IHeroEquipView view;

    private List<HeroEquipModelVM> equipData;

    private PackagesDao packagesDao;
    private WeaponsDao weaponsDao;
    private ArmorsDao armorsDao;
    private AccessoriesDao accessoriesDao;

    public HeroEquipPresenter() {
        weaponsDao = DatabaseManager.getInstance().getWeaponsDao();
        packagesDao = DatabaseManager.getInstance().getPackagesDao();
        armorsDao = DatabaseManager.getInstance().getArmorsDao();
        accessoriesDao = DatabaseManager.getInstance().getAccessoriesDao();
    }

    @Override
    public void subscribe(HeroEquipContract.IHeroEquipView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
//        view.showLoadingDialog(view.getString(R.string.string_init_data_loading));
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            initEquipData();
            e.onNext(1);
        }).compose(RxTransformerHelper.schedulerTransf())
                .subscribe(integer -> {
                    view.showEquipData(equipData);
                    view.dismissLoadingDialog();
                }, throwable -> {
                    view.dismissLoadingDialog();
                });
    }

    @Override
    public void notifyEquipUpdateUI(int position) {
        // 根据position重新加载数据，刷UI
        switch (position) {
            case 1: {
                InitEquipResp attackType = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ATTACK);
                if (!TextUtils.isEmpty(attackType.equipType)) {
                    Weapons weapons = weaponsDao.queryBuilder().where(WeaponsDao.Properties.Type.eq(attackType.equipType)).unique();
                    if (weapons != null) {
                        equipData.set(1, new HeroEquipModelVM(attackType.pkId, weapons.tips, attackType.strengthen, weapons.name, weapons.attack, weapons.armor, 0, weapons.type, true, 1));
                    }
                }
                break;
            }
            case 2: {
                // 查询装备的防具
                InitEquipResp armorType = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ARMOR);
                if (!TextUtils.isEmpty(armorType.equipType)) {
                    Armors armor = armorsDao.queryBuilder().where(ArmorsDao.Properties.Type.eq(armorType.equipType)).unique();
                    if (armor != null) {
                        equipData.set(2, new HeroEquipModelVM(armorType.pkId, armor.tips, armorType.strengthen, armor.name, armor.attack, armor.armor, 0, armor.type, true, 2));
                    }
                }
                break;
            }
            case 3: {
                // 查询装备的饰品
                InitEquipResp accessoriesType = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ACCESSORIES);
                if (!TextUtils.isEmpty(accessoriesType.equipType)) {
                    Accessories accessories = accessoriesDao.queryBuilder().where(AccessoriesDao.Properties.Type.eq(accessoriesType.equipType)).unique();
                    if (accessories != null) {
                        equipData.set(3, new HeroEquipModelVM(accessoriesType.pkId, accessories.tips, accessoriesType.strengthen, accessories.name, accessories.attack, accessories.armor, accessories.life, accessories.type, true, 3));
                    }
                }
                break;
            }
            default:
                break;
        }
        view.notifyByPosition(position);
    }

    private void initEquipData() {
        equipData = new ArrayList<>();
        // Title的布局
        equipData.add(new HeroEquipModelVM((long) -2, "", 0, "", 0, 0, 0, null, false, 1));

        // 查询装备的武器
        InitEquipResp attackType = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ATTACK);
        if (!TextUtils.isEmpty(attackType.equipType)) {
            Weapons weapons = weaponsDao.queryBuilder().where(WeaponsDao.Properties.Type.eq(attackType.equipType)).unique();
            if (weapons != null) {
                equipData.add(new HeroEquipModelVM(attackType.pkId, weapons.tips, attackType.strengthen, weapons.name, weapons.attack, weapons.armor, 0, weapons.type, true, 1));
            }
        }

        // 添加武器占位
        if (equipData.size() == 1) {
            equipData.add(new HeroEquipModelVM((long) -1, view.getString(R.string.string_no_hold_on_attack), 0, view.getString(R.string.string_no_hold_on_attack), 0, 0, 0, "-1", false, 1));
        }
        // 查询装备的防具
        InitEquipResp armorType = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ARMOR);
        if (!TextUtils.isEmpty(armorType.equipType)) {
            Armors armor = armorsDao.queryBuilder().where(ArmorsDao.Properties.Type.eq(armorType.equipType)).unique();
            if (armor != null) {
                equipData.add(new HeroEquipModelVM(armorType.pkId, armor.tips, armorType.strengthen, armor.name, armor.attack, armor.armor, 0, armor.type, true, 2));
            }
        }
        // 添加防具占位
        if (equipData.size() == 2) {
            equipData.add(new HeroEquipModelVM((long) -1, view.getString(R.string.string_no_hold_on_armor), 0, view.getString(R.string.string_no_hold_on_armor), 0, 0, 0, "-1", false, 2));
        }
        // 查询装备的饰品
        InitEquipResp accessoriesType1 = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ACCESSORIES);
        if (!TextUtils.isEmpty(accessoriesType1.equipType)) {
            Accessories accessories = accessoriesDao.queryBuilder().where(AccessoriesDao.Properties.Type.eq(accessoriesType1.equipType)).unique();
            if (accessories != null) {
                equipData.add(new HeroEquipModelVM(accessoriesType1.pkId, accessories.tips, accessoriesType1.strengthen, accessories.name, accessories.attack, accessories.armor, accessories.life, accessories.type, true, 3));
            }
        }
        // 添加饰品占位
        if (equipData.size() == 3) {
            equipData.add(new HeroEquipModelVM((long) -1, view.getString(R.string.string_no_hold_on_accessories), 0, view.getString(R.string.string_no_hold_on_accessories), 0, 0, 0, "-1", false, 3));
        }
    }


    public class InitEquipResp {
        public long pkId;
        public String equipType;
        public long strengthen;
    }

    // 从pk里找到装备对应的type
    private InitEquipResp getGoodsTypeFromPk(int status) {
        InitEquipResp initEquipResp = new InitEquipResp();
        List<Packages> packages = packagesDao.queryBuilder().where(PackagesDao.Properties.IsEquip.eq(0)).list();
        if (packages == null || packages.size() <= 0) {
            return initEquipResp;
        }
        switch (status) {
            case EQUIP_STATUS_TYPE_ATTACK: {
                for (Packages pk : packages) {
                    if (pk.isEquip == 0 && pk.type.startsWith("A")) {
                        initEquipResp.equipType = pk.type;
                        initEquipResp.pkId = pk.id;
                        initEquipResp.strengthen = pk.strengthenLevel;
                        break;
                    }
                }
                break;
            }
            case EQUIP_STATUS_TYPE_ARMOR: {
                for (Packages pk : packages) {
                    if (pk.isEquip == 0 && pk.type.startsWith("B")) {
                        initEquipResp.equipType = pk.type;
                        initEquipResp.pkId = pk.id;
                        initEquipResp.strengthen = pk.strengthenLevel;
                        break;
                    }
                }
                break;
            }
            case EQUIP_STATUS_TYPE_ACCESSORIES: {
                for (Packages pk : packages) {
                    if (pk.isEquip == 0 && pk.type.startsWith("G")) {
                        initEquipResp.equipType = pk.type;
                        initEquipResp.pkId = pk.id;
                        initEquipResp.strengthen = pk.strengthenLevel;
                        break;
                    }
                }
                break;
            }
            default:
                break;
        }
        return initEquipResp;
    }

    @Override
    public void onClickTakeOff(final HeroEquipModelVM vm) {
        Observable.create((ObservableOnSubscribe<IAttrsModel>) e -> {
            // 获取是武器还是防具
            e.onNext(AllGoodsToDBIdUtils.getInstance().getAttrsModelFromType(vm.type.get()));
        }).compose(RxTransformerHelper.<IAttrsModel>schedulerTransf())
                .map(model -> {
                    GoodsEquipResp resp = new GoodsEquipResp();
                    // 从所有持有的装备(Packages)中，重置isEquip的状态
                    Packages pk = packagesDao.queryBuilder().where(PackagesDao.Properties.Type.eq(vm.type.get())
                            , PackagesDao.Properties.IsEquip.eq(Integer.valueOf(0))).unique();
                    if (pk != null) {
                        // 更新脱装备的状态
                        pk.isEquip = 1;
                        packagesDao.update(pk);
                        resp.takeOffType = pk.type;
                        resp.takeOffPkId = pk.id;
                        resp.pk = pk;
                        resp.takeOffStrengthen = pk.strengthenLevel;
                        resp.takeOffModel = model;
                    }
                    return resp;
                }).map(goodsEquipResp -> {
            // 更新英雄属性
            boolean takeOffSuc = false;
            if (goodsEquipResp.takeOffModel != null) {
                HeroAttributesManager.getInstance().takeOff(goodsEquipResp.takeOffStrengthen, goodsEquipResp.takeOffModel,
                        goodsEquipResp.pk.getRandomAttr());
                takeOffSuc = true;
            }
            NotifyResp resp = null;
            if (takeOffSuc) {
                resp = new NotifyResp();
                resp.takeOffOnId = goodsEquipResp.takeOffPkId;
                resp.takeOffType = goodsEquipResp.takeOffType;
            }
            return resp;
        }).subscribe(new Observer<NotifyResp>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(NotifyResp resp) {
                view.dismissLoadingDialog();
                if (resp.takeOffOnId != -1) {
                    takeOffByType(resp.takeOffType);
                    view.notifyPackageAddUI(resp.takeOffOnId);
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

    private void takeOffByType(String type) {
        int position = -1;
        if (equipData == null) {
            return;
        }
        for (HeroEquipModelVM modelVM : equipData) {
            if (!TextUtils.isEmpty(modelVM.type.get()) && modelVM.type.get().equals(type)) {
                position = equipData.indexOf(modelVM);
            }
        }
        switch (position) {
            case 1: {
                equipData.set(1, new HeroEquipModelVM((long) -1, view.getString(R.string.string_no_hold_on_attack), 0, view.getString(R.string.string_no_hold_on_attack), 0, 0, 0, "-1", false, 1));
                break;
            }
            case 2: {
                equipData.set(2, new HeroEquipModelVM((long) -1, view.getString(R.string.string_no_hold_on_armor), 0, view.getString(R.string.string_no_hold_on_armor), 0, 0, 0, "-1", false, 2));
                break;
            }
            case 3: {
                equipData.set(3, new HeroEquipModelVM((long) -1, view.getString(R.string.string_no_hold_on_accessories), 0, view.getString(R.string.string_no_hold_on_accessories), 0, 0, 0, "-1", false, 3));
                break;
            }
            default:
                break;
        }
        view.notifyByPosition(position);

    }

    // 临时封装了脱掉装备的Id和穿装备的Id
    private class GoodsEquipResp {
        public long takeOffPkId;
        public Packages pk;
        public String takeOffType;
        // 用于查强化等级
        public long takeOffStrengthen;
        // 用于外部计算属性增减
        public IAttrsModel takeOffModel;

        public Weapons takeOffAttack;
        public Armors takeOffArmor;
        public Accessories takeOffAccessories;
    }

    private class NotifyResp {
        // 在装备页脱装备，不能单纯的delete，需要添加未装备的占位符(通过id去计算position)
        public String takeOffType;
        public long takeOffOnId = -1;
    }
}
