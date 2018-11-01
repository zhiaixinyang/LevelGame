package com.mdove.levelgame.main.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.View;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.databinding.ActivityHomeBinding;
import com.mdove.levelgame.main.home.adapter.HomeAdapter;
import com.mdove.levelgame.main.home.model.BigMonstersModelVM;
import com.mdove.levelgame.main.home.model.HomeActionHandler;
import com.mdove.levelgame.main.home.model.MainActionHandler;
import com.mdove.levelgame.main.home.model.MainMenuModelVM;
import com.mdove.levelgame.main.home.presenter.HomeContract;
import com.mdove.levelgame.main.home.presenter.HomePresenter;

import java.util.List;

/**
 * @author MDove on 2018/10/31
 */
public class HomeActivity extends BaseActivity implements HomeContract.IHomeView {
    private HomePresenter presenter;
    private HomeAdapter adapter;
    private ActivityHomeBinding binding;

    public static void start(Context context) {
        Intent start = new Intent(context, HomeActivity.class);
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        presenter = new HomePresenter();
        presenter.subscribe(this);

        adapter = new HomeAdapter(presenter);
        binding.rlv.setLayoutManager(new LinearLayoutManager(this));
        binding.rlv.setAdapter(adapter);

        presenter.initMenu();
        presenter.initBigMonster();
        presenter.initBigMonsterInvade();

        binding.setHandler(new HomeActionHandler(presenter));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initBigMonsterInvade();
        presenter.initBigMonster();
    }

    @Override
    public void showMenu(List<MainMenuModelVM> data) {
        adapter.setData(data);
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
