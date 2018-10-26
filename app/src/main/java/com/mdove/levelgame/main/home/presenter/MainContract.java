package com.mdove.levelgame.main.home.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;

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
        void onClickBigMonsters();

        void initAllData();
    }

    interface IMainView extends BaseView {

    }
}
