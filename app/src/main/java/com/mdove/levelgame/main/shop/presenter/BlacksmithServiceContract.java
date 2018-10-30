package com.mdove.levelgame.main.shop.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/30.
 */

public interface BlacksmithServiceContract {
    interface IBlacksmithServicePresenter extends BasePresenter<IBlacksmithServiceView> {
        void initData();

        void onItemBtnClick(String type, Long id);
    }

    interface IBlacksmithServiceView extends BaseView {
        void showData(List<BlacksmithModelVM> data);
    }
}
