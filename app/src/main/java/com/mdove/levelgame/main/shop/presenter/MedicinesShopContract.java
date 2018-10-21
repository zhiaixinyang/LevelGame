package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.shop.model.MedicinesModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/21.
 */

public interface MedicinesShopContract {
    interface IMedicinesShopPresenter extends BasePresenter<IMedicinesShopView> {
        void initData();

        void onItemBtnClick(Long id);
    }

    interface IMedicinesShopView extends BaseView {
        void showData(List<MedicinesModelVM> data);
    }
}
