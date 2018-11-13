package com.mdove.levelgame.main.home.model;

import com.mdove.levelgame.main.home.presenter.HomePresenter;

/**
 * Created by MDove on 2018/11/1.
 */

public class HomeActionHandler {
    private HomePresenter presenter;

    public HomeActionHandler(HomePresenter presenter) {
        this.presenter = presenter;
    }

    public void onClickSetting(){
        presenter.onClickSetting();
    }

    public void onClickBigMonsters(BigMonstersModelVM vm) {
        presenter.onClickBigMonsters(vm);
    }
}
