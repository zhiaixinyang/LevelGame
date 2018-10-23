package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.shop.model.ShopArmorModel;
import com.mdove.levelgame.main.shop.model.ShopAttackModel;
import com.mdove.levelgame.main.shop.model.mv.ShopArmorModelVM;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;

import java.util.ArrayList;
import java.util.List;

public class ShopArmorPresenter implements ShopArmorContract.IShopArmorPresenter {
    private ShopArmorContract.IShopArmorView view;

    @Override
    public void subscribe(ShopArmorContract.IShopArmorView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
        List<ShopArmorModel> list = InitDataFileUtils.getShopArmors();
        List<ShopArmorModelVM> data = new ArrayList<>();
        for (ShopArmorModel model : list) {
            data.add(new ShopArmorModelVM(model));
        }
        view.showData(data);
    }

    @Override
    public void onItemBtnClick(Long id) {

    }
}
