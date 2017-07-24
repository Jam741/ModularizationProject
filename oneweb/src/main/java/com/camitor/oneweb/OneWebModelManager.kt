package com.camitor.oneweb

import android.content.Context

/**
 * Created by jamisonline on 2017/7/3.
 */
object OneWebModelManager {

    fun startHasTitle(context: Context, url: String, title: String?) {
        OneWebActivity.start(context, url, title, true)
    }

    fun startNoTitle(context: Context, url: String) {
        OneWebActivity.start(context, url, null, false)
    }
}