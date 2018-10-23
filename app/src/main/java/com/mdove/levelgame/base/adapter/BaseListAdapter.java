package com.mdove.levelgame.base.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * @author zhaojing on 2018/10/22
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> data;

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
