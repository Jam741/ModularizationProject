package com.yingwumeijia.commonlibrary.net

import android.content.Context
import com.orhanobut.logger.Logger
import com.yingwumeijia.commonlibrary.api.CommonApi
import com.yingwumeijia.commonlibrary.net.interceptor.AccountInterceptor
import com.yingwumeijia.commonlibrary.net.proxy.ProxyHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Proxy

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
            return Retrofit
                    .Builder()
                    .baseUrl(SeverUrlManager.baseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient())
                    .build()
        }

        private fun okHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor()).addInterceptor(AccountInterceptor()).build()
        }

        private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Logger.d("HTTP", message) }).setLevel(HttpLoggingInterceptor.Level.BODY)
        }


    }


    fun getCommonService(): CommonApi {
        return get<CommonApi>(CommonApi::class.java)
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
        return Proxy.newProxyInstance(tClass.classLoader, arrayOf<Class<T>>(tClass), ProxyHandler(t, IGlobalManager {  })) as T
    }

}