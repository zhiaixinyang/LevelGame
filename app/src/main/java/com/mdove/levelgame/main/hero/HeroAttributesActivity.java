package com.mdove.levelgame.main.hero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.databinding.ActivityHeroAttributesBinding;
import com.mdove.levelgame.main.hero.model.HeroAttributesModelVM;
import com.mdove.levelgame.main.hero.presenter.HeroAttributesContract;
import com.mdove.levelgame.main.hero.presenter.HeroAttributesPresenter;
import com.mdove.levelgame.main.monsters.MonstersPlaceActivity;

/**
 * Created by MBENBEN on 2018/10/21.
 */

public class HeroAttributesActivity extends BaseActivity implements HeroAttributesContract.IHeroAttributesView {
    private ActivityHeroAttributesBinding binding;
    private HeroAttributesPresenter presenter;

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

        presenter = new HeroAttributesPresenter();
        presenter.subscribe(this);

        presenter.initData();
    }

    @Override
    public void showData(HeroAttributesModelVM vm) {
        binding.setVm(vm);
    }
}
