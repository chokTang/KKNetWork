package com.lilong.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

/**
 * 作者　: TYK
 * 时间　: 2022/1/13
 * 描述　: token过期拦截器，登录失效拦截器
 */
class TokenOutInterceptor(private val responseDeal:(String)->Unit) : Interceptor {


    @kotlin.jvm.Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return if (response.body() != null && response.body()!!.contentType() != null) {
            val mediaType = response.body()!!.contentType()
            val string = response.body()!!.string()
            val responseBody = ResponseBody.create(mediaType, string)
            //执行登录失效操作
            responseDeal.invoke(string)


            response.newBuilder().body(responseBody).build()
        } else {
            response
        }
    }
}