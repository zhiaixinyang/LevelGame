package com.mdove.levelgame.main.shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseAdapter;
import com.mdove.levelgame.databinding.ItemBlacksmithBinding;
import com.mdove.levelgame.main.shop.model.handler.BlacksmithItemHandler;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.presenter.BlacksmithPresenter;
import com.mdove.levelgame.utils.InflateUtils;


/**
 * Created by MDove on 2018/10/28.
 */

public class BlacksmithAdapter extends BaseAdapter<BlacksmithModelVM> {
    private BlacksmithPresenter presenter;

    public BlacksmithAdapter(BlacksmithPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ItemBlacksmithBinding) InflateUtils.bindingInflate(parent, R.layout.item_blacksmith));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(data.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBlacksmithBinding binding;

        public ViewHolder(ItemBlacksmithBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BlacksmithModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new BlacksmithItemHandler(presenter));
        }
    }
}
