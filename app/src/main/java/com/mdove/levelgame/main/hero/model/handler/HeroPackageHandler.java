package com.mdove.levelgame.main.hero.model.handler;

import com.mdove.levelgame.greendao.entity.Packages;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.main.hero.presenter.HeroPackagePresenter;

/**
 * @author MDove on 2018/10/23
 */
public class HeroPackageHandler {
    private HeroPackagePresenter presenter;

    public HeroPackageHandler(HeroPackagePresenter packagesPresenter) {
        presenter = packagesPresenter;
    }

    public void onLongClick(Long pkId){
        presenter.onLongClick(pkId);
    }

    public void onClickEquip(HeroPackageModelVM vm) {
        presenter.onClickEquip(vm);
    }

    public void onClickStrengthen(HeroPackageModelVM vm) {
        presenter.onClickStrengthen(vm);
    }

    public void onClickSell(HeroPackageModelVM vm) {
        presenter.onClickSell(vm);
    }
}
