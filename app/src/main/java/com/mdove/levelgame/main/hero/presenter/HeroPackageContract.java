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

        void onLongClick(long pkId);

        void onClickSell(HeroPackageModelVM vm);

        void notifyPackageAddUI(long pkId);
    }

    interface IHeroPackageView extends BaseView {
        void showPackage(List<HeroPackageModelVM> data);

        void notifyByPosition(int position);

        void deleteByPosition(int position);

        void addByPosition(int position);

        //通知Equip页面重新对进行刷新（重新加载对应的值）
        void notifyEquipUpdateUI(int position);

        void notifyPackageAddUI(long pkId);
    }
}
