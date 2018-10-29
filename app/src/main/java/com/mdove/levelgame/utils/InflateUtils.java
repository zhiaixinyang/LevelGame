package com.mdove.levelgame.utils;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author MDove on 2018/2/10.
 */
public class InflateUtils {

    public static <T extends ViewDataBinding> T bindingInflate(@NonNull ViewGroup parent, @LayoutRes int layoutId) {
        return DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                layoutId,
                parent,
                false);
    }

    public static View inflate(@NonNull ViewGroup parent, @LayoutRes int layoutId) {
        return LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
    }
}
