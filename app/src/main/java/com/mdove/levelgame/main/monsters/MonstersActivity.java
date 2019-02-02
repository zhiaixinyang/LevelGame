package com.mdove.levelgame.main.monsters;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.TextView;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.main.hero.fragment.HeroPackageDialogFragment;
import com.mdove.levelgame.main.monsters.adapter.MonstersAdapter;
import com.mdove.levelgame.main.monsters.manager.AdventureManager;
import com.mdove.levelgame.main.monsters.model.MonstersViewModel;
import com.mdove.levelgame.main.monsters.model.vm.BaseMonsterModelVM;
import com.mdove.levelgame.main.monsters.presenter.MonstersConstract;
import com.mdove.levelgame.main.monsters.presenter.MonstersPresenter;
import com.mdove.levelgame.view.HorizontalSmoothProgressBar;
import com.mdove.levelgame.view.MyDialog;

import java.util.List;

/**
 * Created by MDove on 2018/10/21.
 */

public class MonstersActivity extends BaseActivity implements MonstersConstract.IMonstersView {
    private static final String EXTRA_MONSTERS_PLACE_ID = "extra_monsters_place_id";
    private static final String EXTRA_MONSTERS_PLACE_NAME = "extra_monsters_place_name";
    private RecyclerView rlv;
    private TextView tvPower, tvLife;
    private HorizontalSmoothProgressBar horizontalSmoothProgressBar;
    private MonstersPresenter presenter;
    private MonstersAdapter adapter;
    private long monsterPlaceId;
    private String title;
    private MonstersViewModel mViewModel;

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
        presenter.setPlaceId(monsterPlaceId);

        adapter = new MonstersAdapter(presenter);

        rlv = findViewById(R.id.rlv);
        tvPower = findViewById(R.id.tv_power);
        tvLife = findViewById(R.id.tv_life);
        horizontalSmoothProgressBar = findViewById(R.id.pb_hero);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.setAdapter(adapter);

        mViewModel = ViewModelProviders.of(this).get(MonstersViewModel.class);
        mViewModel.loadMonsters(monsterPlaceId);
        mViewModel.getMonstersData().observe(this, monstersModelVMS -> adapter.setData(monstersModelVMS));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 奇遇设置
        boolean isShow = AdventureManager.getInstance().setAdventure();
        if (presenter == null) {
            return;
        }
        presenter.initPower();
        presenter.initMoney();
        presenter.initlife();
        if (isShow) {
            presenter.initData(monsterPlaceId);
        }
    }

    @Override
    protected void onToolbarBack() {
        MyDialog.showMyDialog(MonstersActivity.this, "确认离开？", "你确定要离开："+title+"吗？","取消","确认", false, () -> finish());
    }

    @Override
    protected void onBackPressed(boolean fromKey) {
        MyDialog.showMyDialog(MonstersActivity.this, "确认离开？", "你确定要离开："+title+"吗？","取消","确认", false, () -> finish());
    }

    @Override
    public void attackUI(int index) {
        adapter.notifyAttackUI(index);
    }

    @Override
    public void showPowerText(String content) {
        tvPower.setText(Html.fromHtml(content));
    }

    @Override
    public void showMyPackage() {
        new HeroPackageDialogFragment().show(getSupportFragmentManager(), "");
    }

    @Override
    public void showLifeText(int progress, String content) {
        tvLife.setText(Html.fromHtml(content));
        horizontalSmoothProgressBar.setProgress(progress);
    }

    @Override
    public void showMoneyText(String content) {
    }

    @Override
    public void showData(List<BaseMonsterModelVM> data) {
        adapter.setData(data);
    }
}
