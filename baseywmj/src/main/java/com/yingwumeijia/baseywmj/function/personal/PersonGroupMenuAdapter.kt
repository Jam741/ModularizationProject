package com.yingwumeijia.baseywmj.function.personal

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration

/**
 * Created by Jam on 2017/4/8 下午6:24.
 * Describe:
 */

class PersonGroupMenuAdapter(internal var context: Context, internal var menuOnItemClickListener: MenuOnItemClickListener, internal var menuOnItemLongClickListener: MenuOnItemLongClickListener) : RecyclerView.Adapter<PersonGroupMenuAdapter.MyViewHolder>() {

    val data: ArrayList<ArrayList<MenuInfo>> = ArrayList<ArrayList<MenuInfo>>()


    fun setMenuOnItemClickListener(menuOnItemClickListener: MenuOnItemClickListener) {
        this.menuOnItemClickListener = menuOnItemClickListener
    }

    fun setMenuOnItemLongClickListener(menuOnItemLongClickListener: MenuOnItemLongClickListener) {
        this.menuOnItemLongClickListener = menuOnItemLongClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_person_group, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.rvGroup.layoutManager = LinearLayoutManager(context)
        holder.rvGroup.addItemDecoration(HorizontalDividerItemDecoration.Builder(context).margin(ScreenUtils.dp2px(20f, context), 0).build())
        holder.rvGroup.adapter = PersonMenuAdapter(data[position], context, menuOnItemClickListener, menuOnItemLongClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    fun refreshMenu(data: ArrayList<ArrayList<MenuInfo>>) {
        this@PersonGroupMenuAdapter.data.clear()
        this@PersonGroupMenuAdapter.data.addAll(data)
        notifyDataSetChanged()
    }

    fun insertItem(menuInfo: MenuInfo, position: IntArray) {
        data[position[0]].add(position[1], menuInfo)
        notifyDataSetChanged()
    }

    fun insertGroup(position: Int, vararg menuInfos: MenuInfo) {
        val menuInfoList = ArrayList<MenuInfo>()
        for (m in menuInfos) {
            menuInfoList.add(m)
        }
        data.add(position, menuInfoList)
        notifyItemInserted(position)
    }

    fun refreshItem(menuInfo: MenuInfo, position: IntArray) {
        data[position[0]].set(position[1], menuInfo)
        notifyDataSetChanged()
    }

    fun removeItem(position: IntArray) {
        data[position[0]].removeAt(position[1])
        notifyDataSetChanged()
    }

    fun removeGroup(position: Int) {
        data[position]
        notifyItemRemoved(position)
    }


    fun refreshGroup(position: Int, vararg menuInfos: MenuInfo) {
        val menuInfoList = ArrayList<MenuInfo>()
        for (m in menuInfos) {
            menuInfoList.add(m)
        }
        data[position] = menuInfoList
        notifyItemChanged(position)
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val rvGroup: RecyclerView

        init {
            rvGroup = itemView.findViewById(R.id.rv_groupMenu) as RecyclerView
        }
    }

    interface MenuOnItemClickListener {

        fun itemClick(action: MenuAction)
    }

    interface MenuOnItemLongClickListener {

        fun itemLongClick(action: MenuAction)
    }

}
