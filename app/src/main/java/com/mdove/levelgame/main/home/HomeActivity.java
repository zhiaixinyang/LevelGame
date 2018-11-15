package com.mdove.levelgame.main.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.mdove.levelgame.BuildConfig;
import com.mdove.levelgame.R;
import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.databinding.ActivityHomeBinding;
import com.mdove.levelgame.main.hero.manager.HeroAttributesManager;
import com.mdove.levelgame.main.hero.manager.HeroManager;
import com.mdove.levelgame.main.home.adapter.HomeAdapter;
import com.mdove.levelgame.main.home.model.BigMonstersModelVM;
import com.mdove.levelgame.main.home.model.HomeActionHandler;
import com.mdove.levelgame.main.home.model.MainActionHandler;
import com.mdove.levelgame.main.home.model.MainMenuModelVM;
import com.mdove.levelgame.main.home.presenter.HomeContract;
import com.mdove.levelgame.main.home.presenter.HomePresenter;
import com.mdove.levelgame.utils.AppUtils;
import com.mdove.levelgame.utils.DensityUtil;
import com.mdove.levelgame.utils.ToastHelper;
import com.mdove.levelgame.view.guideview.Guide;
import com.mdove.levelgame.view.guideview.GuideBuilder;
import com.mdove.levelgame.view.guideview.component.CommonComponent;

import java.util.List;

/**
 * @author MDove on 2018/10/31
 */
public class HomeActivity extends BaseActivity implements HomeContract.IHomeView {
    private HomePresenter presenter;
    private HomeAdapter adapter;
    private ActivityHomeBinding binding;
    private boolean isAniming = false;
    private long clickTime = 0;

    public static void start(Context context) {
        Intent start = new Intent(context, HomeActivity.class);
        if (!(context instanceof Activity)) {
            start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        presenter.initGuide();
        presenter.checkUpdate(AppUtils.getAPPVersionCodeFromAPP(this));

        binding.setHandler(new HomeActionHandler(presenter));

        binding.ivBigMonster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BuildConfig.DEBUG) {
                    HeroManager.getInstance().getHeroAttributes().money += 1000;
                    HeroManager.getInstance().save();
                }
            }
        });
        binding.rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && !isAniming) {
                    showView();
                } else if (dy < 0 && !isAniming) {
                    hideView();
                }
            }
        });
    }

    private void showView() {
        ViewCompat.animate(binding.layoutBigMonsters)
                .translationY(binding.layoutBigMonsters.getHeight() +
                        DensityUtil.dip2px(getContext(), getResources().getDimension(R.dimen.main_btn_normal_margin)))
                .setDuration(300)
                .setListener(listener)
                .start();

        ViewCompat.animate(binding.bgLayout)
                .translationY(binding.layoutBigMonsters.getHeight())
                .setDuration(300)
                .setListener(listener)
                .start();
    }

    private void hideView() {
        ViewCompat.animate(binding.layoutBigMonsters)
                .translationY(0)
                .setDuration(300)
                .setListener(listener)
                .start();
        ViewCompat.animate(binding.bgLayout)
                .translationY(0)
                .setDuration(300)
                .setListener(listener)
                .start();
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
            binding.layoutBigMonsters.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showBigMonsterInvade(String days) {
        binding.tvInvade.setText(Html.fromHtml(days));
    }

    @Override
    public void showGuide() {
        final GuideBuilder optionBuilder = new GuideBuilder();
        optionBuilder.addComponent(new CommonComponent(getString(R.string.string_guide_title)));
        optionBuilder.setTargetView(binding.btnSetting)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
        //Guide必须要在GuideBuilder之后初始化
        final Guide optionGuide = optionBuilder.createGuide();
        optionGuide.setShouldCheckLocInWindow(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                optionGuide.show(HomeActivity.this);
            }
        }, 100);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 1000) {
            ToastHelper.shortToast(getString(R.string.string_exit_game));
            clickTime = System.currentTimeMillis();
        } else {
            this.finish();
        }

    }

    private ViewPropertyAnimatorListener listener = new ViewPropertyAnimatorListener() {
        @Override
        public void onAnimationStart(View view) {
            isAniming = true;
        }

        @Override
        public void onAnimationEnd(View view) {
            isAniming = false;
        }

        @Override
        public void onAnimationCancel(View view) {
            isAniming = false;
        }
    };
}
