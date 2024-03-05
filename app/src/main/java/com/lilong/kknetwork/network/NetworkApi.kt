package com.lilong.kknetwork.network

import android.content.Intent
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lilong.network.BaseNetworkApi
import com.lilong.network.appContext
import com.lilong.network.interceptor.CacheInterceptor
import com.lilong.network.interceptor.MyHeadInterceptor
import com.lilong.network.interceptor.TokenOutInterceptor
import com.lilong.network.interceptor.logging.LogInterceptor
import com.lilong.kknetwork.MainActivity
import com.lilong.kknetwork.network.api.ApiService
import com.lilong.kknetwork.network.api.ApiServiceRx
import com.lilong.kknetwork.network.response.ApiResponse
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 作者　: TYK
 * 时间　: 2023/12/23
 * 描述　: 网络请求构建器，继承BasenetworkApi 并实现setHttpClientBuilder/setRetrofitBuilder方法，
 * 在这里可以添加拦截器，设置构造器可以对Builder做任意操作
 */


//双重校验锁式-单例 封装NetApiService 方便直接快速调用简单的接口
val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    NetworkApi.INSTANCE.getApi(ApiService::class.java, ApiService.SERVER_URL)
}

//双重校验锁式-单例 封装NetApiService 方便直接快速调用简单的接口
val apiServiceRx: ApiServiceRx by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    NetworkApi.INSTANCE.getApi(ApiServiceRx::class.java, ApiService.SERVER_URL)
}


class NetworkApi : BaseNetworkApi() {

    companion object {
        val INSTANCE: NetworkApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkApi()
        }
    }


    val gson: Gson by lazy { Gson() }

    /**
     * 实现重写父类的setHttpClientBuilder方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            //设置缓存配置 缓存最大10M
            cache(Cache(File(appContext.cacheDir, "ll_cache"), 10 * 1024 * 1024))
            //添加Cookies自动持久化
            cookieJar(cookieJar)
            //示例：添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
            addInterceptor(MyHeadInterceptor())
            //添加缓存拦截器 可传入缓存天数，不传默认7天
            addInterceptor(CacheInterceptor())
            //拦截器里面返回数据处理，如登录失效等，就可以用下面例子返回99999登录失效，就跳转登录页面(这里没有loginActivity，就用main代替当前demo)
            addInterceptor(TokenOutInterceptor { string ->
                val apiResponse = gson.fromJson(string, ApiResponse::class.java)
//            val apiResponse = gson.fromJson(string, AmapResponse::class.java)
                //判断逻辑 模拟一下
                if (apiResponse.errorCode == 99999) {
                    //如果是普通的activity话 可以直接跳转，如果是navigation中的fragment，可以发送通知跳转
                    appContext.startActivity(Intent(appContext, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                }
            })
            // 日志拦截器
            addInterceptor(LogInterceptor())
            //超时时间 连接、读、写
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(5, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
        }
        return builder
    }

    /**
     * 实现重写父类的setRetrofitBuilder方法，
     * 在这里可以对Retrofit.Builder做任意操作，比如添加GSON解析器，protobuf等
     */
    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            //如果是RXJAVA需要添加这个回调
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }
    }

    //获取到cookie
    val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))
    }

}



