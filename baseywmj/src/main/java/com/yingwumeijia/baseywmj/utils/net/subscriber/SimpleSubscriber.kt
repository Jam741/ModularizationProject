package com.yingwumeijia.baseywmj.utils.net.subscriber

import com.yingwumeijia.baseywmj.utils.net.HttpUtil

/**
 * Created by jamisonline on 2017/6/12.
 */
abstract class SimpleSubscriber<T>(var context: android.content.Context) : rx.Subscriber<T>() {


    override fun onError(e: Throwable?) {
        if (e != null) {
            e.printStackTrace()
            HttpUtil.disposeHttpException(context, e)
        }
    }


    override fun onCompleted() {
    }

    override fun onNext(t: T?) {
        _onNext(t)
    }


    protected abstract fun _onNext(t: T?)

}