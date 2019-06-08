package com.mdove.levelgame.base.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import com.mdove.levelgame.base.BaseActivity;
import com.mdove.levelgame.base.recyclerview.diff.SimpleDiffCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojing on 2018/10/22
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> data = new ArrayList<>();
    private OnDataEmptyListener mListener;

    private RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            if (mListener == null) {
                return;
            }
            if (data.size() == 0 || data == null) {
                mListener.onEmpty(true);
            } else {
                mListener.onEmpty(false);
            }
        }
    };

    protected SimpleDiffCallback<T> createDiffUtils() {
        return null;
    }

    public void updateDataByDiffUtil(List<T> data){
        if(createDiffUtils()!=null){
            this.data = data;
            createDiffUtils().update(data);
            DiffUtil.calculateDiff(createDiffUtils(), false).dispatchUpdatesTo(this);
        }
    }

    public BaseListAdapter() {
        registerAdapterDataObserver(observer);
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void unRegisterAdapter() {
        if (observer != null) {
            unregisterAdapterDataObserver(observer);
        }
        if (mListener != null) {
            mListener = null;
        }
    }

    public void setListener(OnDataEmptyListener listener) {
        mListener = listener;
    }

    public List<T> getData() {
        return data;
    }

    public void deleteFromPosition(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void addFromPosition(int position) {
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public interface OnDataEmptyListener {
        void onEmpty(boolean isEmpty);
    }
}
