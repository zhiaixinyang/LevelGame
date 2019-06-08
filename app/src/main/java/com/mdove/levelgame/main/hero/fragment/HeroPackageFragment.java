package com.mdove.levelgame.main.hero.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mdove.levelgame.base.BaseListFragment;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.main.hero.HeroPackagesActivity;
import com.mdove.levelgame.main.hero.adapter.HeroPackageAdapter;
import com.mdove.levelgame.main.hero.model.BasePackageModelVM;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.main.hero.presenter.HeroPackageContract;
import com.mdove.levelgame.main.hero.presenter.HeroPackagePresenter;
import com.mdove.levelgame.main.hero.viewmodel.HeroPackageViewModel;

import java.util.List;

/**
 * @author MDove on 2018/10/30
 */
public class HeroPackageFragment extends BaseListFragment implements HeroPackageContract.IHeroPackageView {
    private HeroPackagePresenter presenter;
    private HeroPackageViewModel mViewModel;

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
        mViewModel = ViewModelProviders.of(getActivity()).get(HeroPackageViewModel.class);
        mViewModel.getPackageAddId().observe(this, pkiId -> {
            presenter.notifyPackageAddUI(pkiId);
        });
    }

    @Override
    public void loadData() {
        presenter.initData();
    }

    @Override
    public BaseListAdapter<BasePackageModelVM> createAdapter() {
        return new HeroPackageAdapter(presenter);
    }

    @Override
    protected RecyclerView.LayoutManager customLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                BasePackageModelVM vm = (BasePackageModelVM) getAdapter().getData().get(position);
                if (vm instanceof HeroPackageModelVM && ((HeroPackageModelVM) vm).isMaterials.get()) {
                    return 1;
                } else {
                    return 4;
                }
            }
        });
        return gridLayoutManager;
    }

    @Override
    public void showPackage(List<BasePackageModelVM> data) {
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
        } else {
            mViewModel.getEquipPkPosition().setValue(position);
        }
    }

    @Override
    public void notifyPackageAddUI(long pkId) {
        presenter.notifyPackageAddUI(pkId);
    }
}
