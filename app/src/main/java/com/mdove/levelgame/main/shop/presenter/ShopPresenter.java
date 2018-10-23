package com.mdove.levelgame.main.shop.presenter;

public class ShopPresenter implements ShopContract.IMedicinesShopPresenter {
    private ShopContract.IMedicinesShopView view;

    @Override
    public void subscribe(ShopContract.IMedicinesShopView view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {

    }
}
