package com.mdove.levelgame.main.home.model;

import com.mdove.levelgame.main.home.presenter.HomePresenter;

/**
 * @author MDove on 2018/10/31
 */
public class MainMenuHandler {
    private HomePresenter presenter;

    public MainMenuHandler(HomePresenter presenter) {
        this.presenter = presenter;
    }

    public void onClickById(MainMenuModelVM vm) {
        presenter.onClickItemId(vm);
    }
}
