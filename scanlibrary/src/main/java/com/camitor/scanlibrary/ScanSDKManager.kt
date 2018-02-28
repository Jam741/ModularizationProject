package com.camitor.scanlibrary

import android.app.Activity
import android.content.Context
import com.google.zxing.integration.android.IntentIntegrator

/**
 * Created by jamisonline on 2017/7/4.
 */
object ScanSDKManager {

    fun simpleScan(activity: Activity) {
        IntentIntegrator(activity).initiateScan()
    }

}