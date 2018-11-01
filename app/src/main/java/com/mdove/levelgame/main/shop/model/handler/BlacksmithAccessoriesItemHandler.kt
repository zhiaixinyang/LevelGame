package com.mdove.levelgame.main.shop.model.handler;

import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.presenter.BlacksmithAccessoriesPresenter;
import com.mdove.levelgame.main.shop.presenter.BlacksmithArmorPresenter;

/**
 * Created by MDove on 2018/11/1.
 */

class BlacksmithAccessoriesItemHandler constructor(presenter: BlacksmithAccessoriesPresenter) {
    var presenter: BlacksmithAccessoriesPresenter = presenter

    fun onItemBtnOnClick(vm: BlacksmithModelVM) {
        presenter.onItemBtnClick(vm.type.get(), vm.id.get())
    }
}
