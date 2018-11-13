package com.mdove.levelgame.main.hero.fragment;

import android.os.Bundle;

import com.mdove.levelgame.base.BaseListFragment;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.main.hero.HeroPackagesActivity;
import com.mdove.levelgame.main.hero.adapter.HeroPackageAdapter;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.main.hero.presenter.HeroPackageContract;
import com.mdove.levelgame.main.hero.presenter.HeroPackagePresenter;

import java.util.List;

/**
 * @author MDove on 2018/10/30
 */
public class HeroPackageFragment extends BaseListFragment implements HeroPackageContract.IHeroPackageView {
    private HeroPackagePresenter presenter;


    public static HeroPackageFragment newInstance() {
        Bundle args = new Bundle();
        HeroPackageFragment fragment = new HeroPackageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        presenter = new HeroPackagePresenter();
        presenter.subscribe(this);
    }

    @Override
    public void loadData() {
        presenter.initData();
    }

    @Override
    public BaseListAdapter<HeroPackageModelVM> createAdapter() {
        return new HeroPackageAdapter(presenter);
    }

    @Override
    public void showPackage(List<HeroPackageModelVM> data) {
        getAdapter().setData(data);
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
    public void addByPosition(int position) {
        getAdapter().addFromPosition(position);
    }

    @Override
    public void notifyEquipUpdateUI(int position) {
        HeroPackagesActivity activity = null;
        if (getActivity() instanceof HeroPackagesActivity) {
            activity = (HeroPackagesActivity) getActivity();
            if (activity.isFinishing()) {
                return;
            }
            activity.notifyEquipUpdateUI(position);
        }
    }

    @Override
    public void notifyPackageAddUI(long pkId) {
        presenter.notifyPackageAddUI(pkId);
    }
}
