package com.mdove.levelgame.main.shop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.databinding.ItemBaseSellGoodsBinding;
import com.mdove.levelgame.main.shop.model.handler.SellGoodsItemHandler;
import com.mdove.levelgame.main.shop.model.mv.SellGoodsModelVM;
import com.mdove.levelgame.main.shop.presenter.BusinessmanPresenter;
import com.mdove.levelgame.utils.InflateUtils;

/**
 * @author MDove on 2018/10/29
 */
public class BusinessmanAdapter extends BaseListAdapter<SellGoodsModelVM> {
    private BusinessmanPresenter presenter;

    public BusinessmanAdapter(BusinessmanPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder((ItemBaseSellGoodsBinding) InflateUtils.bindingInflate(parent, R.layout.item_base_sell_goods));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(getData().get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBaseSellGoodsBinding binding;

        public ViewHolder(ItemBaseSellGoodsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SellGoodsModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new SellGoodsItemHandler(presenter));
        }
    }
}
