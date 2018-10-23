package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.shop.model.ShopAttackModel;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;

import java.util.ArrayList;
import java.util.List;

public class ShopAttackPresenter implements ShopAttackContract.IShopAttackPresenter {
    private ShopAttackContract.IShopAttackView view;

    @Override
    public void subscribe(ShopAttackContract.IShopAttackView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
        List<ShopAttackModel> list = InitDataFileUtils.getShopWeapons();
        List<ShopAttackModelVM> data = new ArrayList<>();
        for (ShopAttackModel model : list) {
            data.add(new ShopAttackModelVM(model));
        }
        view.showData(data);
    }

    @Override
    public void onItemBtnClick(Long id) {

    }
}
