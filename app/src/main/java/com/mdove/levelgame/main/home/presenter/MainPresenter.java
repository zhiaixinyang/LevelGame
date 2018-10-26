package com.mdove.levelgame.main.home.presenter;

import com.mdove.levelgame.base.RxTransformerHelper;
import com.mdove.levelgame.greendao.utils.InitDataFileUtils;
import com.mdove.levelgame.main.hero.HeroAttributesActivity;
import com.mdove.levelgame.main.hero.HeroPackagesActivity;
import com.mdove.levelgame.main.home.presenter.MainContract;
import com.mdove.levelgame.main.monsters.MonstersPlaceActivity;
import com.mdove.levelgame.main.shop.MedicinesShopActivity;
import com.mdove.levelgame.main.shop.ShopActivity;
import com.mdove.levelgame.main.shop.adapter.MedicinesShopAdapter;

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
    public void onClickBigMonsters() {

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
}
