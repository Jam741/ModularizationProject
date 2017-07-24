package com.yingwumeijia.android.worker.function.splash

import android.os.Bundle
import android.util.Log
import com.yingwumeijia.android.worker.Constant
import com.yingwumeijia.android.worker.R
import com.yingwumeijia.android.worker.function.home.EmployeeMainActivity
import com.yingwumeijia.baseywmj.api.Service
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.SeverBean
import com.yingwumeijia.baseywmj.utils.net.SeverUrlManager
import com.yingwumeijia.baseywmj.utils.net.converter.GsonConverterFactory
import com.yingwumeijia.commonlibrary.utils.AppUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : JBaseActivity() {

    val Api by lazy {
        Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(defaultClient())
                .build()
                .create(Service::class.java)
    }


    private fun defaultClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("Sever:", message) })
        builder.connectTimeout(10000, TimeUnit.MILLISECONDS)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }


    fun loadBaseUrl() {
        Api
                .getService("e", AppUtils.getVersionName(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Subscriber<SeverBean>() {
                    override fun onNext(t: SeverBean?) {
                        didSuccess(t!!)
                    }

                    override fun onError(e: Throwable?) {

                    }

                    override fun onCompleted() {

                    }

                })

    }


    /**
     *  加载完成
     */
    private fun didSuccess(t: SeverBean) {
        SeverUrlManager.refreshBaseUrl(t!!.serverUrl)
        close()
        EmployeeMainActivity.start(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        loadBaseUrl()
    }
}
