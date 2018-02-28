package com.camitor.oneweb

import android.content.Context
import android.util.Log
import com.tencent.smtt.sdk.QbSdk

/**
 * Created by jamisonline on 2017/7/3.
 */
class OneWebSDK {


    private constructor()

    companion object {

        val preInitCallback: QbSdk.PreInitCallback = object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onViewInitFinished(p0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + p0)
            }
        }

        fun init(context: Context) {
            QbSdk.initX5Environment(context, preInitCallback)
        }
    }
}