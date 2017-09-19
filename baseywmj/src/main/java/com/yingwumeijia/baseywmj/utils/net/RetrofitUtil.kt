package com.yingwumeijia.baseywmj.utils.net

import android.util.Log
import com.yingwumeijia.baseywmj.option.PATHUrlConfig
import com.yingwumeijia.baseywmj.utils.net.converter.GsonConverterFactory
import com.yingwumeijia.baseywmj.utils.net.interceptor.AccountInterceptor
import com.yingwumeijia.baseywmj.utils.net.proxy.ProxyHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import java.lang.reflect.Proxy
import java.util.concurrent.TimeUnit

/**
 * Created by jamisonline on 2017/6/1.
 */
class RetrofitUtil {


    companion object {

        val instacne = getInstance()

        fun getInstance(): RetrofitUtil {
            return RetrofitUtil()
        }

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(PATHUrlConfig.severUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(defaultClient())
                    .build()
        }

        private fun defaultClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("HTTP", message) })
            builder.connectTimeout(10000, TimeUnit.MILLISECONDS)
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
            builder.addInterceptor(AccountInterceptor())
            return builder.build()
        }


    }


    operator fun <T> get(tClass: Class<T>): T {
        return getRetrofit().create(tClass)
    }

    /**
     * 动态代理

     * @param tClass
     * *
     * @param <T>
     * *
     * @return
    </T> */
    fun <T> getProxy(tClass: Class<T>): T {
        val t = getRetrofit().create(tClass)
        return Proxy.newProxyInstance(tClass.classLoader, arrayOf<Class<T>>(tClass), ProxyHandler(t, IGlobalManager { })) as T
    }

}