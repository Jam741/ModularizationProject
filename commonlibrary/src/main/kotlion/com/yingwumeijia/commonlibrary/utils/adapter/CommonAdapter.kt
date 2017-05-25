package com.yingwumeijia.commonlibrary.utils.adapter

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter

/**
 * Created by jamisonline on 2017/5/25.
 */
abstract class CommonAdapter<T:Any>(layoutInflater: LayoutInflater, context: Context, data: Array<T>) : BaseAdapter() {

    var mLayoutInflater: LayoutInflater? = null

    var mContent: Context? = null

    var mData: Array<T>? = null


    init {
        mLayoutInflater = layoutInflater
        mContent = context
        mData = data
    }

    override fun getCount(): Int {
       return if (mData == null) 0 else mData!!.size
    }

    override fun getItem(position: Int): Any? {
        return if (mData == null) null else mData!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}