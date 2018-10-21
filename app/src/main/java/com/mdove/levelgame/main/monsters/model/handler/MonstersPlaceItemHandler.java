package com.mdove.levelgame.main.monsters.model.handler;

import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;
import com.mdove.levelgame.main.monsters.model.vm.MonstersPlaceModelVM;
import com.mdove.levelgame.main.monsters.presenter.MonstersPlacePresenter;
import com.mdove.levelgame.utils.ToastHelper;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersPlaceItemHandler {
    private MonstersPlacePresenter monstersPlacePresenter;

    public MonstersPlaceItemHandler(MonstersPlacePresenter monstersPlacePresenter) {
        this.monstersPlacePresenter = monstersPlacePresenter;
    }

    public void onItemBtnOnClick(MonstersPlaceModelVM vm) {
        monstersPlacePresenter.onItemBtnClick(vm.id.get(), vm.name.get());
    }
}
