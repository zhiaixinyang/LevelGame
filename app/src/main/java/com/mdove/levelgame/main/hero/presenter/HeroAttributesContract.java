package com.mdove.levelgame.main.hero.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.hero.model.HeroAttributesModelVM;

/**
 * Created by MDove on 2018/10/21.
 */

public interface HeroAttributesContract {
    interface IHeroAttributesPresenter extends BasePresenter<IHeroAttributesView> {
        void initData();
    }

    interface IHeroAttributesView extends BaseView {
        void showData(HeroAttributesModelVM vm);
    }
}
