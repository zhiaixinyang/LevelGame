package com.mdove.levelgame.main.home.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.home.model.BigMonstersModelVM;

/**
 * Created by MDove on 2018/10/21.
 */

public interface MainContract {
    interface IMainPresenter extends BasePresenter<IMainView> {
        void onClickMonstersPlace();

        void onClickHeroAttributes();

        void onClickBuyMedicines();

        void onClickGoShop();

        void onClickHeroPackage();
        void onClickBigMonsters(BigMonstersModelVM vm);

        void initAllData();
        void initBigMonster();
    }

    interface IMainView extends BaseView {
        void showBigMonsters(BigMonstersModelVM vm);
    }
}
