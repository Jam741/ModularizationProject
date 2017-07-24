package com.yingwumeijia.sharelibrary

import android.content.Context
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo

/**
 * Created by jamisonline on 2017/7/10.
 */
class ShareSDK {

    companion object{
        fun init(context: Context) {
            WbSdk.install(context, AuthInfo(context, ShareConstant.APP_KEY, ShareConstant.REDIRECT_URL, ShareConstant.SCOPE))
        }
    }

}