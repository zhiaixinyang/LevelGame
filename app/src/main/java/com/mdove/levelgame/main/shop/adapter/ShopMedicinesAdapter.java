package com.mdove.levelgame.main.shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.databinding.ItemMedicinesBinding;
import com.mdove.levelgame.main.shop.model.handler.MedicinesItemHandler;
import com.mdove.levelgame.main.shop.model.mv.MedicinesModelVM;
import com.mdove.levelgame.main.shop.presenter.ShopMedicinesPresenter;
import com.mdove.levelgame.utils.InflateUtils;

/**
 * @author MDove on 2018/10/30
 */
public class ShopMedicinesAdapter extends BaseListAdapter<MedicinesModelVM> {
    private ShopMedicinesPresenter presenter;

    public ShopMedicinesAdapter(ShopMedicinesPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ItemMedicinesBinding) InflateUtils.bindingInflate(parent, R.layout.item_medicines));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ShopMedicinesAdapter.ViewHolder) holder).bind(getData().get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMedicinesBinding binding;

        public ViewHolder(ItemMedicinesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MedicinesModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new MedicinesItemHandler(presenter));
        }
    }
}
