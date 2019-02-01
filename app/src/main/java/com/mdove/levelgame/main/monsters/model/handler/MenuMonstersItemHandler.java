package com.mdove.levelgame.main.monsters.model.handler;

import com.mdove.levelgame.main.monsters.presenter.MonstersPresenter;

/**
 * Created by MDove on 2019/1/27.
 */

public class MenuMonstersItemHandler {
    private MonstersPresenter monstersPresenter;

    public MenuMonstersItemHandler(MonstersPresenter monstersPresenter) {
        this.monstersPresenter = monstersPresenter;
    }

    public void onItemBtnHeroRest() {
        monstersPresenter.heroRest();
    }

    public void onItemMyPackage() {
        monstersPresenter.onItemMyPackage();
    }
    public void onItemMyAttr() {
        monstersPresenter.onItemMyAttr();
    }
}
