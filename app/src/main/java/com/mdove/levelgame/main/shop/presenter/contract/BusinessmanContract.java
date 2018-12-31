package com.mdove.levelgame.main.shop.presenter.contract;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.shop.model.mv.SellGoodsModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/29.
 */

public interface BusinessmanContract {
    interface IBusinessmanPresenter extends BasePresenter<IBusinessmanView> {
        void initData(Long monstersId);

        void onItemBtnClick(int status,String type, long price);
    }

    interface IBusinessmanView extends BaseView {
        void showData(List<SellGoodsModelVM> data);

        void showTitle(String title);
    }
}
