package com.mdove.levelgame.main.monsters.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.monsters.model.MonstersModel;
import com.mdove.levelgame.main.monsters.model.MonstersPlaceModel;
import com.mdove.levelgame.main.monsters.model.vm.BaseMonsterModelVM;
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/21.
 */

public interface MonstersConstract {
    interface IMonstersPresenter extends BasePresenter<IMonstersView> {
        void initData(long monstersPlaceId);

        void initPower();

        void initMoney();

        void initlife();

        void heroRest();

        void onItemMyPackage();

        void onItemBtnClick(String type, Long id);

        void onItemLongBtnOnClick(Long id);
    }

    interface IMonstersView extends BaseView {
        void attackUI(int index);

        void showPowerText(String content);

        void showMyPackage();

        void showLifeText(int progress, String content);

        void showMoneyText(String content);

        void showData(List<BaseMonsterModelVM> data);
    }
}
