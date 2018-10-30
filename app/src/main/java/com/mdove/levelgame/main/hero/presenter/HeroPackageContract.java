package com.mdove.levelgame.main.hero.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/30.
 */

public interface HeroPackageContract {
    interface IHeroPackagePresenter extends BasePresenter<IHeroPackageView> {
        void initData();

        void onClickEquip(HeroPackageModelVM vm);

        void onClickStrengthen(HeroPackageModelVM vm);

        void onClickSell(HeroPackageModelVM vm);
    }

    interface IHeroPackageView extends BaseView {
        void showPackage(List<HeroPackageModelVM> data);

        void notifyByPosition(int position);
    }
}
