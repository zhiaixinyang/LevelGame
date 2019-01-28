package com.mdove.levelgame.main.monsters.model.handler;

import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;
import com.mdove.levelgame.main.monsters.presenter.MonstersPresenter;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersItemHandler {
    private MonstersPresenter monstersPresenter;

    public MonstersItemHandler(MonstersPresenter monstersPresenter) {
        this.monstersPresenter = monstersPresenter;
    }

    public void onItemBtnOnClick(MonstersModelVM vm) {
        monstersPresenter.onItemBtnClick(vm.type.get(), vm.id.get());
    }

    public boolean onItemLongBtnOnClick(MonstersModelVM vm) {
        monstersPresenter.onItemLongBtnOnClick(vm.id.get());
        return false;
    }
}
