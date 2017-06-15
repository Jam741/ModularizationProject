package com.yingwumeijia.commonlibrary.utils.glide

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yingwumeijia.commonlibrary.R

/**
 * Created by jamisonline on 2017/6/11.
 */
object JImageLolder {

    val IMAGE_PREVIEW_1600 = "&imageView2/2/w/1600"
    val IMAGE_PREVIEW_720 = "&imageView2/2/w/720"
    val IMAGE_PREVIEW_480 = "&imageView2/2/w/480"
    val IMAGE_PREVIEW_256 = "&imageView2/2/w/256"
    val IMAGE_FOR_SHARE = "&imageView2/1/w/100"

    fun contanisSizeParameter(imageUrl: String): Boolean {
        if (imageUrl.contains("?") && imageUrl.contains("width=") && imageUrl.contains("height"))
            return true
        return false
    }

    /**
     * 1600
     */
    fun loadOriginal(context: Context, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_1600
        Glide.with(context).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(context).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun loadOriginal(activity: Activity, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_1600
        Glide.with(activity).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(activity).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun loadOriginal(fragmentActivity: FragmentActivity, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_1600
        Glide.with(fragmentActivity).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(fragmentActivity).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun loadOriginal(fragment: Fragment, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_1600
        Glide.with(fragment).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(fragment).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    /**
     * 1600
     */
    fun load(context: Context, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_1600
        Glide.with(context).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(context).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load(activity: Activity, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_1600
        Glide.with(activity).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(activity).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load(fragmentActivity: FragmentActivity, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_1600
        Glide.with(fragmentActivity).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(fragmentActivity).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load(fragment: Fragment, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_1600
        Glide.with(fragment).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(fragment).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }


    /**
     * 720
     */
    fun load720(context: Context, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_720
        Glide.with(context).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(context).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load720(activity: Activity, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_720
        Glide.with(activity).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(activity).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load720(fragmentActivity: FragmentActivity, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_720
        Glide.with(fragmentActivity).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(fragmentActivity).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load720(fragment: Fragment, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_720
        Glide.with(fragment).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(fragment).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    /**
     * 480
     */
    fun load480(context: Context, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_480
        Glide.with(context).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(context).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load480(activity: Activity, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_480
        Glide.with(activity).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(activity).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load480(fragmentActivity: FragmentActivity, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_480
        Glide.with(fragmentActivity).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(fragmentActivity).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load480(fragment: Fragment, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_480
        Glide.with(fragment).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(fragment).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    /**
     * 256
     */
    fun load256(context: Context, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_256
        Glide.with(context).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(context).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load256(activity: Activity, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_256
        Glide.with(activity).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(activity).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load256(fragmentActivity: FragmentActivity, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_256
        Glide.with(fragmentActivity).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(fragmentActivity).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load256(fragment: Fragment, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_PREVIEW_256
        Glide.with(fragment).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(fragment).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    /**
     * 100
     */
    fun load100(context: Context, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_FOR_SHARE
        Glide.with(context).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(context).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load100(activity: Activity, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_FOR_SHARE
        Glide.with(activity).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(activity).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load100(fragmentActivity: FragmentActivity, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_FOR_SHARE
        Glide.with(fragmentActivity).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(fragmentActivity).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

    fun load100(fragment: Fragment, imageView: ImageView, imageUrl: String) {
        var url = imageUrl
        if (contanisSizeParameter(imageUrl)) url = imageUrl + IMAGE_FOR_SHARE
        Glide.with(fragment).load(url).crossFade().placeholder(R.mipmap.case_moren_pic).into(imageView)
//        GlideApp.with(fragment).load(url).placeholder(R.mipmap.case_moren_pic).into(imageView)
    }

}