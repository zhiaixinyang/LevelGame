package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.shop.model.mv.ShopArmorModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/23.
 */

public interface ShopArmorContract {
    interface IShopArmorPresenter extends BasePresenter<IShopArmorView> {
        void initData();

        void onItemBtnClick(Long id);
    }

    interface IShopArmorView extends BaseView {
        void showData(List<ShopArmorModelVM> data);
    }
}
