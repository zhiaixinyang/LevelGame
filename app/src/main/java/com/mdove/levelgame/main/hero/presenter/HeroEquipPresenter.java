package com.mdove.levelgame.main.hero.presenter;

import android.text.TextUtils;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.RxTransformerHelper;
import com.mdove.levelgame.greendao.ArmorsDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.WeaponsDao;
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
    private HeroEquipContract.IHeroEquipView view;

    private List<HeroEquipModelVM> equipData;

    private PackagesDao packagesDao;
    private WeaponsDao weaponsDao;
    private ArmorsDao armorsDao;

    public HeroEquipPresenter() {
        weaponsDao = DatabaseManager.getInstance().getWeaponsDao();
        packagesDao = DatabaseManager.getInstance().getPackagesDao();
        armorsDao = DatabaseManager.getInstance().getArmorsDao();
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

    private void initEquipData() {
        equipData = new ArrayList<>();
        // Title的布局
        equipData.add(new HeroEquipModelVM((long) -2, 0, "", 0, 0, null, false, false));

        // 查询装备的武器
        InitEquipResp attackType = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ATTACK);
        if (!TextUtils.isEmpty(attackType.equipType)) {
            Weapons weapons = weaponsDao.queryBuilder().where(WeaponsDao.Properties.Type.eq(attackType.equipType)).unique();
            if (weapons != null) {
                equipData.add(new HeroEquipModelVM(weapons.id, attackType.strengthen, weapons.name, weapons.attack, weapons.armor, weapons.type, true, true));
            }
        }

        // 添加武器占位
        if (equipData.size() == 1) {
            equipData.add(new HeroEquipModelVM((long) -1, 0, view.getString(R.string.string_no_hold_on_attack), 0, 0, "-1", false, true));
        }
        // 查询装备的防具
        InitEquipResp armorType = getGoodsTypeFromPk(EQUIP_STATUS_TYPE_ARMOR);
        if (!TextUtils.isEmpty(armorType.equipType)) {
            Armors armor = armorsDao.queryBuilder().where(ArmorsDao.Properties.Type.eq(armorType.equipType)).unique();
            if (armor != null) {
                equipData.add(new HeroEquipModelVM(armor.id, armorType.strengthen, armor.name, armor.attack, armor.armor, armor.type, true, false));
            }
        }
        // 添加防具占位
        if (equipData.size() == 2) {
            equipData.add(new HeroEquipModelVM((long) -1, 0, view.getString(R.string.string_no_hold_on_armor), 0, 0, "-1", false, false));
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
                                    model.takeOffStrengthen = pk.strengthenLevel;
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
                    HeroAttributesManager.getInstance().takeOffAttack(goodsEquipResp.takeOffStrengthen, goodsEquipResp.takeOffAttack);
                    attackSuc = true;
                }
                // 减少脱掉护甲的属性
                if (goodsEquipResp.takeOffArmor != null) {
                    HeroAttributesManager.getInstance().takeOffArmor(goodsEquipResp.takeOffStrengthen, goodsEquipResp.takeOffArmor);
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

    // 临时封装了脱掉装备的Id和穿装备的Id
    private class GoodsEquipResp {
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
    }
}
