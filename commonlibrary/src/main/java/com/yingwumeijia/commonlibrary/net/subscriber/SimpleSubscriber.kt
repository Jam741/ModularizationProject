package com.yingwumeijia.commonlibrary.net.subscriber

import android.content.Context
import android.widget.Toast
import com.orhanobut.logger.Logger
import com.yingwumeijia.commonlibrary.net.NetUtils
import com.yingwumeijia.commonlibrary.net.exception.ApiException
import rx.Subscriber
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by jamisonline on 2017/6/12.
 */
abstract class SimpleSubscriber<T>(var context: Context) : Subscriber<T>() {


    override fun onError(e: Throwable?) {
        Logger.d(e!!.message)
        var message: String
        if (NetUtils.isConnected(context)) {
            message = "网络异常"
        } else if (e is ApiException) {
            message = e.error_message
        } else if (e is ConnectException) {
            message = "网络连接异常，请重试"
        } else if (e is SocketTimeoutException) {
            message = "网络请求超时，请重试"
        } else {
            message = "网络异常"
        }
        e!!.printStackTrace()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    override fun onCompleted() {

    }
}