package com.yingwumeijia.baseywmj.function

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by jamisonline on 2017/6/28.
 */
object VideoPlayManager {

    fun play(context: Context, videoUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(videoUrl), "video/mp4")
        context.startActivity(intent)
    }
}