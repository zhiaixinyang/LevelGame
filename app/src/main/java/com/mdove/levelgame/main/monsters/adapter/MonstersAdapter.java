package com.mdove.levelgame.main.monsters.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.databinding.ItemMonstersBinding;
import com.mdove.levelgame.main.monsters.model.MonstersModel;
import com.mdove.levelgame.main.monsters.model.handler.MonstersItemHandler;
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;
import com.mdove.levelgame.main.monsters.presenter.MonstersPresenter;
import com.mdove.levelgame.utils.InflateUtils;

import java.util.List;

/**
 * Created by MBENBEN on 2018/10/21.
 */

public class MonstersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private MonstersPresenter placePresenter;
    private List<MonstersModelVM> data;

    public MonstersAdapter(Context context, MonstersPresenter placePresenter) {
        this.context = context;
        this.placePresenter = placePresenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ItemMonstersBinding) InflateUtils.bindingInflate(parent, R.layout.item_monsters));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<MonstersModelVM> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void notifyAttackUI(int index) {
        notifyItemChanged(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMonstersBinding binding;

        public ViewHolder(ItemMonstersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MonstersModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new MonstersItemHandler(placePresenter));
        }
    }
}
