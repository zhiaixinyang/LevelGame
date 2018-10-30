package com.mdove.levelgame.main.shop.model.handler;

import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.presenter.BlacksmithArmorPresenter;
import com.mdove.levelgame.main.shop.presenter.BlacksmithAttackPresenter;

/**
 * Created by MDove on 2018/10/30.
 */

public class BlacksmithArmorItemHandler {
    private BlacksmithArmorPresenter blacksmithPresenter;

    public BlacksmithArmorItemHandler(BlacksmithArmorPresenter blacksmithPresenter) {
        this.blacksmithPresenter = blacksmithPresenter;
    }

    public void onItemBtnOnClick(BlacksmithModelVM vm) {
        blacksmithPresenter.onItemBtnClick(vm.type.get(), vm.id.get());
    }
}
