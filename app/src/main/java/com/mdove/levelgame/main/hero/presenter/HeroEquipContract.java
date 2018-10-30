package com.mdove.levelgame.main.hero.presenter;

import com.mdove.levelgame.base.BasePresenter;
import com.mdove.levelgame.base.BaseView;
import com.mdove.levelgame.main.hero.model.HeroEquipModelVM;

import java.util.List;

/**
 * Created by MDove on 2018/10/30.
 */

public interface HeroEquipContract {
    interface IHeroEquipPresenter extends BasePresenter<IHeroEquipView> {
        void initData();

        void onClickTakeOff(HeroEquipModelVM vm);
    }

    interface IHeroEquipView extends BaseView {
        void showEquipData(List<HeroEquipModelVM> data);

        void notifyByPosition(int position);
    }
}
