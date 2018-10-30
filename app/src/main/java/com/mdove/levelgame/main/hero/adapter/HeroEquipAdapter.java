package com.mdove.levelgame.main.hero.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.databinding.ItemHeroEquipBinding;
import com.mdove.levelgame.databinding.ItemHeroEquipTitleBinding;
import com.mdove.levelgame.main.hero.model.HeroEquipModelVM;
import com.mdove.levelgame.main.hero.model.handler.HasEquipHandler;
import com.mdove.levelgame.main.hero.presenter.HeroEquipPresenter;
import com.mdove.levelgame.utils.InflateUtils;

/**
 * @author MDove on 2018/10/23
 */
public class HeroEquipAdapter extends BaseListAdapter<HeroEquipModelVM> {
    private HeroEquipPresenter presenter;

    public HeroEquipAdapter(HeroEquipPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public int getItemViewType(int position) {
        if (getData() == null) {
            return super.getItemViewType(position);
        }
        if (getData().get(position).id.get() == -2) {
            // 1：title布局
            return 1;
        } else {
            // 2：正经布局
            return 2;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new TitleViewHolder((ItemHeroEquipTitleBinding) InflateUtils.bindingInflate(parent, R.layout.item_hero_equip_title));
        } else if (viewType == 2) {
            return new ViewHolder((ItemHeroEquipBinding) InflateUtils.bindingInflate(parent, R.layout.item_hero_equip));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bind(getData().get(position));
        }
    }

    private int getPositionForId(long id) {
        int position = -1;
        for (HeroEquipModelVM model : getData()) {
            if (model.id.get() == id) {
                position = getData().indexOf(model);
            }
        }
        return position;
    }

    public void deleteNotifyPosition(long id) {
        int position = getPositionForId(id);
        if (position != -1) {
            getData().remove(position);
            notifyItemRemoved(position);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        public TitleViewHolder(ItemHeroEquipTitleBinding binding) {
            super(binding.getRoot());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHeroEquipBinding binding;

        public ViewHolder(ItemHeroEquipBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(HeroEquipModelVM vm) {
            binding.setVm(vm);
            binding.setHandler(new HasEquipHandler(presenter));
        }
    }
}
