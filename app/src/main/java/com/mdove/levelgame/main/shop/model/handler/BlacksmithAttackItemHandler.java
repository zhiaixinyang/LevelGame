package com.mdove.levelgame.main.shop.model.handler;

import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.presenter.BlacksmithAttackPresenter;
import com.mdove.levelgame.main.shop.presenter.BlacksmithPresenter;

/**
 * Created by MDove on 2018/10/30.
 */

public class BlacksmithAttackItemHandler {
    private BlacksmithAttackPresenter blacksmithPresenter;

    public BlacksmithAttackItemHandler(BlacksmithAttackPresenter blacksmithPresenter) {
        this.blacksmithPresenter = blacksmithPresenter;
    }

    public void onItemBtnOnClick(BlacksmithModelVM vm) {
        blacksmithPresenter.onItemBtnClick(vm.type.get(), vm.id.get());
    }
}
