package com.mdove.levelgame.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MDove on 2018/11/14.
 */

public class RetrofitUtil extends RetrofitFactory {
    public static <T> T create(String baseApiUrl, Class<T> service) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        clientBuilder.connectTimeout(10000, TimeUnit.MILLISECONDS);
        clientBuilder.readTimeout(10000, TimeUnit.MILLISECONDS);
        clientBuilder.writeTimeout(50000, TimeUnit.MILLISECONDS);

//        clientBuilder.addInterceptor(getPublicParamsInterceptor(context));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build())
                .build();

        return retrofit.create(service);
    }
}
