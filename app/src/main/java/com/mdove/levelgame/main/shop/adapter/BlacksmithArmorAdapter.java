package com.mdove.levelgame.main.shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseAdapter;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.databinding.ItemBlacksmithArmorBinding;
import com.mdove.levelgame.databinding.ItemBlacksmithBinding;
import com.mdove.levelgame.main.shop.model.handler.BlacksmithArmorItemHandler;
import com.mdove.levelgame.main.shop.model.handler.BlacksmithItemHandler;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.presenter.BlacksmithArmorPresenter;
import com.mdove.levelgame.main.shop.presenter.BlacksmithPresenter;
import com.mdove.levelgame.utils.InflateUtils;

import java.util.List;


/**
 * Created by MDove on 2018/10/30.
 */

public class BlacksmithArmorAdapter extends BaseListAdapter<BlacksmithModelVM> {
    private BlacksmithArmorPresenter presenter;

    public BlacksmithArmorAdapter(BlacksmithArmorPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ItemBlacksmithArmorBinding) InflateUtils.bindingInflate(parent, R.layout.item_blacksmith_armor));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getData().get(position));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBlacksmithArmorBinding binding;

        public ViewHolder(ItemBlacksmithArmorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BlacksmithModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new BlacksmithArmorItemHandler(presenter));
        }
    }
}
