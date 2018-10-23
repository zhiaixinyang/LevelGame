package com.mdove.levelgame.main.hero.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.databinding.ItemHeroEquipBinding;
import com.mdove.levelgame.main.hero.model.HasEquipModelVM;
import com.mdove.levelgame.main.hero.model.handler.HasEquipHandler;
import com.mdove.levelgame.main.hero.presenter.HeroPackagesPresenter;
import com.mdove.levelgame.utils.InflateUtils;

import java.util.List;

/**
 * @author MDove on 2018/10/23
 */
public class HasEquipAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HasEquipModelVM> data;
    private HeroPackagesPresenter packagesPresenter;

    public HasEquipAdapter(HeroPackagesPresenter packagesPresenter) {
        this.packagesPresenter = packagesPresenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ItemHeroEquipBinding) InflateUtils.bindingInflate(parent, R.layout.item_hero_equip));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<HasEquipModelVM> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private int getPositionForId(long id) {
        int position = -1;
        for (HasEquipModelVM model : data) {
            if (model.id.get() == id) {
                position = data.indexOf(model);
            }
        }
        return position;
    }

    public void deleteNotifyPosition(long id) {
        int position = getPositionForId(id);
        if (position != -1) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHeroEquipBinding binding;

        public ViewHolder(ItemHeroEquipBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(HasEquipModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new HasEquipHandler(packagesPresenter));
        }
    }
}
