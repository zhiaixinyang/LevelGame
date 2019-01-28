package com.mdove.levelgame.main.hero.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.databinding.ItemHeroPackageBinding;
import com.mdove.levelgame.databinding.ItemHeroPackageEmptyBinding;
import com.mdove.levelgame.databinding.ItemHeroPackageMaterialsBinding;
import com.mdove.levelgame.databinding.ItemHeroPackageTitleBinding;
import com.mdove.levelgame.main.hero.model.BasePackageModelVM;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.main.hero.model.HeroPkEmptyModelVM;
import com.mdove.levelgame.main.hero.model.handler.HeroPackageHandler;
import com.mdove.levelgame.main.hero.presenter.HeroPackagePresenter;
import com.mdove.levelgame.utils.InflateUtils;

import java.util.List;

/**
 * @author MDove on 2018/10/23
 */
public class HeroPackageAdapter extends BaseListAdapter<BasePackageModelVM> {
    private HeroPackagePresenter presenter;

    public HeroPackageAdapter(HeroPackagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public int getItemViewType(int position) {
        if (getData() == null) {
            return super.getItemViewType(position);
        }
        BasePackageModelVM vm = getData().get(position);
        if (vm instanceof HeroPackageModelVM && ((HeroPackageModelVM) vm).pkId.get() == -3) {
            return 1;
        } else if (vm instanceof HeroPackageModelVM && ((HeroPackageModelVM) vm).isMaterials.get()) {
            return 3;
        } else if (vm instanceof HeroPkEmptyModelVM) {
            return 4;
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
        } else if (viewType == 3) {
            return new MaterialViewHolder(InflateUtils.bindingInflate(parent, R.layout.item_hero_package_materials));
        } else if (viewType == 4) {
            return new EmptyViewHolder(InflateUtils.bindingInflate(parent, R.layout.item_hero_package_empty));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bind((HeroPackageModelVM) getData().get(position));
        } else if (holder instanceof MaterialViewHolder) {
            ((MaterialViewHolder) holder).bind((HeroPackageModelVM) getData().get(position));
        } else if (holder instanceof EmptyViewHolder) {
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
            HeroPackageHandler handler = new HeroPackageHandler(presenter);
            binding.getRoot().setOnLongClickListener(v -> {
                handler.onLongClick(vm.pkId.get());
                return false;
            });
            binding.setHandler(handler);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(ItemHeroPackageEmptyBinding binding) {
            super(binding.getRoot());
        }
    }

    public class MaterialViewHolder extends RecyclerView.ViewHolder {
        private ItemHeroPackageMaterialsBinding binding;

        public MaterialViewHolder(ItemHeroPackageMaterialsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(HeroPackageModelVM vm) {
            binding.setVm(vm);
            HeroPackageHandler handler = new HeroPackageHandler(presenter);
            binding.getRoot().setOnLongClickListener(v -> {
                handler.onLongClick(vm.pkId.get());
                return false;
            });
            binding.setHandler(handler);
        }
    }
}
