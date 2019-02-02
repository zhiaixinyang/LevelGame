package com.mdove.levelgame.main.shop.presenter.contract;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.shop.model.mv.MedicinesModelVM;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/30.
 */
public interface ShopMedicinesContract {
    interface IShopMedicinesPresenter extends BasePresenter<IShopMedicinesView> {
        void initData();

        void onItemBtnClick(String type);
    }

    interface IShopMedicinesView extends BaseView {
        void showData(List<MedicinesModelVM> data);

        void notifyUI(int position);
    }
}
