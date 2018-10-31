package com.mdove.levelgame.main.hero;

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
import com.mdove.levelgame.main.hero.fragment.HeroEquipFragment;
import com.mdove.levelgame.main.hero.fragment.HeroPackageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MDove on 2018/10/23
 */
public class HeroPackagesActivity extends BaseActivity {
    private TabLayout tab;
    private ViewPager vp;
    private String[] titles;
    private List<Fragment> fragments;
    private HeroEquipFragment heroEquipFragment;
    private HeroPackageFragment heroPackageFragment;

    public static void start(Context context) {
        Intent start = new Intent(context, HeroPackagesActivity.class);
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
        setTitle(R.string.string_packages);
        setContentView(R.layout.activity_hero_packages);

        tab = findViewById(R.id.tab);
        vp = findViewById(R.id.vp);

        titles = new String[]{getString(R.string.string_fragment_title_equip), getString(R.string.string_fragment_title_package)};
        fragments = new ArrayList<>();
        heroEquipFragment = HeroEquipFragment.newInstance();
        heroPackageFragment = HeroPackageFragment.newInstance();
        fragments.add(heroEquipFragment);
        fragments.add(heroPackageFragment);

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

    //通知Equip页面重新对进行刷新（重新加载对应的值）
    public void notifyEquipUpdateUI(int position) {
        if (heroEquipFragment != null) {
            heroEquipFragment.notifyEquipUpdateUI(position);
        }
    }
    //通知Packages页面增加刚刚脱掉的装备
    public void notifyPackageAddUI(long pkiId) {
        if (heroPackageFragment != null) {
            heroPackageFragment.notifyPackageAddUI(pkiId);
        }
    }
}
