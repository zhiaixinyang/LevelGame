package com.mdove.levelgame.main.monsters.presenter;

import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.monsters.MonstersActivity;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersPlacePresenter implements MonstersPlaceContract.IMonstersPlacePresenter {
    private MonstersPlaceContract.IMonstersPlaceView view;

    @Override
    public void subscribe(MonstersPlaceContract.IMonstersPlaceView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
        view.showData(InitDataFileUtils.getInitMonstersPlace());
    }

    @Override
    public void onItemBtnClick(Long id, String title) {
        MonstersActivity.start(view.getContext(), id, title);
    }
}
