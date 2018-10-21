package com.mdove.levelgame.main.monsters.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.databinding.ItemMonstersPlaceBinding;
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
    private List<MonstersPlaceModel> data;

    public MonstersPlaceAdapter(Context context, MonstersPlacePresenter placePresenter) {
        this.context = context;
        this.placePresenter = placePresenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ItemMonstersPlaceBinding) InflateUtils.bindingInflate(parent, R.layout.item_monsters_place));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<MonstersPlaceModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMonstersPlaceBinding binding;

        public ViewHolder(ItemMonstersPlaceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MonstersPlaceModel model) {
            binding.setVm(new MonstersPlaceModelVM(model));
            binding.setHandler(new MonstersPlaceItemHandler(placePresenter));
        }
    }
}
