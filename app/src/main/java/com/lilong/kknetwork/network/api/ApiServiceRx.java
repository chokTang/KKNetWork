package com.lilong.kknetwork.network.api;


import com.lilong.kknetwork.network.response.AmapResponse;
import com.lilong.kknetwork.network.response.Regeocode;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * RXjava封装的retrofit
 */
public interface ApiServiceRx {

    public static final String SERVER_URL = "https://restapi.amap.com/";
    public static final String SERVER_URL1 = "https://wanandroid.com/";

    @FormUrlEncoded
    @POST("/v1/pai/post/terminal")
    Observable<AmapResponse<Regeocode>> postWitchForm(@Field("a") String a, @Field("b") String b, @Field("c") String c);
//
//    @POST("/v1/message/client/user")
//    Observable<BaseBean<String>> postWitchRequestBody(@Query("a") String a, @Query("b") String b, @Query("c") String c, @Body RequestBody requestBody);

    /**
     * 获取高德地图数据
     */
    @GET("v3/geocode/regeo?parameters")
    Observable<AmapResponse<Regeocode>> getAddress(@QueryMap Map<String, String> map);


//    @Multipart
//    @POST("/v1/message/server/user")
//    Observable<BaseBean<String>> uploadFile(@Part List<MultipartBody.Part> path);
}
