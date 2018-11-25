package com.mdove.levelgame.net;

import com.mdove.levelgame.update.model.RealUpdate;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author MDove on 2018/11/14.
 */
public interface ApiServer {
    @FormUrlEncoded
    @POST("PasswordGuard/checkUpdate")
    Observable<RealUpdate> checkUpdate(@Field("version") String version);

    @FormUrlEncoded
    @POST("ZJ/servlet/appContent")
    Observable<ResponseBody> feedBack(@Field("tips") String content);
}
