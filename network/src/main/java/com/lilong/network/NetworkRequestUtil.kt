package com.lilong.network

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext


const val TAG = "REQUEST_TAG"

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 * @param isShowDialog 是否显示加载框
 */
fun <T> requestKotlin(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    error: (AppException) -> Unit = {},
    isShowDialog: Boolean = false,
    dialogBefore: (() -> Unit)? = null,
    dialogSuccess: (() -> Unit)? = null,
    dialogError: (() -> Unit)? = null
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return CoroutineScope(EmptyCoroutineContext).launch {
        runCatching {
            //请求之前
            if (isShowDialog) {
                //切到主线程
                withContext(Dispatchers.Main){
                    dialogBefore?.invoke()
                }
            }
            Log.d(TAG, "request: 开始请求")
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            withContext(Dispatchers.Main){
                dialogSuccess?.invoke()
            }
            Log.d(TAG, "request: 网络请求成功")

            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(it) { t ->
                    success(t)
                }
            }.onFailure { e ->
                //打印错误消息
                e.message?.loge()
                //打印错误栈信息
                e.printStackTrace()
                //失败回调
                error(ExceptionHandle.handleException(e))
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            withContext(Dispatchers.Main){
                dialogError?.invoke()
            }
            Log.d(TAG, "request: 网络请求异常")
            //打印错误消息
            it.message?.loge()
            //打印错误栈信息
            it.printStackTrace()
            //失败回调
            error(ExceptionHandle.handleException(it))
        }
    }
}


/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponse(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit
) {
    coroutineScope {
        withContext(Dispatchers.Main){
            when {
                response.isSuccess() -> {
                    success(response.getResponseData())
                }

                else -> {
                    throw AppException(
                        response.getResponseCode(),
                        response.getResponseMsg(),
                        response.getResponseMsg()
                    )
                }
            }
        }

    }
}
