package com.yingwumeijia.commonlibrary.net.interceptor


import com.yingwumeijia.commonlibrary.net.AccountManager

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Jam on 2017/2/15 下午5:04.
 * Describe:
 */

class AccountInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
                .newBuilder()
                .addHeader("access-token", AccountManager.accessToken())
                .addHeader("session-id", AccountManager.sessionId())
                .build()
        return chain.proceed(request)
    }
}
