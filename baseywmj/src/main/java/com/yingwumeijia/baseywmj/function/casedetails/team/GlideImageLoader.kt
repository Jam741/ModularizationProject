package com.yingwumeijia.baseywmj.function.casedetails.team

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.youth.banner.loader.ImageLoader

/**
 * Created by jamisonline on 2017/9/24.
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        imageView!!.scaleType = ImageView.ScaleType.FIT_CENTER
        Picasso.with(context).load(path as String).into(imageView)
    }
}