package com.mdove.levelgame.main.shop.fragment;

import android.os.Bundle;

import com.mdove.levelgame.base.BaseListFragment;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.main.shop.adapter.ShopArmorAdapter;
import com.mdove.levelgame.main.shop.adapter.ShopAttackAdapter;
import com.mdove.levelgame.main.shop.model.mv.ShopArmorModelVM;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;
import com.mdove.levelgame.main.shop.presenter.ShopArmorContract;
import com.mdove.levelgame.main.shop.presenter.ShopArmorPresenter;
import com.mdove.levelgame.main.shop.presenter.ShopAttackContract;
import com.mdove.levelgame.main.shop.presenter.ShopAttackPresenter;

import java.util.List;

public class ShopArmorFragment extends BaseListFragment implements ShopArmorContract.IShopArmorView {
    private ShopArmorPresenter presenter;

    public static ShopArmorFragment newInstance() {
        Bundle args = new Bundle();
        
        ShopArmorFragment fragment = new ShopArmorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        presenter = new ShopArmorPresenter();
        presenter.subscribe(this);

        presenter.initData();
    }

    @Override
    public BaseListAdapter createAdapter() {
        return new ShopArmorAdapter(presenter);
    }


    @Override
    public void showData(List<ShopArmorModelVM> data) {
        getAdapter().setData(data);
    }
}
