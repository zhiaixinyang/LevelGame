package com.mdove.levelgame.main.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.main.shop.adapter.BlacksmithAdapter;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.presenter.BlacksmithContract;
import com.mdove.levelgame.main.shop.presenter.BlacksmithPresenter;

import java.util.List;

/**
 * Created by MDove on 2018/10/27.
 */

public class BlacksmithActivity extends BaseActivity implements BlacksmithContract.IBlacksmithView {
    private RecyclerView rlv;
    private BlacksmithAdapter adapter;
    private BlacksmithPresenter presenter;

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
        rlv = findViewById(R.id.rlv);

        presenter = new BlacksmithPresenter();
        presenter.subscribe(this);

        adapter = new BlacksmithAdapter(presenter);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.setAdapter(adapter);

        presenter.initData();
    }

    @Override
    public void showData(List<BlacksmithModelVM> data) {
        adapter.setData(data);
    }
}
