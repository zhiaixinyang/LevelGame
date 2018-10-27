package com.mdove.levelgame.main.home.presenter;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.RxTransformerHelper;
import com.mdove.levelgame.greendao.BigMonstersDao;
import com.mdove.levelgame.greendao.entity.BigMonsters;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.HeroAttributesActivity;
import com.mdove.levelgame.main.hero.HeroPackagesActivity;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.model.AttackResp;
import com.mdove.levelgame.main.home.model.BigMonstersModelVM;
import com.mdove.levelgame.main.home.presenter.MainContract;
import com.mdove.levelgame.main.monsters.MonstersPlaceActivity;
import com.mdove.levelgame.main.shop.MedicinesShopActivity;
import com.mdove.levelgame.main.shop.ShopActivity;
import com.mdove.levelgame.main.shop.adapter.MedicinesShopAdapter;
import com.mdove.levelgame.utils.ToastHelper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by MDove on 2018/10/21.
 */

public class MainPresenter implements MainContract.IMainPresenter {
    private MainContract.IMainView mView;

    public MainPresenter() {
    }

    @Override
    public void subscribe(MainContract.IMainView view) {
        mView = view;
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void onClickMonstersPlace() {
        MonstersPlaceActivity.start(mView.getContext());
    }

    @Override
    public void onClickHeroAttributes() {
        HeroAttributesActivity.start(mView.getContext());
    }

    @Override
    public void onClickBuyMedicines() {
        MedicinesShopActivity.start(mView.getContext());
    }

    @Override
    public void onClickGoShop() {
        ShopActivity.start(mView.getContext());
    }

    @Override
    public void onClickHeroPackage() {
        HeroPackagesActivity.start(mView.getContext());
    }

    @Override
    public void onClickBigMonsters(BigMonstersModelVM vm) {
        AttackResp resp = HeroAttributesManager.getInstance().attackBigMonsters(vm.id.get());
        switch (resp.attackStatus) {
            case HeroAttributesManager.ATTACK_STATUS_ERROR: {
                ToastHelper.shortToast(mView.getContext().getString(R.string.string_error));
                break;
            }
            case HeroAttributesManager.ATTACK_STATUS_NO_POWER: {
                ToastHelper.shortToast(mView.getContext().getString(R.string.string_no_power));
                break;
            }
            case HeroAttributesManager.ATTACK_STATUS_FAIL: {
                ToastHelper.shortToast(mView.getContext().getString(R.string.string_attack_fail));
                break;
            }
            case HeroAttributesManager.ATTACK_STATUS_WIN: {
                ToastHelper.shortToast(String.format(mView.getContext().getString(R.string.string_attack_win), resp.money, resp.exp, resp.life));
                break;
            }
            case HeroAttributesManager.ATTACK_STATUS_HAS_DROP_GOODS: {
                String dropGood = "";
                for (String name : resp.dropGoods) {
                    dropGood = name + "、";
                }
                dropGood = dropGood.substring(0, dropGood.length() - 1);
                ToastHelper.shortToast(String.format(mView.getContext().getString(R.string.string_attack_win_has_goods), resp.money, resp.exp, resp.life,dropGood));
                break;
            }
            default:
                break;
        }
        initBigMonster();
    }

    @Override
    public void initAllData() {
        Observable.just(1)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        mView.showLoadingDialog("加载本地数据，请稍后...");
                    }
                }).compose(RxTransformerHelper.<Integer>schedulerTransf())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        mView.dismissLoadingDialog();
                    }
                });
    }

    @Override
    public void initBigMonster() {
        BigMonstersDao bigMonstersDao = DatabaseManager.getInstance().getBigMonstersDao();
        List<BigMonsters> bigMonsters = bigMonstersDao.loadAll();
        if (bigMonsters != null && bigMonsters.size() > 0) {
            for (BigMonsters bigMonster : bigMonsters) {
                if (bigMonster.isGone == 1) {
                    mView.showBigMonsters(new BigMonstersModelVM(bigMonster));
                    return;
                }
            }
        }
        mView.showBigMonsters(null);
    }
}
