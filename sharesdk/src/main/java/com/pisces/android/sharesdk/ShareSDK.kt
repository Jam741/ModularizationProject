package com.pisces.android.sharesdk

import android.content.Context
import com.sina.weibo.sdk.constant.WBConstants.Base.APP_KEY
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.WbSdk


/**
 * Created by Jam on 2017/9/23.
 */
class ShareSDK {

    companion object {
        fun initSDK(context: Context, wbAppKey: String, wxAppId: String) {
            SPUtils.put(context, "WX_APP_ID", wxAppId)
            WbSdk.install(context, AuthInfo(context, wbAppKey, Config.WeiboConstants.REDIRECT_URL, Config.WeiboConstants.SCOPE))
        }
    }

}