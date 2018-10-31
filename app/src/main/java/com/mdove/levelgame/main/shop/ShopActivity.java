package com.mdove.levelgame.main.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.main.shop.fragment.ShopArmorFragment;
import com.mdove.levelgame.main.shop.fragment.ShopAttackFragment;
import com.mdove.levelgame.main.shop.fragment.ShopMedicinesFragment;
import com.mdove.levelgame.main.shop.presenter.ShopContract;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends BaseActivity implements ShopContract.IMedicinesShopView {
    private TabLayout tab;
    private ViewPager vp;
    private String[] titles;
    private List<Fragment> fragments;

    public static void start(Context context) {
        Intent start = new Intent(context, ShopActivity.class);
        if (!(context instanceof Activity)) {
            start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(start);
    }

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

        titles = new String[]{getString(R.string.string_fragment_title_buy_attack), getString(R.string.string_fragment_title_buy_armor),
                getString(R.string.string_fragment_title_buy_medicine)};
        fragments = new ArrayList<>();
        fragments.add(ShopAttackFragment.newInstance());
        fragments.add(ShopArmorFragment.newInstance());
        fragments.add(ShopMedicinesFragment.newInstance());

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        tab.setupWithViewPager(vp);
    }
}
