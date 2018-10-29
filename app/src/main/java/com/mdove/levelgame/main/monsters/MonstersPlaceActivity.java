package com.mdove.levelgame.main.monsters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.greendao.entity.MonstersPlace;
import com.mdove.levelgame.main.monsters.adapter.MonstersPlaceAdapter;
import com.mdove.levelgame.main.monsters.model.MonstersPlaceModel;
import com.mdove.levelgame.main.monsters.presenter.MonstersPlaceContract;
import com.mdove.levelgame.main.monsters.presenter.MonstersPlacePresenter;

import java.util.List;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersPlaceActivity extends BaseActivity implements MonstersPlaceContract.IMonstersPlaceView {
    private RecyclerView rlv;
    private MonstersPlaceAdapter adapter;
    private MonstersPlacePresenter presenter;

    public static void start(Context context){
        Intent start = new Intent(context, MonstersPlaceActivity.class);
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
        setTitle(R.string.activity_title_monsters_place);
        setContentView(R.layout.activity_monsters_place);
        rlv = findViewById(R.id.rlv);

        presenter = new MonstersPlacePresenter();
        presenter.subscribe(this);

        adapter = new MonstersPlaceAdapter(this, presenter);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.setAdapter(adapter);

        presenter.initData();
    }

    @Override
    public void showData(List<MonstersPlace> data) {
        adapter.setData(data);
    }

}
