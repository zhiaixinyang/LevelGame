package com.mdove.levelgame.main.shop.model.handler;

import com.mdove.levelgame.main.shop.model.mv.MedicinesModelVM;
import com.mdove.levelgame.main.shop.presenter.ShopMedicinesPresenter;

/**
 * Created by MDove on 2018/10/21.
 */

public class MedicinesItemHandler {
    private ShopMedicinesPresenter medicinesShopPresenter;

    public MedicinesItemHandler(ShopMedicinesPresenter medicinesShopPresenter) {
        this.medicinesShopPresenter = medicinesShopPresenter;
    }

    public void onItemBtnOnClick(MedicinesModelVM vm) {
        medicinesShopPresenter.onItemBtnClick(vm.id.get());
    }
}
