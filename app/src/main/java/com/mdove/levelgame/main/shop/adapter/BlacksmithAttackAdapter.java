package com.mdove.levelgame.main.shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseAdapter;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.databinding.ItemBlacksmithAttackBinding;
import com.mdove.levelgame.databinding.ItemBlacksmithBinding;
import com.mdove.levelgame.main.shop.model.handler.BlacksmithAttackItemHandler;
import com.mdove.levelgame.main.shop.model.handler.BlacksmithItemHandler;
import com.mdove.levelgame.main.shop.model.mv.BlacksmithModelVM;
import com.mdove.levelgame.main.shop.presenter.BlacksmithAttackPresenter;
import com.mdove.levelgame.main.shop.presenter.BlacksmithPresenter;
import com.mdove.levelgame.utils.InflateUtils;


/**
 * Created by MDove on 2018/10/30.
 */

public class BlacksmithAttackAdapter extends BaseListAdapter<BlacksmithModelVM> {
    private BlacksmithAttackPresenter presenter;

    public BlacksmithAttackAdapter(BlacksmithAttackPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ItemBlacksmithAttackBinding) InflateUtils.bindingInflate(parent, R.layout.item_blacksmith_attack));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getData().get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBlacksmithAttackBinding binding;

        public ViewHolder(ItemBlacksmithAttackBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BlacksmithModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new BlacksmithAttackItemHandler(presenter));
        }
    }
}
