package com.mdove.levelgame.main.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.databinding.ActivityMainBinding;
import com.mdove.levelgame.main.home.model.BigMonstersModelVM;
import com.mdove.levelgame.main.home.model.MainActionHandler;
import com.mdove.levelgame.main.home.presenter.MainContract;
import com.mdove.levelgame.main.home.presenter.MainPresenter;

public class MainActivity extends BaseActivity implements MainContract.IMainView {
    private ActivityMainBinding binding;
    private MainPresenter mainPresenter;

    public static void start(Context context) {
        Intent start = new Intent(context, MainActivity.class);
        if (!(context instanceof Activity)) {
            start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(start);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainPresenter = new MainPresenter();
        mainPresenter.subscribe(this);
        binding.setHandler(new MainActionHandler(mainPresenter));

        mainPresenter.initAllData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.initBigMonster();
        mainPresenter.initBigMonsterInvade();
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return true;
    }

    @Override
    public void showBigMonsters(BigMonstersModelVM vm) {
        if (vm != null) {
            binding.layoutBigMonsters.setVisibility(View.VISIBLE);
            binding.setVm(vm);
        } else {
            binding.layoutBigMonsters.setVisibility(View.GONE);
        }
    }

    @Override
    public void showBigMonsterInvade(String days) {
        binding.tvInvade.setText(Html.fromHtml(days));
    }

}
