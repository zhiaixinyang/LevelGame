package com.mdove.levelgame.main.monsters.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.monsters.model.MonstersModel;
import com.mdove.levelgame.main.monsters.model.MonstersPlaceModel;
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/21.
 */

public interface MonstersConstract {
    interface IMonstersPresenter extends BasePresenter<IMonstersView> {
        void initData(long monstersPlaceId);

        void initPower();

        void heroRest();

        void onItemBtnClick(String type, Long id);
    }

    interface IMonstersView extends BaseView {
        void attackUI(int index);

        void showPowerText(String content);

        void showData(List<MonstersModelVM> data);
    }
}
