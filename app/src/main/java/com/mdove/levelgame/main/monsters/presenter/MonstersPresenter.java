package com.mdove.levelgame.main.monsters.presenter;

import com.mdove.levelgame.R;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.model.AttackResp;
import com.mdove.levelgame.main.monsters.model.MonstersModel;
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;
import com.mdove.levelgame.utils.ToastHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersPresenter implements MonstersConstract.IMonstersPresenter {
    private MonstersConstract.IMonstersView view;
    private List<MonstersModelVM> realData;

    @Override
    public void subscribe(MonstersConstract.IMonstersView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {
    }

    @Override
    public void initData(long monstersPlaceId) {
        List<MonstersModel> monsters = InitDataFileUtils.getInitMonsters();
        realData = new ArrayList<>();

        for (MonstersModel monstersModel : monsters) {
            if (monstersModel.monsterPlaceId == monstersPlaceId) {
                realData.add(new MonstersModelVM(monstersModel));
            }
        }
        view.showData(realData);
    }

    @Override
    public void onItemBtnClick(final Long id) {
        MonstersModelVM modelVM = null;
        int uiIndex = -1;
        for (MonstersModelVM monstersModel : realData) {
            if (monstersModel.id.get() == id) {
                modelVM = monstersModel;
                uiIndex = realData.indexOf(monstersModel);
            }
        }

        if (modelVM != null && uiIndex != -1) {
            Observable.just(1)
                    .doOnNext(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            view.showLoadingDialog(view.getContext().getString(R.string.string_attack_loading_msg));
                        }
                    })
                    .delay(3, TimeUnit.SECONDS)
                    .flatMap(new Function<Integer, ObservableSource<AttackResp>>() {
                        @Override
                        public ObservableSource<AttackResp> apply(Integer integer) throws Exception {
                            return Observable.just(HeroAttributesManager.getInstance().attack(id));
                        }
                    }).subscribe(new Consumer<AttackResp>() {
                @Override
                public void accept(AttackResp resp) throws Exception {
                    switch (resp.attackStatus) {
                        case HeroAttributesManager.ATTACK_STATUS_ERROR: {
                            ToastHelper.shortToast(view.getContext().getString(R.string.string_error));
                            break;
                        }
                        case HeroAttributesManager.ATTACK_STATUS_FAIL: {
                            ToastHelper.shortToast(view.getContext().getString(R.string.string_attack_fail));
                            break;
                        }
                        case HeroAttributesManager.ATTACK_STATUS_WIN: {
                            ToastHelper.shortToast(String.format(view.getContext().getString(R.string.string_attack_win), resp.money, resp.exp, resp.life));
                            break;
                        }
                    }
                    view.dismissLoadingDialog();
                }
            });
        }
    }
}
