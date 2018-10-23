package com.mdove.levelgame.main.shop.model.handler;

import com.mdove.levelgame.main.shop.model.mv.MedicinesModelVM;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;
import com.mdove.levelgame.main.shop.presenter.MedicinesShopPresenter;
import com.mdove.levelgame.main.shop.presenter.ShopAttackPresenter;

/**
 * Created by MDove on 2018/10/22.
 */

public class ShopAttackItemHandler {
    private ShopAttackPresenter shopAttackPresenter;

    public ShopAttackItemHandler(ShopAttackPresenter shopAttackPresenter) {
        this.shopAttackPresenter = shopAttackPresenter;
    }

    public void onItemBtnOnClick(ShopAttackModelVM vm) {
        shopAttackPresenter.onItemBtnClick(vm.id.get());
    }
}
