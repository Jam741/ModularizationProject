package com.yingwumeijia.commonlibrary.utils.adapter

import android.app.Activity
import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by jamisonline on 2017/5/25.
 */
class ViewHolder {

    var activity: Activity? = null

    var fragment: Fragment? = null


    var mViews = SparseArray<View>()
    var mContentView: View
    var position: Int? = null


    constructor(activity: Activity, parent: ViewGroup, @LayoutRes layoutId: Int) {
        this.mContentView = LayoutInflater.from(activity).inflate(layoutId, parent, false)
        this.mContentView.setTag(this)
    }

    constructor(fragment: Fragment, parent: ViewGroup, @LayoutRes layoutId: Int) {
        this.mContentView = LayoutInflater.from(fragment.context).inflate(layoutId, parent, false)
        this.mContentView.setTag(this)
    }


    companion object {
        fun get(activity: Activity, convertView: View?, parent: ViewGroup, @LayoutRes layoutId: Int): ViewHolder {
            if (convertView == null)
                return ViewHolder(activity, parent, layoutId)
            else
                return convertView.getTag() as ViewHolder
        }

        fun get(fragment: Fragment, convertView: View, parent: ViewGroup, @LayoutRes layoutId: Int): ViewHolder {
            if (convertView == null)
                return ViewHolder(fragment, parent, layoutId)
            else
                return convertView.getTag() as ViewHolder
        }
    }

    /**
     * get view from viewId
     */
    fun getView(@IdRes viewId: Int): View {
        var view = mViews[viewId]
        if (view == null) {
            view = mContentView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view
    }


    fun setText(@IdRes viewId: Int, text: String) {
        var tv = getView(viewId) as TextView
        tv.text = text
    }

    fun setImgRes(@IdRes id: Int, @IdRes resId: Int) {
        var iv = getView(id) as ImageView
        iv.setImageResource(resId)
    }


}