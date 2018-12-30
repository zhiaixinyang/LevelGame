package com.mdove.levelgame.main.home.city.presenter.contract;


import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.home.city.model.CityVM;

import java.util.List;

/**
 * Created by MDove on 2018/9/3.
 */
public interface CityContract {
    interface ICityPresenter extends BasePresenter<ICityView> {
        void initData();

        void onClick(CityVM cityVM);
    }

    interface ICityView extends BaseView {
        void showData(List<CityVM> bannerList);

        void onClickCity(Long placeId);
    }
}
