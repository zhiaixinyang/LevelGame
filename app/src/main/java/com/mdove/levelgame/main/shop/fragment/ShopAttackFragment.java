package com.mdove.levelgame.main.shop.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.mdove.levelgame.base.BaseListFragment;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.main.shop.adapter.ShopAttackAdapter;
import com.mdove.levelgame.main.shop.model.mv.ShopArmorModelVM;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;
import com.mdove.levelgame.main.shop.presenter.ShopAttackContract;
import com.mdove.levelgame.main.shop.presenter.ShopAttackPresenter;

import java.util.List;

public class ShopAttackFragment extends BaseListFragment implements ShopAttackContract.IShopAttackView {
    private ShopAttackPresenter presenter;

    @Override
    public void initData() {
        presenter = new ShopAttackPresenter();
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
