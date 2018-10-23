package com.mdove.levelgame.main.shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.databinding.ItemShopArmorBinding;
import com.mdove.levelgame.main.shop.model.handler.ShopArmorItemHandler;
import com.mdove.levelgame.main.shop.model.mv.ShopArmorModelVM;
import com.mdove.levelgame.main.shop.presenter.ShopArmorPresenter;
import com.mdove.levelgame.utils.InflateUtils;

/**
 * @author MDove on 2018/10/23
 */
public class ShopArmorAdapter extends BaseListAdapter<ShopArmorModelVM> {
    private ShopArmorPresenter presenter;

    public ShopArmorAdapter(ShopArmorPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ItemShopArmorBinding) InflateUtils.bindingInflate(parent, R.layout.item_shop_armor));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(getData().get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemShopArmorBinding binding;

        public ViewHolder(ItemShopArmorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ShopArmorModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new ShopArmorItemHandler(presenter));
        }
    }
}
