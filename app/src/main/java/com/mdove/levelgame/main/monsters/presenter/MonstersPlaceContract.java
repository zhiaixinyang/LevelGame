package com.mdove.levelgame.main.monsters.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.greendao.entity.MonstersPlace;
import com.mdove.levelgame.main.monsters.model.MonstersPlaceModel;

import java.util.List;

/**
 * Created by MDove on 2018/10/21.
 */

public interface MonstersPlaceContract {
    interface IMonstersPlacePresenter extends BasePresenter<IMonstersPlaceView> {
        void initData();

        void onItemBtnClick(Long id, String title);
    }

    interface IMonstersPlaceView extends BaseView {
        void showData(List<MonstersPlace> data);
    }
}
