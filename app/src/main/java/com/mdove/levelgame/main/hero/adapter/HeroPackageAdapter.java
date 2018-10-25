package com.mdove.levelgame.main.hero.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.databinding.ItemHeroPackageBinding;
import com.mdove.levelgame.main.hero.model.HeroPackageModelVM;
import com.mdove.levelgame.main.hero.model.handler.HeroPackageHandler;
import com.mdove.levelgame.main.hero.presenter.HeroPackagesPresenter;
import com.mdove.levelgame.utils.InflateUtils;

import java.util.List;

/**
 * @author MDove on 2018/10/23
 */
public class HeroPackageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HeroPackageModelVM> data;
    private HeroPackagesPresenter presenter;

    public HeroPackageAdapter(HeroPackagesPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ItemHeroPackageBinding) InflateUtils.bindingInflate(parent, R.layout.item_hero_package));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<HeroPackageModelVM> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private void deleteByPosition(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void deleteByType(String type) {
        int position = -1;
        for (HeroPackageModelVM vm : data) {
            if (TextUtils.equals(type, vm.type.get())) {
                position = data.indexOf(vm);
                break;
            }
        }
        if (position != -1) {
            deleteByPosition(position);
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
