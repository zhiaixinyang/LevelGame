package com.mdove.levelgame.main.hero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.main.hero.adapter.HasEquipAdapter;
import com.mdove.levelgame.main.hero.adapter.HeroPackageAdapter;
import com.mdove.levelgame.main.hero.model.HasEquipModelVM;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.main.hero.presenter.HeroPackagesContract;
import com.mdove.levelgame.main.hero.presenter.HeroPackagesPresenter;

import java.util.List;

/**
 * @author MDove on 2018/10/23
 */
public class HeroPackagesActivity extends BaseActivity implements HeroPackagesContract.IHeroPackagesView {
    private RecyclerView rlvEquip, rlvPackages;
    private HasEquipAdapter equipAdapter;
    private HeroPackageAdapter heroPackageAdapter;
    private HeroPackagesPresenter presenter;

    public static void start(Context context) {
        Intent start = new Intent(context, HeroPackagesActivity.class);
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
        setTitle(getString(R.string.string_packages));
        setContentView(R.layout.activity_hero_packages);
        rlvEquip = findViewById(R.id.rlv_equip);
        rlvPackages = findViewById(R.id.rlv_packages);

        presenter = new HeroPackagesPresenter();
        presenter.subscribe(this);

        equipAdapter = new HasEquipAdapter(presenter);
        heroPackageAdapter = new HeroPackageAdapter(presenter);

        rlvEquip.setLayoutManager(new LinearLayoutManager(this));
        rlvEquip.setAdapter(equipAdapter);
        rlvPackages.setLayoutManager(new LinearLayoutManager(this));
        rlvPackages.setAdapter(heroPackageAdapter);

        presenter.initData();
    }

    @Override
    public void showEquipData(List<HasEquipModelVM> data) {
        equipAdapter.setData(data);
    }

    @Override
    public void showPackage(List<HeroPackageModelVM> data) {
        heroPackageAdapter.setData(data);
    }

    @Override
    public void deleteUIByType(String type) {
        heroPackageAdapter.deleteByType(type);
    }
}
