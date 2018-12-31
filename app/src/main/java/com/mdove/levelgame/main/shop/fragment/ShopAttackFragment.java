package com.mdove.levelgame.main.shop.fragment;

import android.os.Bundle;

import com.mdove.levelgame.base.BaseListFragment;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.main.shop.adapter.ShopAttackAdapter;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;
import com.mdove.levelgame.main.shop.presenter.contract.ShopAttackContract;
import com.mdove.levelgame.main.shop.presenter.ShopAttackPresenter;

import java.util.List;

/**
 * Created by MDove on 2018/10/30.
 */
public class ShopAttackFragment extends BaseListFragment implements ShopAttackContract.IShopAttackView {
    private ShopAttackPresenter presenter;

    public static ShopAttackFragment newInstance() {
        Bundle args = new Bundle();
        
        ShopAttackFragment fragment = new ShopAttackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        presenter = new ShopAttackPresenter();
        presenter.subscribe(this);
    }

    @Override
    public void loadData() {
        presenter.initData();
    }

    @Override
    public BaseListAdapter createAdapter() {
        return new ShopAttackAdapter(presenter);
    }


    @Override
    public void showData(List<ShopAttackModelVM> data) {
        getAdapter().setData(data);
    }
}
