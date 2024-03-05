package com.lilong.kknetwork.network.repository

import com.lilong.kknetwork.network.apiService
import com.lilong.kknetwork.network.response.AmapResponse
import com.lilong.kknetwork.network.response.Regeocode

/**
 * 作者　: hegaojian
 * 时间　: 2020/5/4
 * 描述　: 处理协程的请求类
 */

val HttpRequestCoroutine: HttpRequestRepository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    HttpRequestRepository()
}

class HttpRequestRepository {
    /**
     * 获取当前地址
     *
     */
    suspend fun getAddress(map :HashMap<String, String>): AmapResponse<Regeocode?> {
        return apiService.getAddress(map)
    }


}