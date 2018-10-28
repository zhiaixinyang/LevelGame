package com.mdove.levelgame.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by MDove on 2018/10/28.
 */

public class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<T> data;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
