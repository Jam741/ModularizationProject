package com.yingwumeijia.baseywmj.function

import android.app.Activity
import android.content.Context
//import com.camitor.oneweb.BaseJsBirdge
//import com.camitor.oneweb.OneWebModelManager
import com.yingwumeijia.baseywmj.function.web.OneWebActivity

/**
 * Created by jamisonline on 2017/6/27.
 */
object WebViewManager {


    fun startHasTitle(context: Context, url: String, title: String?) {
//        OneWebModelManager.startHasTitle(context, url, title)
        OneWebActivity.start(context, url, title, true)
    }

    fun startFullScreen(context: Context, url: String) {
//        OneWebModelManager.startNoTitle(context, url)
        OneWebActivity.start(context, url, null, false)

    }

    fun start720(context: Context, pathOf720: String) {
//        OneWebModelManager.startHasTitle(context, pathOf720, null)
        OneWebActivity.start(context, pathOf720, null, true)
    }


}