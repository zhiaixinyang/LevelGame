package com.mdove.levelgame.net;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MDove on 18/2/14.
 */

public abstract class RetrofitFactory {

    private static PublicParamsGenerator<String, String> sParamsGenerator;

    protected static Interceptor getPublicParamsInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originRequest = chain.request();
                HttpUrl originUrl = originRequest.url();

                HttpUrl.Builder newUrlBuilder = originUrl.newBuilder();

                for (Map.Entry<String, String> entry : getPublicParams(context).entrySet()) {
                    newUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
                }

                Request.Builder builder = originRequest.newBuilder().url(newUrlBuilder.build());
                return chain.proceed(builder.build());
            }
        };
    }

    /**
     * 自由定制公共参数
     * @param publicParamsGenerator
     */
    public static void setPublicParamsGenerator(PublicParamsGenerator<String, String> publicParamsGenerator) {
        sParamsGenerator = publicParamsGenerator;
    }

    public static Map<String, String> getPublicParams(Context context) {
        if (sParamsGenerator != null) {
            return sParamsGenerator.generate();
        }
        Map<String, String> params = new HashMap<>();
//        params.put("vc", Integer.toString(SystemUtils.getVersionCode(context)));
//        params.put("vn", SystemUtils.getVersionName(context));
//        params.put("udid", UDIDUtils.getUDID(context));
//        params.put("tz", TimeZone.getDefault().getID());
//        params.put("locale", Locale.getDefault().getDisplayName());
//        params.put("sdk", Integer.toString(SystemUtils.getApiLevel()));
//        params.put("from", context.getPackageName());
//        params.put("nw", NetworkUtils.getNetworkTypeName(context));
//        params.put("dm", SystemUtils.getNonNullModel());
//        params.put("idfa", SystemUtils.getImei(context));
//        params.put("op", SystemUtils.getSimCountryISO(context));
        return params;
    }
}
