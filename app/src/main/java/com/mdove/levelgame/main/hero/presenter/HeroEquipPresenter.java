package com.mdove.levelgame.main.hero.presenter;

import android.text.TextUtils;

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
        initEquipData();
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
                        equipData.set(1, new HeroEquipModelVM(weapons.id, weapons.tips, attackType.strengthen, weapons.name, weapons.attack, weapons.armor, 0, weapons.type, true, 1));
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
                        equipData.set(2, new HeroEquipModelVM(armor.id, armor.tips, armorType.strengthen, armor.name, armor.attack, armor.armor, 0, armor.type, true, 2));
                    }
                }
                break;
            }
            case 3: {
                // 查询装备的饰品
                InitEquipResp accessoriesType1 = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ACCESSORIES);
                if (!TextUtils.isEmpty(accessoriesType1.equipType)) {
                    Accessories accessories = accessoriesDao.queryBuilder().where(AccessoriesDao.Properties.Type.eq(accessoriesType1.equipType)).unique();
                    if (accessories != null) {
                        equipData.set(3, new HeroEquipModelVM(accessories.id, accessories.tips, accessoriesType1.strengthen, accessories.name, accessories.attack, accessories.armor, accessories.life, accessories.type, true, 3));
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
        view.showEquipData(equipData);
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
                                    model.takeOffPkId = pk.id;
                                    model.takeOffStrengthen = pk.strengthenLevel;
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
                                    model.takeOffPkId = pk.id;
                                    model.takeOffStrengthen = pk.strengthenLevel;
                                    model.takeOffArmor = takeOffArmor;
                                }
                                return model;
                            }
                            // 饰品逻辑
                            case AllGoodsToDBIdUtils.DB_TYPE_IS_ACCESSORIES: {
                                GoodsEquipResp model = new GoodsEquipResp();
                                // 从库中找到脱的铠甲
                                Accessories takeOffAccessories = accessoriesDao.queryBuilder().where(AccessoriesDao.Properties.Type.eq(type)).unique();
                                // 从所有持有的装备(Packages)中，重置isEquip的状态
                                Packages pk = packagesDao.queryBuilder().where(PackagesDao.Properties.Type.eq(vm.type.get())
                                        , PackagesDao.Properties.IsEquip.eq(Integer.valueOf(0))).unique();
                                if (pk != null) {
                                    // 更新脱装备的状态
                                    pk.isEquip = 1;
                                    packagesDao.update(pk);
                                    model.takeOffType = pk.type;
                                    model.takeOffPkId = pk.id;
                                    model.takeOffStrengthen = pk.strengthenLevel;
                                    model.takeOffAccessories = takeOffAccessories;
                                }
                                return model;
                            }
                            default:
                                break;
                        }
                        return null;
                    }
                }).map(new Function<GoodsEquipResp, NotifyResp>() {
            @Override
            public NotifyResp apply(GoodsEquipResp goodsEquipResp) throws Exception {
                // 更新英雄属性
                boolean attackSuc = false;
                boolean armorSuc = false;
                boolean accessoriesSuc = false;
                // 减少脱掉武器的属性
                if (goodsEquipResp.takeOffAttack != null) {
                    HeroAttributesManager.getInstance().takeOffAttack(goodsEquipResp.takeOffStrengthen, goodsEquipResp.takeOffAttack);
                    attackSuc = true;
                }
                // 减少脱掉护甲的属性
                if (goodsEquipResp.takeOffArmor != null) {
                    HeroAttributesManager.getInstance().takeOffArmor(goodsEquipResp.takeOffStrengthen, goodsEquipResp.takeOffArmor);
                    armorSuc = true;
                }
                // 减少脱掉饰品的属性
                if (goodsEquipResp.takeOffAccessories != null) {
                    HeroAttributesManager.getInstance().takeOffAccessories(goodsEquipResp.takeOffStrengthen, goodsEquipResp.takeOffAccessories);
                    accessoriesSuc = true;
                }
                NotifyResp resp = null;
                if (armorSuc || attackSuc || accessoriesSuc) {
                    resp = new NotifyResp();
                    resp.takeOffOnId = goodsEquipResp.takeOffPkId;
                    if (armorSuc) {
                        resp.takeOffPosition = 2;
                    } else if (attackSuc) {
                        resp.takeOffPosition = 1;
                    } else if (accessoriesSuc) {
                        resp.takeOffPosition = 3;
                    }
                }
                return resp;
            }
        }).subscribe(new Observer<NotifyResp>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(NotifyResp resp) {
                view.dismissLoadingDialog();
                if (resp.takeOffOnId != -1) {
                    takeOffById(resp.takeOffPosition);
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

    private void takeOffById(int position) {
        switch (position) {
            case 1: {
                equipData.set(1, new HeroEquipModelVM((long) -1,view.getString(R.string.string_no_hold_on_attack), 0, view.getString(R.string.string_no_hold_on_attack), 0, 0, 0, "-1", false, 1));
                break;
            }
            case 2: {
                equipData.set(2, new HeroEquipModelVM((long) -1,view.getString(R.string.string_no_hold_on_armor), 0, view.getString(R.string.string_no_hold_on_armor), 0, 0, 0, "-1", false, 2));
                break;
            }
            case 3: {
                equipData.set(3, new HeroEquipModelVM((long) -1,view.getString(R.string.string_no_hold_on_accessories), 0, view.getString(R.string.string_no_hold_on_accessories), 0, 0, 0, "-1", false, 3));
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
        public String takeOffType;
        // 用于查强化等级
        public long takeOffStrengthen;
        // 用于外部计算属性增减
        public Weapons takeOffAttack;
        public Armors takeOffArmor;
        public Accessories takeOffAccessories;
    }

    private class NotifyResp {
        public long takeOffOnId = -1;
        // 在装备页脱装备，不能单纯的delete，需要添加未装备的占位符
        public int takeOffPosition = -1;
    }
}
