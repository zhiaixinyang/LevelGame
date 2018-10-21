package com.mdove.levelgame.main.shop.model.handler;

import com.mdove.levelgame.main.shop.model.MedicinesModelVM;
import com.mdove.levelgame.main.shop.presenter.MedicinesShopPresenter;

/**
 * Created by MDove on 2018/10/21.
 */

public class MedicinesItemHandler {
    private MedicinesShopPresenter medicinesShopPresenter;

    public MedicinesItemHandler(MedicinesShopPresenter medicinesShopPresenter) {
        this.medicinesShopPresenter = medicinesShopPresenter;
    }

    public void onItemBtnOnClick(MedicinesModelVM vm) {
        medicinesShopPresenter.onItemBtnClick(vm.id.get());
    }
}
