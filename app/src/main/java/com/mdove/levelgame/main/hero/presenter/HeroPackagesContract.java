package com.mdove.levelgame.main.hero.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.hero.model.HeroEquipModelVM;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/23.
 */

public interface HeroPackagesContract {
    interface IHeroPackagesPresenter extends BasePresenter<IHeroPackagesView> {
        void initData();

        void onClickTakeOff(HeroEquipModelVM vm);

        void onClickEquip(HeroPackageModelVM vm);

        void onClickStrengthen(HeroPackageModelVM vm);

        void onClickSell(HeroPackageModelVM vm);
    }

    interface IHeroPackagesView extends BaseView {
        void showEquipData(List<HeroEquipModelVM> data);

        void showPackage(List<HeroPackageModelVM> data);

        void notifyByPosition(int position);

        // 效果差
        void deleteUIByType(String type);
    }
}
