package com.mdove.levelgame.main.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.databinding.ItemMedicinesBinding;
import com.mdove.levelgame.databinding.ItemMonstersBinding;
import com.mdove.levelgame.main.monsters.model.handler.MonstersItemHandler;
import com.mdove.levelgame.main.monsters.model.vm.MonstersModelVM;
import com.mdove.levelgame.main.monsters.presenter.MonstersPresenter;
import com.mdove.levelgame.main.shop.model.MedicinesModelVM;
import com.mdove.levelgame.main.shop.model.handler.MedicinesItemHandler;
import com.mdove.levelgame.main.shop.presenter.MedicinesShopPresenter;
import com.mdove.levelgame.utils.InflateUtils;

import java.util.List;

/**
 * Created by MBENBEN on 2018/10/21.
 */

public class MedicinesShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private MedicinesShopPresenter medicinesShopPresenter;
    private List<MedicinesModelVM> data;

    public MedicinesShopAdapter(Context context, MedicinesShopPresenter medicinesShopPresenter) {
        this.context = context;
        this.medicinesShopPresenter = medicinesShopPresenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ItemMedicinesBinding) InflateUtils.bindingInflate(parent, R.layout.item_medicines));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<MedicinesModelVM> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMedicinesBinding binding;

        public ViewHolder(ItemMedicinesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MedicinesModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new MedicinesItemHandler(medicinesShopPresenter));
        }
    }
}
