package com.mdove.levelgame.main.shop.fragment;

import android.os.Bundle;

import com.mdove.levelgame.base.BaseListFragment;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.main.shop.adapter.BlacksmithArmorAdapter;
import com.mdove.levelgame.main.shop.adapter.BlacksmithAttackAdapter;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.presenter.BlacksmithArmorContract;
import com.mdove.levelgame.main.shop.presenter.BlacksmithArmorPresenter;
import com.mdove.levelgame.main.shop.presenter.BlacksmithAttackContract;
import com.mdove.levelgame.main.shop.presenter.BlacksmithAttackPresenter;

import java.util.List;

/**
 * Created by MDove on 2018/10/30.
 */

public class BlacksmithArmorFragment extends BaseListFragment implements BlacksmithArmorContract.IBlacksmithArmorView {
    private BlacksmithArmorPresenter presenter;

    public static BlacksmithArmorFragment newInstance() {
        
        Bundle args = new Bundle();
        
        BlacksmithArmorFragment fragment = new BlacksmithArmorFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void initData() {
        presenter = new BlacksmithArmorPresenter();
        presenter.subscribe(this);
    }

    @Override
    public void loadData() {
        presenter.initData();
    }

    @Override
    public BaseListAdapter createAdapter() {
        return new BlacksmithArmorAdapter(presenter);
    }

    @Override
    public void showData(List<BlacksmithModelVM> data) {
        getAdapter().setData(data);
    }
}
