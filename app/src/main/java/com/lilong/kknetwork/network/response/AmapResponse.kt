package com.lilong.kknetwork.network.response

import com.lilong.network.BaseResponse


/**
 * 作者　: TYK
 * 时间　: 2023/12/23
 * 描述　:高德地图服务器返回数据的基类
 * 如果你的项目中有基类，那美滋滋，可以继承BaseResponse，请求时框架可以帮你自动脱壳，自动判断是否请求成功，怎么做：
 * 1.继承 BaseResponse
 * 2.isSuccess 方法，编写你的业务需求，根据自己的条件判断数据是否请求成功
 * 3.重写 getResponseCode、getResponseData、getResponseMsg方法，传入你的 code data msg
 * @param status 返回结果状态值：返回值为 0 或 1，0 表示请求失败；1 表示请求成功。
 * @param info info:当 status 为 0 时，info 会返回具体错误原因，否则返回“OK”。详情可以参阅info状态表:https://lbs.amap.com/api/webservice/guide/tools/info
 * @param infocode infocode:当 status 为 0 时，info 会返回具体错误原因，否则返回“OK”。详情可以参阅info状态表:https://lbs.amap.com/api/webservice/guide/tools/info
 * @param regeocode 返回高德数据
 *
 */
data class AmapResponse<T>(
    val status: Int,
    val info: String,
    val infocode: Int,
    val regeocode: T
) : BaseResponse<T>() {

    // 这里是示例，高德 网站返回的 错误码为 1 就代表请求成功，请你根据自己的业务需求来改变
    override fun isSuccess() = status == 1

    override fun getResponseCode() = infocode

    override fun getResponseData() = regeocode

    override fun getResponseMsg() = info

}