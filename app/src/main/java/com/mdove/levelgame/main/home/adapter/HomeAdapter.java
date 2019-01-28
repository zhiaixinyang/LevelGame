package com.mdove.levelgame.main.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.databinding.ItemCityBtnBinding;
import com.mdove.levelgame.databinding.ItemCityBtnTopBinding;
import com.mdove.levelgame.main.home.model.MainMenuHandler;
import com.mdove.levelgame.main.home.model.vm.BaseMainMenuVM;
import com.mdove.levelgame.main.home.model.vm.MainMenuModelVM;
import com.mdove.levelgame.main.home.model.vm.TopMainMenuModelVM;
import com.mdove.levelgame.main.home.presenter.HomePresenter;
import com.mdove.levelgame.utils.InflateUtils;

/**
 * @author MDove on 2018/10/31
 */
public class HomeAdapter extends BaseListAdapter<BaseMainMenuVM> {
    private HomePresenter presenter;

    public HomeAdapter(HomePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public int getItemViewType(int position) {
        if (getData().get(position) instanceof MainMenuModelVM) {
            return 1;
        } else if (getData().get(position) instanceof TopMainMenuModelVM) {
            return 0;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new TopViewHolder(InflateUtils.bindingInflate(parent, R.layout.item_city_btn_top));
        } else {
            return new ViewHolder(InflateUtils.bindingInflate(parent, R.layout.item_city_btn));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder) {
            ((TopViewHolder) holder).bind((TopMainMenuModelVM) getData().get(position));
        } else {
            ((ViewHolder) holder).bind((MainMenuModelVM) getData().get(position));
        }
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

    public class TopViewHolder extends RecyclerView.ViewHolder {
        private ItemCityBtnTopBinding binding;

        public TopViewHolder(ItemCityBtnTopBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TopMainMenuModelVM vm) {
            binding.setVmOne(vm.one);
            binding.setVmTwo(vm.two);
            binding.setVmThree(vm.three);
            binding.setHandler(new MainMenuHandler(presenter));
        }
    }
}
