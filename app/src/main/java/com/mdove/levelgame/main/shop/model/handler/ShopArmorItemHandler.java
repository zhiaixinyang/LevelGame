package com.mdove.levelgame.main.shop.model.handler;

import com.mdove.levelgame.main.shop.model.mv.ShopArmorModelVM;
import com.mdove.levelgame.main.shop.presenter.ShopArmorPresenter;

/**
 * Created by MDove on 2018/10/22.
 */

public class ShopArmorItemHandler {
    private ShopArmorPresenter shopArmorPresenter;

    public ShopArmorItemHandler(ShopArmorPresenter shopArmorPresenter) {
        this.shopArmorPresenter = shopArmorPresenter;
    }

    public void onItemBtnOnClick(ShopArmorModelVM vm) {
        shopArmorPresenter.onItemBtnClick(vm.id.get());
    }
}
