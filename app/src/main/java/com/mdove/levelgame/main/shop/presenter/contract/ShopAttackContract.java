package com.mdove.levelgame.main.shop.presenter.contract;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.shop.model.mv.ShopArmorModelVM;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/22.
 */

public interface ShopAttackContract {
    interface IShopAttackPresenter extends BasePresenter<IShopAttackView> {
        void initData();

        void onItemBtnClick(Long id);
    }

    interface IShopAttackView extends BaseView {
        void showData(List<ShopAttackModelVM> data);
    }
}
