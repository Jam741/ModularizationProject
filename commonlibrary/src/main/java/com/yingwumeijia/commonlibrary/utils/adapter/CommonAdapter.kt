package com.yingwumeijia.commonlibrary.utils.adapter

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter

/**
 * Created by jamisonline on 2017/5/25.
 */
abstract class CommonAdapter<T : Any>(var activity: Activity?, var fragment: Fragment?, var data: ArrayList<T>?, var itemLayoutId: Int) : BaseAdapter() {


    override fun getCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    override fun getItem(position: Int): Any? {
        return if (data == null) null else data!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder
        if (activity == null)
            viewHolder = ViewHolder.get(fragment!!, convertView!!, parent!!, itemLayoutId)
        else
            viewHolder = ViewHolder.get(activity!!, convertView!!, parent!!, itemLayoutId)

        conver(viewHolder, getItem(position) as T, position)
        viewHolder.position = position
        return viewHolder.mContentView
    }

    fun getViewHolder(position: Int, convertView: View, parent: ViewGroup): ViewHolder {
        if (activity == null)
            return ViewHolder.get(fragment!!, convertView, parent, itemLayoutId)
        else
            return ViewHolder.get(activity!!, convertView, parent, itemLayoutId)
    }

    abstract fun conver(holder: ViewHolder, item: T, position: Int)


}