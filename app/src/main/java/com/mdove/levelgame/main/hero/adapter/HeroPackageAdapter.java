package com.mdove.levelgame.main.hero.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.databinding.ItemHeroPackageBinding;
import com.mdove.levelgame.databinding.ItemHeroPackageTitleBinding;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.main.hero.model.handler.HeroPackageHandler;
import com.mdove.levelgame.main.hero.presenter.HeroPackagePresenter;
import com.mdove.levelgame.main.hero.presenter.HeroPackagesPresenter;
import com.mdove.levelgame.utils.InflateUtils;

import java.util.List;

/**
 * @author MDove on 2018/10/23
 */
public class HeroPackageAdapter extends BaseListAdapter<HeroPackageModelVM> {
    private HeroPackagePresenter presenter;

    public HeroPackageAdapter(HeroPackagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public int getItemViewType(int position) {
        if (getData() == null) {
            return super.getItemViewType(position);
        }
        if (getData().get(position).pkId.get() == -3) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new TitleViewHolder((ItemHeroPackageTitleBinding) InflateUtils.bindingInflate(parent, R.layout.item_hero_package_title));
        } else if (viewType == 2) {
            return new ViewHolder((ItemHeroPackageBinding) InflateUtils.bindingInflate(parent, R.layout.item_hero_package));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bind(getData().get(position));
        }
    }

    private void deleteByPosition(int position) {
        getData().remove(position);
        notifyItemRemoved(position);
    }

    public void deleteByType(String type) {
        int position = -1;
        for (HeroPackageModelVM vm : getData()) {
            if (TextUtils.equals(type, vm.type.get())) {
                position = getData().indexOf(vm);
                break;
            }
        }
        if (position != -1) {
            deleteByPosition(position);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        public TitleViewHolder(ItemHeroPackageTitleBinding binding) {
            super(binding.getRoot());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHeroPackageBinding binding;

        public ViewHolder(ItemHeroPackageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(HeroPackageModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new HeroPackageHandler(presenter));
        }
    }
}
