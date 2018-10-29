package com.mdove.levelgame.main.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.mdove.levelgame.base.BaseListActivity;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.main.shop.adapter.BusinessmanAdapter;
import com.mdove.levelgame.main.shop.model.mv.SellGoodsModelVM;
import com.mdove.levelgame.main.shop.presenter.BusinessmanContract;
import com.mdove.levelgame.main.shop.presenter.BusinessmanPresenter;

import java.util.List;

/**
 * @author MDove on 2018/10/29
 */
public class BusinessmanActivity extends BaseListActivity<SellGoodsModelVM> implements BusinessmanContract.IBusinessmanView {
    private static final String EXTRA_INTENT_MONSTERS_ID = "extra_intent_monsters_id";
    private BusinessmanPresenter presenter;
    private long monstersId;

    public static void start(Context context, Long monstersId) {
        Intent start = new Intent(context, BusinessmanActivity.class);
        if (!(context instanceof Activity)) {
            start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        start.putExtra(EXTRA_INTENT_MONSTERS_ID, monstersId);
        context.startActivity(start);
    }

    @Override
    public BaseListAdapter<SellGoodsModelVM> createAdapter() {
        return new BusinessmanAdapter(presenter);
    }

    @Override
    public void loadData() {
        presenter.initData(monstersId);
    }

    @Override
    public void initData(Intent intent) {
        monstersId = intent.getLongExtra(EXTRA_INTENT_MONSTERS_ID, -1);
        presenter = new BusinessmanPresenter();
        presenter.subscribe(this);
    }

    @Override
    public void showData(List<SellGoodsModelVM> data) {
        getAdapter().setData(data);
    }
}
