package com.camitor.pdfviewlibrary

import android.content.Context

/**
 * Created by jamisonline on 2017/8/15.
 */
object PDFViewManager {

    fun openPDF(context: Context, title: String, url: String) {
        PDFViewActivity.start(context, title, url)
    }
}