package com.mdove.levelgame.main.home.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.home.model.BigMonstersModelVM;
import com.mdove.levelgame.main.home.model.MainMenuModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/30.
 */

public interface HomeContract {
    interface IHomePresenter extends BasePresenter<IHomeView> {
        void onClickItemId(MainMenuModelVM vm);

        void initMenu();

        void initBigMonster();

        void initBigMonsterInvade();

        void onClickSetting();

        void onClickBigMonsters(BigMonstersModelVM vm);

        void initGuide();
    }

    interface IHomeView extends BaseView {
        void showMenu(List<MainMenuModelVM> data);

        void showBigMonsters(BigMonstersModelVM vm);

        void showBigMonsterInvade(String days);

        void showGuide();
    }
}
