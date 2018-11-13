package com.mdove.levelgame.main.shop.fragment;

import android.os.Bundle;

import com.mdove.levelgame.base.BaseListFragment;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.main.shop.adapter.ShopAttackAdapter;
import com.mdove.levelgame.main.shop.adapter.ShopMedicinesAdapter;
import com.mdove.levelgame.main.shop.model.mv.MedicinesModelVM;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;
import com.mdove.levelgame.main.shop.presenter.ShopAttackContract;
import com.mdove.levelgame.main.shop.presenter.ShopAttackPresenter;
import com.mdove.levelgame.main.shop.presenter.ShopMedicinesContract;
import com.mdove.levelgame.main.shop.presenter.ShopMedicinesPresenter;

import java.util.List;

/**
 * Created by MDove on 2018/10/30.
 */
public class ShopMedicinesFragment extends BaseListFragment implements ShopMedicinesContract.IShopMedicinesView {
    private ShopMedicinesPresenter presenter;

    public static ShopMedicinesFragment newInstance() {
        Bundle args = new Bundle();

        ShopMedicinesFragment fragment = new ShopMedicinesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        presenter = new ShopMedicinesPresenter();
        presenter.subscribe(this);
    }

    @Override
    public void loadData() {
        presenter.initData();
    }

    @Override
    public BaseListAdapter createAdapter() {
        return new ShopMedicinesAdapter(presenter);
    }


    @Override
    public void showData(List<MedicinesModelVM> data) {
        getAdapter().setData(data);
    }

    @Override
    public void notifyUI(int position) {
        getAdapter().notifyItemChanged(position);
    }
}
