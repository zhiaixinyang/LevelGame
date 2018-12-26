package com.mdove.levelgame.main.fb.presenter.contract;



import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.fb.viewmodel.FbPlaceVM;

import java.util.List;

/**
 * Created by MDove on 2018/9/3.
 */
public interface FbPlaceContract {
    interface IFbPlacePresenter extends BasePresenter<IFbPlaceView> {
        void initData();
    }

    interface IFbPlaceView extends BaseView {
        void showData(List<FbPlaceVM> bannerList);
    }
}
