package com.mdove.levelgame.main.home.model;

import com.mdove.levelgame.main.home.presenter.MainPresenter;

/**
 * Created by MDove on 2018/10/21.
 */

public class MainActionHandler {
    private MainPresenter mainPresenter;

    public MainActionHandler(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    public void onClickMonstersPlace() {
        mainPresenter.onClickMonstersPlace();
    }

    public void onClickGoShop() {
        mainPresenter.onClickGoShop();
    }

    public void onClickHeroAttributes() {
        mainPresenter.onClickHeroAttributes();
    }

    public void onClickBuyMedicines() {
        mainPresenter.onClickBuyMedicines();
    }
}
