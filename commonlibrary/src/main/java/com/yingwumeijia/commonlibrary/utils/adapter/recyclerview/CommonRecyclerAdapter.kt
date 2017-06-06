package com.yingwumeijia.commonlibrary.utils.adapter.recyclerview

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by jamisonline on 2017/5/31.
 */
abstract class CommonRecyclerAdapter<T> constructor(var activity: Activity?, var fragment: Fragment?, var data: ArrayList<T>?, var layoutId: Int) : RecyclerView.Adapter<RecyclerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolder {
        if (activity == null)
            return RecyclerViewHolder.get(fragment!!, parent!!, layoutId, viewType)
        else
            return RecyclerViewHolder.get(activity!!, parent!!, layoutId, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder?, position: Int) {
        holder!!.position = position
        convert(holder, data!![position], position)
    }

    abstract fun convert(holder: RecyclerViewHolder, t: T, position: Int)

    fun isLoastOneAt(position: Int): Boolean {
        return position == itemCount - 1
    }

    override fun getItemCount(): Int {
        if (data == null)
            return 0
        else
            return data!!.size
    }

    fun getItemAt(position: Int): T {
        return data!![position]
    }


    fun clearnData(){
        data!!.clear()
    }


}