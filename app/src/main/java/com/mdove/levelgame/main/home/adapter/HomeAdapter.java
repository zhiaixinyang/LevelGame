package com.mdove.levelgame.main.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.databinding.ItemCityBtnBinding;
import com.mdove.levelgame.databinding.ItemMainMenuBinding;
import com.mdove.levelgame.main.home.model.MainMenuHandler;
import com.mdove.levelgame.main.home.model.MainMenuModelVM;
import com.mdove.levelgame.main.home.presenter.HomePresenter;
import com.mdove.levelgame.utils.InflateUtils;

/**
 * @author MDove on 2018/10/31
 */
public class HomeAdapter extends BaseListAdapter<MainMenuModelVM> {
    private HomePresenter presenter;

    public HomeAdapter(HomePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder( InflateUtils.bindingInflate(parent, R.layout.item_city_btn));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(getData().get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCityBtnBinding binding;

        public ViewHolder(ItemCityBtnBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MainMenuModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new MainMenuHandler(presenter));
        }
    }

}
