package com.mdove.levelgame.main.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.databinding.ActivityMainBinding;
import com.mdove.levelgame.main.home.model.MainActionHandler;
import com.mdove.levelgame.main.home.presenter.MainContract;
import com.mdove.levelgame.main.home.presenter.MainPresenter;

public class MainActivity extends BaseActivity implements MainContract.IMainView {
    private ActivityMainBinding binding;
    private MainPresenter mainPresenter;

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
    protected boolean isNeedCustomLayout() {
        return true;
    }

}
