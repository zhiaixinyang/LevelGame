package com.mdove.levelgame.main.hero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mdove.levelgame.BuildConfig;
import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.config.AppConfig;
import com.mdove.levelgame.databinding.ActivityHeroAttributesBinding;
import com.mdove.levelgame.greendao.utils.DatabaseManager;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.hero.model.HeroAttributesModelVM;
import com.mdove.levelgame.main.hero.presenter.HeroAttributesContract;
import com.mdove.levelgame.main.hero.presenter.HeroAttributesPresenter;
import com.mdove.levelgame.main.monsters.MonstersPlaceActivity;
import com.mdove.levelgame.utils.ToastHelper;

/**
 * Created by MDove on 2018/10/21.
 */

public class HeroAttributesActivity extends BaseActivity implements HeroAttributesContract.IHeroAttributesView {
    private ActivityHeroAttributesBinding binding;
    private HeroAttributesPresenter presenter;
    private Toolbar mToolbar;
    private long clickTime = 0;
    private boolean isStartClick = false;
    private long clickCount = 0;

    public static void start(Context context) {
        Intent start = new Intent(context, HeroAttributesActivity.class);
        if (!(context instanceof Activity)) {
            start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(start);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hero_attributes);
        initToolbar();

        presenter = new HeroAttributesPresenter();
        presenter.subscribe(this);

        presenter.initData();

        if (BuildConfig.DEBUG) {
            binding.tvDays.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConfig.setFirstLogin(false);
                }
            });
        }
        binding.tvBodyPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartClick = true;
                if (isStartClick && System.currentTimeMillis() - clickTime > 1500) {
                    clickCount++;
                } else {
                    clickTime = System.currentTimeMillis();
                    clickCount++;
                }
                if (clickCount == 5) {
                    isStartClick = false;
                    clickCount = 0;
                    HeroAttributesManager.getInstance().resetPower();
                    ToastHelper.shortToast(getString(R.string.string_faker_power));
                }
            }
        });
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void showData(HeroAttributesModelVM vm) {
        binding.setVm(vm);
    }
}
