package com.mdove.levelgame.main.shop.presenter.contract;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/30.
 */

public interface BlacksmithArmorContract {
    interface IBlacksmithArmorPresenter extends BasePresenter<IBlacksmithArmorView> {
        void initData();

        void onItemBtnClick(String type, Long id);
    }

    interface IBlacksmithArmorView extends BaseView {
        void showData(List<BlacksmithModelVM> data);
    }
}
