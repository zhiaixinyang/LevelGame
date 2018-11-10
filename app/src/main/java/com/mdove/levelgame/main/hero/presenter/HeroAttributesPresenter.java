package com.mdove.levelgame.main.hero.presenter;

import com.mdove.levelgame.greendao.HeroAttributesDao;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.hero.model.HeroAttributesModelVM;

/**
 * Created by MDove on 2018/10/21.
 */

public class HeroAttributesPresenter implements HeroAttributesContract.IHeroAttributesPresenter {
    private HeroAttributesDao heroAttributesDao;
    private HeroAttributesContract.IHeroAttributesView view;

    public HeroAttributesPresenter() {
        heroAttributesDao = DatabaseManager.getInstance().getHeroAttributesDao();
    }

    @Override
    public void subscribe(HeroAttributesContract.IHeroAttributesView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void initData() {
        view.showData(new HeroAttributesModelVM(HeroManager.getInstance().getHeroAttributes()));
    }
}
