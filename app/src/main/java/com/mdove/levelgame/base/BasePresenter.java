package com.mdove.levelgame.base;


/**
 * @author MDove on 2018/2/10.
 */
public interface BasePresenter<T extends BaseView>{
    void subscribe(T view);

    void unSubscribe();
}
