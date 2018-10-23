package com.mdove.levelgame.main.hero.model.handler;

import com.mdove.levelgame.main.hero.model.HasEquipModelVM;
import com.mdove.levelgame.main.hero.presenter.HeroPackagesPresenter;

/**
 * @author MDove on 2018/10/23
 */
public class HasEquipHandler {
    private HeroPackagesPresenter presenter;

    public HasEquipHandler(HeroPackagesPresenter packagesPresenter) {
        presenter = packagesPresenter;
    }

    public void onClickTakeOff(HasEquipModelVM vm){
        presenter.onClickTakeOff(vm);
    }
}
