package com.yingwumeijia.commonlibrary.net.subscriber

import android.content.Context
import android.widget.Toast
import com.orhanobut.logger.Logger
import com.yingwumeijia.commonlibrary.net.HttpUtil
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
        if (e != null) {
            e.printStackTrace()
            HttpUtil.disposeHttpException(context, e)
        }
    }


    override fun onCompleted() {
        Logger.d("onCompleted")
    }

    override fun onNext(t: T?) {
        Logger.d("onNext")
        _onNext(t)
    }


    protected abstract fun _onNext(t: T?)

}