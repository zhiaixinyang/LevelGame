package com.mdove.levelgame.main.hero.fragment;

import android.os.Bundle;

import com.mdove.levelgame.base.BaseListFragment;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.main.hero.HeroPackagesActivity;
import com.mdove.levelgame.main.hero.adapter.HeroEquipAdapter;
import com.mdove.levelgame.main.hero.model.HeroEquipModelVM;
import com.mdove.levelgame.main.hero.presenter.HeroEquipContract;
import com.mdove.levelgame.main.hero.presenter.HeroEquipPresenter;

import java.util.List;

import kotlinx.coroutines.GlobalScope;

/**
 * @author MDove on 2018/10/30
 */
public class HeroEquipFragment extends BaseListFragment implements HeroEquipContract.IHeroEquipView {
    private HeroEquipPresenter presenter;

    public static HeroEquipFragment newInstance() {

        Bundle args = new Bundle();

        HeroEquipFragment fragment = new HeroEquipFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        presenter = new HeroEquipPresenter();
        presenter.subscribe(this);
    }

    @Override
    public void loadData() {
        presenter.initData();
    }

    @Override
    public BaseListAdapter<HeroEquipModelVM> createAdapter() {
        return new HeroEquipAdapter(presenter);
    }

    @Override
    public void showEquipData(List<HeroEquipModelVM> data) {
        getAdapter().setData(data);
    }

    @Override
    public void notifyEquipUpdateUI(int position) {
        presenter.notifyEquipUpdateUI(position);
    }

    @Override
    public void notifyByPosition(int position) {
        getAdapter().notifyItemChanged(position);
    }

    @Override
    public void deleteByPosition(int position) {
        getAdapter().deleteFromPosition(position);
    }

    @Override
    public void notifyPackageAddUI(long pkId) {
        HeroPackagesActivity activity = null;
        if (getActivity() instanceof HeroPackagesActivity) {
            activity = (HeroPackagesActivity) getActivity();
            if (activity.isFinishing()) {
                return;
            }
            activity.notifyPackageAddUI(pkId);
        }
    }
}
