package com.mdove.levelgame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhaojing on 2018/10/24
 */
public class TestActivity extends AppCompatActivity {
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_test);

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d("aaa",  "subscribeOn111" + Thread.currentThread().getName());
                e.onNext(111);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        Log.d("aaa",  "observeOn222" + Thread.currentThread().getName());

                        return Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                                Log.d("aaa", "subscribeOn222" + Thread.currentThread().getName());
                                e.onNext(222);
                            }
                        }).subscribeOn(AndroidSchedulers.mainThread());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        Log.d("aaa",  "observeOn333" + Thread.currentThread().getName());
                        return Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                                Log.d("aaa",  "subscribeOn333" + Thread.currentThread().getName());
                                e.onNext(333);
                            }
                        }).subscribeOn(Schedulers.io());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        Log.d("aaa",  "observeOn444" + Thread.currentThread().getName());
                        return Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                                Log.d("aaa", "subscribeOn44" + Thread.currentThread().getName());
                                e.onNext(333);
                            }
                        }).subscribeOn(AndroidSchedulers.mainThread());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("aaa", "observeOn!!!" + Thread.currentThread().getName());
                    }
                });
    }
}
