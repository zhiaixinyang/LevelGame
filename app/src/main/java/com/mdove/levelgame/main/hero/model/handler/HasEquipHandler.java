package com.mdove.levelgame.main.hero.model.handler;

import com.mdove.levelgame.main.hero.model.HeroEquipModelVM;
import com.mdove.levelgame.main.hero.presenter.HeroEquipPresenter;

/**
 * @author MDove on 2018/10/23
 */
public class HasEquipHandler {
    private HeroEquipPresenter presenter;

    public HasEquipHandler(HeroEquipPresenter packagesPresenter) {
        presenter = packagesPresenter;
    }

    public void onClickTakeOff(HeroEquipModelVM vm){
        presenter.onClickTakeOff(vm);
    }
}
