package com.mdove.levelgame.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdove.levelgame.R;
import com.mdove.levelgame.base.adapter.BaseListAdapter;
import com.mdove.levelgame.utils.InflateUtils;
import com.mdove.levelgame.view.MyProgressDialog;

import java.util.List;


public abstract class BaseListFragment extends Fragment implements BaseView{
    private RecyclerView rlv;
    private BaseListAdapter adapter;
    private MyProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return InflateUtils.inflate(container, R.layout.fragment_base_recyclerview);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rlv = view.findViewById(R.id.rlv);

        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = createAdapter();
        rlv.setAdapter(adapter);

        initData();
    }

    @Override
    public void showLoadingDialog(final String msg) {
        if (getActivity().isFinishing()) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog == null) {
                    progressDialog = new MyProgressDialog(getActivity());
                    progressDialog.setCancelable(false);
                }
                progressDialog.setMessage(msg);
                progressDialog.show();
            }
        });
    }

    @Override
    public void dismissLoadingDialog() {
        if (getActivity().isFinishing()) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    public abstract void initData();

    public abstract BaseListAdapter createAdapter();

    public BaseListAdapter getAdapter() {
        return adapter;
    }
}
