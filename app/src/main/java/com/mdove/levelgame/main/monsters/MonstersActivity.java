package com.mdove.levelgame.main.monsters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.main.monsters.adapter.MonstersAdapter;
import com.mdove.levelgame.main.monsters.model.MonstersModel;
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;
import com.mdove.levelgame.main.monsters.presenter.MonstersConstract;
import com.mdove.levelgame.main.monsters.presenter.MonstersPresenter;

import java.util.List;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersActivity extends BaseActivity implements MonstersConstract.IMonstersView {
    private static final String EXTRA_MONSTERS_PLACE_ID = "extra_monsters_place_id";
    private static final String EXTRA_MONSTERS_PLACE_NAME = "extra_monsters_place_name";
    private RecyclerView rlv;
    private TextView tvPower;
    private TextView btnRest;
    private MonstersPresenter presenter;
    private MonstersAdapter adapter;
    private long monsterPlaceId;
    private String title;

    public static void start(Context context, long monsterPlaceId, String title) {
        Intent start = new Intent(context, MonstersActivity.class);
        if (!(context instanceof Activity)) {
            start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        start.putExtra(EXTRA_MONSTERS_PLACE_ID, monsterPlaceId);
        start.putExtra(EXTRA_MONSTERS_PLACE_NAME, title);
        context.startActivity(start);
    }

    @Override
    protected boolean isNeedCustomLayout() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monsters);
        monsterPlaceId = getIntent().getLongExtra(EXTRA_MONSTERS_PLACE_ID, -1);
        title = getIntent().getStringExtra(EXTRA_MONSTERS_PLACE_NAME);
        setTitle(title);

        presenter = new MonstersPresenter();
        presenter.subscribe(this);

        adapter = new MonstersAdapter(this, presenter);

        rlv = findViewById(R.id.rlv);
        tvPower = findViewById(R.id.tv_power);
        btnRest = findViewById(R.id.btn_rest);
        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.heroRest();
            }
        });
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.setAdapter(adapter);

        presenter.initData(monsterPlaceId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.initPower();
        }
    }

    @Override
    public void attackUI(int index) {
        adapter.notifyAttackUI(index);
    }

    @Override
    public void showPowerText(String content) {
        tvPower.setText(content);
    }

    @Override
    public void showData(List<MonstersModelVM> data) {
        adapter.setData(data);
    }
}
