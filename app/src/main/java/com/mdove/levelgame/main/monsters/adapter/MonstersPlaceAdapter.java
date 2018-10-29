package com.mdove.levelgame.main.monsters.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.databinding.ItemMonstersPlaceAdventureBinding;
import com.mdove.levelgame.databinding.ItemMonstersPlaceBinding;
import com.mdove.levelgame.greendao.entity.MonstersPlace;
import com.mdove.levelgame.main.monsters.model.MonstersPlaceModel;
import com.mdove.levelgame.main.monsters.model.handler.MonstersPlaceItemHandler;
import com.mdove.levelgame.main.monsters.model.vm.MonstersPlaceModelVM;
import com.mdove.levelgame.main.monsters.presenter.MonstersPlacePresenter;
import com.mdove.levelgame.utils.InflateUtils;

import java.util.List;

/**
 * Created by MBENBEN on 2018/10/21.
 */

public class MonstersPlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private MonstersPlacePresenter placePresenter;
    private List<MonstersPlace> data;

    public MonstersPlaceAdapter(Context context, MonstersPlacePresenter placePresenter) {
        this.context = context;
        this.placePresenter = placePresenter;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).isAdventure == 0) {
            // 奇遇layout
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new AdventureViewHolder((ItemMonstersPlaceAdventureBinding) InflateUtils.bindingInflate(parent, R.layout.item_monsters_place_adventure));
        } else {
            return new ViewHolder((ItemMonstersPlaceBinding) InflateUtils.bindingInflate(parent, R.layout.item_monsters_place));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<MonstersPlace> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMonstersPlaceBinding binding;

        public ViewHolder(ItemMonstersPlaceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MonstersPlace model) {
            binding.setVm(new MonstersPlaceModelVM(model));
            binding.setHandler(new MonstersPlaceItemHandler(placePresenter));
        }
    }

    public class AdventureViewHolder extends RecyclerView.ViewHolder {
        private ItemMonstersPlaceAdventureBinding binding;

        public AdventureViewHolder(ItemMonstersPlaceAdventureBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MonstersPlace model) {
            binding.setVm(new MonstersPlaceModelVM(model));
            binding.setHandler(new MonstersPlaceItemHandler(placePresenter));
        }
    }
}
