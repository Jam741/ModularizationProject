package com.yingwumeijia.baseywmj.function.web

import android.app.Activity
import android.content.Intent
import com.tencent.smtt.sdk.WebView
import java.io.Serializable

/**
 * Created by jamisonline on 2017/7/8.
 */
abstract class BaseJsBirdge(var activity: Activity) {

    lateinit var webView: WebView

    abstract fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    abstract fun onResume()

}