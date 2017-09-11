package com.yingwumeijia.baseywmj.function.web

import android.app.Activity
import android.content.Intent
import android.webkit.WebView
import java.io.Serializable

/**
 * Created by jamisonline on 2017/7/8.
 */
abstract class NativeBaseJsBirdge(var activity: Activity) {

    lateinit var webView: WebView

    abstract fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    abstract fun onResume()

}