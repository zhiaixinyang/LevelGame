package com.mdove.levelgame.main.hero.model.handler;

import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.main.hero.presenter.HeroPackagesPresenter;

/**
 * @author MDove on 2018/10/23
 */
public class HeroPackageHandler {
    private HeroPackagesPresenter presenter;

    public HeroPackageHandler(HeroPackagesPresenter packagesPresenter) {
        presenter = packagesPresenter;
    }

    public void onClickEquip(HeroPackageModelVM vm){
        presenter.onClickEquip(vm);
    }

    public void onClickStrengthen(HeroPackageModelVM vm){
        presenter.onClickStrengthen(vm);
    }

    public void onClickSell(HeroPackageModelVM vm){
        presenter.onClickSell(vm);
    }
}
