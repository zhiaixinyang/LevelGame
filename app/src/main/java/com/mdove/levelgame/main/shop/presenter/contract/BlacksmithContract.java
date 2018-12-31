package com.mdove.levelgame.main.shop.presenter.contract;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.model.mv.MedicinesModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/28.
 */

public interface BlacksmithContract {
    interface IBlacksmithPresenter extends BasePresenter<IBlacksmithView> {
        void initData();

        void onItemBtnClick(String type, Long id);
    }

    interface IBlacksmithView extends BaseView {
        void showData(List<BlacksmithModelVM> data);
    }
}
