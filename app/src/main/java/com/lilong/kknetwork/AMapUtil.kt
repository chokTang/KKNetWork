package com.lilong.kknetwork

import android.content.Context
import android.content.Intent
import android.util.Log

object AMapUtil {

    /**
     * 导航到某地
     */
    fun navi(context: Context) {
        val intent = Intent()
        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV")
        intent.putExtra("KEY_TYPE", 10007)
        //起点经纬度，以及名称，不填就是当前位置
//        intent.putExtra("EXTRA_SNAME", "北京⼤学")
//        intent.putExtra("EXTRA_SLON", 116.31088)
//        intent.putExtra("EXTRA_SLAT", 39.99281)
        //终点经纬度
        intent.putExtra("EXTRA_DNAME", "复旦⼤学")
        intent.putExtra("EXTRA_DLON", 121.503584)
        intent.putExtra("EXTRA_DLAT", 31.296426)
        //:起终点是否偏移(int)(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要
        intent.putExtra("EXTRA_DEV", 0)
        //驾⻋⽅式=1（避免收费） =2（多策略算路）=3 （不⾛⾼速） =4（躲避拥堵）
        //=5（不⾛⾼速且避免收费） =6（不⾛⾼速且躲避拥堵）
        //=7（躲避收费且躲避拥堵） =8（不⾛⾼速躲避收费和拥堵）
        //=20 （⾼速优先） =24（⾼速优先且躲避拥堵）
        //=-1（地图内部设置默认规则）
        intent.putExtra("EXTRA_M", -1)
        Log.d("跳转导航222222", "navi: 跳转导航")
        context.sendBroadcast(intent)

    }


    /**
     * 导航到附近的 poi
     *
     */
    fun naviPoiKeyWord(context: Context){

        val intent = Intent()
        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV")
        intent.putExtra("KEY_TYPE", 10037)
        intent.putExtra("KEYWORDS", "加油站")
        //目标位置
//        intent.putExtra("LAT", 24.444593)
//        intent.putExtra("LON", 118.101011)
        intent.putExtra("DEV", 0)
        intent.putExtra("SOURCE_APP", "Third App")
        context.sendBroadcast(intent)
    }


    /**
     * 导航到某个经纬度
     */
    fun naviPoiLatLon(context: Context){
        val intent = Intent()
        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV")
        intent.putExtra("KEY_TYPE", 10013)
        intent.putExtra("EXTRA_LON", 118.182198)
        intent.putExtra("EXTRA_LAT", 24.486762)
        intent.putExtra("EXTRA_DEV", 0)
        intent.putExtra("SOURCE_APP", "Third App")
        context.sendBroadcast(intent)

    }


    /**
     * 导航到某个地址
     */
    fun naviAddress(context: Context){
        val intent = Intent()
        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV")
        intent.putExtra("KEY_TYPE", 10011)
        intent.putExtra("EXTRA_ADDRESS", "上海财经大学")
        intent.putExtra("SOURCE_APP", "Third App")
        context.sendBroadcast(intent)
    }


}