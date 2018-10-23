package com.mdove.levelgame.main.shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.databinding.ItemShopAttackBinding;
import com.mdove.levelgame.main.shop.model.handler.ShopAttackItemHandler;
import com.mdove.levelgame.main.shop.model.mv.ShopAttackModelVM;
import com.mdove.levelgame.main.shop.presenter.ShopAttackPresenter;
import com.mdove.levelgame.utils.InflateUtils;

/**
 * @author MDove on 2018/10/22
 */
public class ShopAttackAdapter extends BaseListAdapter<ShopAttackModelVM> {
    private ShopAttackPresenter presenter;

    public ShopAttackAdapter(ShopAttackPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ItemShopAttackBinding) InflateUtils.bindingInflate(parent, R.layout.item_shop_attack));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(getData().get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemShopAttackBinding binding;

        public ViewHolder(ItemShopAttackBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ShopAttackModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new ShopAttackItemHandler(presenter));
        }
    }
}
