package com.mdove.levelgame.main.hero.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.hero.model.HasEquipModelVM;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/23.
 */

public interface HeroPackagesContract {
    interface IHeroPackagesPresenter extends BasePresenter<IHeroPackagesView> {
        void initData();

        void onClickTakeOff(HasEquipModelVM vm);

        void onClickEquip(HeroPackageModelVM vm);
    }

    interface IHeroPackagesView extends BaseView {
        void showEquipData(List<HasEquipModelVM> data);

        void showPackage(List<HeroPackageModelVM> data);

        void deleteUIByType(String type);
    }
}
