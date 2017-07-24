package com.yingwumeijia.sharelibrary

import android.graphics.Bitmap

/**
 * Created by jamisonline on 2017/7/10.
 */
data class ShareData(var title: String, var description: String, var url: String, var img: Bitmap, var imgUrl: String? ,var source:Int?) {
}