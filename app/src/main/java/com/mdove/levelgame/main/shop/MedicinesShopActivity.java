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
import com.mdove.levelgame.main.monsters.MonstersActivity;
import com.mdove.levelgame.main.shop.adapter.MedicinesShopAdapter;
import com.mdove.levelgame.main.shop.model.MedicinesModelVM;
import com.mdove.levelgame.main.shop.presenter.MedicinesShopContract;
import com.mdove.levelgame.main.shop.presenter.MedicinesShopPresenter;

import java.util.List;

/**
 * Created by MBENBEN on 2018/10/21.
 */

public class MedicinesShopActivity extends BaseActivity implements MedicinesShopContract.IMedicinesShopView {
    private RecyclerView rlv;
    private MedicinesShopPresenter presenter;
    private MedicinesShopAdapter adapter;

    public static void start(Context context) {
        Intent start = new Intent(context, MedicinesShopActivity.class);
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
        setTitle(R.string.activity_title_medicines_shop);
        setContentView(R.layout.activity_medicines_shop);

        presenter = new MedicinesShopPresenter();
        presenter.subscribe(this);

        rlv = findViewById(R.id.rlv);
        adapter = new MedicinesShopAdapter(this, presenter);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.setAdapter(adapter);

        presenter.initData();
    }

    @Override
    public void showData(List<MedicinesModelVM> data) {
        adapter.setData(data);
    }
}
