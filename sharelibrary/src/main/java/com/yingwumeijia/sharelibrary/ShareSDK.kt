package com.yingwumeijia.sharelibrary

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo

/**
 * Created by jamisonline on 2017/7/10.
 */
class ShareSDK {

    companion object{
        fun init(context: Context,wb_app_key:String,wx_app_id:String) {
            SPUtils.put(context,"WX_APP_ID",wx_app_id)
            WbSdk.install(context, AuthInfo(context, wb_app_key , ShareConstant.REDIRECT_URL, ShareConstant.SCOPE))
        }
    }

}