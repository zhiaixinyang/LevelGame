package com.mdove.levelgame.base;



import android.app.Activity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MBENBEN on 2018/10/21.
 */
public class RxTransformerHelper {


    /**
     * 切换线程
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> schedulerTransf() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 切换线程, 用于Single
     *
     * @param <T>
     * @return
     */
    public static <T> SingleTransformer<T, T> schedulerTransSingle() {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 显示加载loading, 订阅完成后(正常or异常)dismiss掉dialog
     * 必须在切换完线程{@link RxTransformerHelper#schedulerTransf()}之后调用
     *
     * @param view
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> showLoadingDialog(final BaseView view, final String msg) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                view.showLoadingDialog(msg);
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                view.dismissLoadingDialog();
                            }
                        });
            }
        };
    }

    /**
     * 切换线程同时显示加载loading, 订阅完成后(正常or异常)dismiss掉dialog
     *
     * @param view
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> schedulerTransformAndShowLoading(final BaseView view, final String msg) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                view.showLoadingDialog(msg);
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                view.dismissLoadingDialog();
                            }
                        });
            }
        };
    }

//    /**
//     * 切换线程并显示loading
//     *
//     * @param activity
//     * @param <T>
//     * @return
//     */
//    public static <T> ObservableTransformer<T, T> schedulerTransformAndShowLoading(final Activity activity) {
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<T> upstream) {
//                return upstream
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnSubscribe(new Consumer<Disposable>() {
//                            @Override
//                            public void accept(@NonNull Disposable disposable) throws Exception {
//                                activity.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (activity instanceof AbstractActivity) {
//                                            AbstractActivity abstractActivity = (AbstractActivity) activity;
//                                            LoadingThreadErrorLog.log("schedulerTransformAndShowLoading--showLoadingDialog");
//                                            abstractActivity.showLoadingDialog("");
//                                        }
//                                    }
//                                });
//                            }
//                        })
//                        .subscribeOn(AndroidSchedulers.mainThread())
//                        .doFinally(new Action() {
//                            @Override
//                            public void run() throws Exception {
//                                activity.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (activity instanceof AbstractActivity) {
//                                            AbstractActivity abstractActivity = (AbstractActivity) activity;
//                                            LoadingThreadErrorLog.log("schedulerTransformAndShowLoading--dismissLoadingDialog");
//                                            abstractActivity.dismissLoadingDialog();
//                                        }
//                                    }
//                                });
//                            }
//                        });
//            }
//        };
//    }
}
