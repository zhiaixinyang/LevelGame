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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.main.hero.fragment.HeroEquipFragment;
import com.mdove.levelgame.main.hero.fragment.HeroPackageFragment;
import com.mdove.levelgame.main.shop.adapter.BlacksmithAdapter;
import com.mdove.levelgame.main.shop.fragment.BlacksmithAccessoriesFragment;
import com.mdove.levelgame.main.shop.fragment.BlacksmithArmorFragment;
import com.mdove.levelgame.main.shop.fragment.BlacksmithAttackFragment;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.presenter.BlacksmithContract;
import com.mdove.levelgame.main.shop.presenter.BlacksmithPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MDove on 2018/10/27.
 */

public class BlacksmithActivity extends BaseActivity {
    private TabLayout tab;
    private ViewPager vp;
    private String[] titles;
    private List<Fragment> fragments;


    public static void start(Context context) {
        Intent start = new Intent(context, BlacksmithActivity.class);
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
        setTitle(R.string.string_blacksmith);
        setContentView(R.layout.activity_blacksmith);
        tab = findViewById(R.id.tab);
        vp = findViewById(R.id.vp);

        titles = new String[]{getString(R.string.string_fragment_title_update_attack), getString(R.string.string_fragment_title_update_armor)
                , getString(R.string.string_fragment_title_update_accessories)};
        fragments = new ArrayList<>();
        fragments.add(BlacksmithAttackFragment.newInstance());
        fragments.add(BlacksmithArmorFragment.newInstance());
        fragments.add(BlacksmithAccessoriesFragment.Companion.newInstance());

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
