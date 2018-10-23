package com.mdove.levelgame.main.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.main.shop.presenter.ShopContract;

public class ShopActivity extends BaseActivity implements ShopContract.IMedicinesShopView {
    private TabLayout tab;
    private ViewPager vp;

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        tab = findViewById(R.id.tab);
        vp = findViewById(R.id.vp);
    }
}
