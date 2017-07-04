package com.camitor.oneweb

import android.content.Context

/**
 * Created by jamisonline on 2017/7/3.
 */
object OneWebModelManager {


    fun binding(context: Context) {
        OneWebSDK.init(context)
    }


    fun start(context: Context, url: String, title: String?) {

    }


    fun startNoTitle(context: Context, url: String) {

    }
}