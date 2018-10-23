package com.mdove.levelgame.main.hero.presenter;

import android.text.TextUtils;

import com.mdove.levelgame.App;
import com.mdove.levelgame.config.AppConfig;
import com.mdove.levelgame.greendao.HeroAttributesDao;
import com.mdove.levelgame.greendao.PackagesDao;
import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.model.HasEquipModelVM;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.main.shop.model.ShopArmorModel;
import com.mdove.levelgame.main.shop.model.ShopAttackModel;
import com.mdove.levelgame.utils.AllGoodsToDBIdUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2018/10/23.
 */

public class HeroPackagesPresenter implements HeroPackagesContract.IHeroPackagesPresenter {
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
        List<HasEquipModelVM> equipData = new ArrayList<>();
        // 查询装备的武器
        String attackType = AppConfig.getHoldWeaponsType();
        List<ShopAttackModel> attacks = InitDataFileUtils.getShopWeapons();
        if (!TextUtils.equals(attackType, "-1")) {
            for (ShopAttackModel model : attacks) {
                if (model.type == attackType) {
                    equipData.add(new HasEquipModelVM(model.id, model.name, model.attack, model.armor));
                    break;
                }
            }
        }
        // 查询装备的防具
        String armorType = AppConfig.getHoldArmorType();
        List<ShopArmorModel> armors = InitDataFileUtils.getShopArmors();
        if (!TextUtils.equals(armorType, "-1")) {
            for (ShopArmorModel model : armors) {
                if (model.type == attackType) {
                    equipData.add(new HasEquipModelVM(model.id, model.name, model.attack, model.armor));
                    break;
                }
            }
        }
        view.showEquipData(equipData);

        List<Packages> packages = packagesDao.loadAll();
        List<HeroPackageModelVM> packageModelVMS = new ArrayList<>();
        for (Packages packages1 : packages) {
            int dbTpe = AllGoodsToDBIdUtils.getInstance().getDBType(packages1.type);
            switch (dbTpe) {
                case AllGoodsToDBIdUtils.DB_TYPE_IS_ATTACK: {
                    for (ShopAttackModel model : InitDataFileUtils.getShopWeapons()) {
                        if (model.id == packages1.id) {
                            packageModelVMS.add(new HeroPackageModelVM(packages1.id, model.name, model.attack, model.armor));
                            break;
                        }
                    }
                    break;
                }
                case AllGoodsToDBIdUtils.DB_TYPE_IS_ARMOR: {
                    for (ShopArmorModel model : InitDataFileUtils.getShopArmors()) {
                        if (model.id == packages1.id) {
                            packageModelVMS.add(new HeroPackageModelVM(packages1.id, model.name, model.attack, model.armor));
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

    @Override
    public void onClickTakeOff(HasEquipModelVM vm) {

    }

    @Override
    public void onClickEquip(HeroPackageModelVM vm) {

    }
}
