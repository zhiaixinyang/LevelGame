package com.mdove.levelgame.main.shop.model.handler;

import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;
import com.mdove.levelgame.main.shop.presenter.BlacksmithPresenter;
import com.mdove.levelgame.main.shop.presenter.ShopAttackPresenter;

/**
 * Created by MDove on 2018/10/28.
 */

public class BlacksmithItemHandler {
    private BlacksmithPresenter blacksmithPresenter;

    public BlacksmithItemHandler(BlacksmithPresenter blacksmithPresenter) {
        this.blacksmithPresenter = blacksmithPresenter;
    }

    public void onItemBtnOnClick(BlacksmithModelVM vm) {
        blacksmithPresenter.onItemBtnClick(vm.type.get(), vm.id.get());
    }
}
