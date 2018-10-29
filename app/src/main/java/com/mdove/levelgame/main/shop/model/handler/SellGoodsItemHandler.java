package com.mdove.levelgame.main.shop.model.handler;

import com.mdove.levelgame.main.shop.model.mv.SellGoodsModelVM;
import com.mdove.levelgame.main.shop.presenter.BusinessmanPresenter;

/**
 * Created by MDove on 2018/10/29.
 */

public class SellGoodsItemHandler {
    private BusinessmanPresenter presenter;

    public SellGoodsItemHandler(BusinessmanPresenter presenter) {
        this.presenter = presenter;
    }

    public void onItemBtnOnClick(SellGoodsModelVM vm) {
        presenter.onItemBtnClick(vm.type.get(),vm.realPrice.get());
    }
}
