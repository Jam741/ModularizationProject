package com.yingwumeijia.baseywmj.function.personal

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.yingwumeijia.baseywmj.R


class PersonMenuAdapter(private val data: List<MenuInfo>, private val context: Context, private var menuOnItemClickListener: PersonGroupMenuAdapter.MenuOnItemClickListener?, private var menuOnItemLongClickListener: PersonGroupMenuAdapter.MenuOnItemLongClickListener?) : RecyclerView.Adapter<PersonMenuAdapter.MyViewHolder>() {


    fun setMenuOnItemLongClickListener(menuOnItemLongClickListener: PersonGroupMenuAdapter.MenuOnItemLongClickListener) {
        this.menuOnItemLongClickListener = menuOnItemLongClickListener
    }

    fun setMenuOnItemClickListener(menuOnItemClickListener: PersonGroupMenuAdapter.MenuOnItemClickListener) {
        this.menuOnItemClickListener = menuOnItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_person_menu, parent, false), menuOnItemClickListener, menuOnItemLongClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (_, icon, text, textColor) = data[position]
        holder.tv_menu.setTextColor(context.resources.getColor(textColor))

        val left_drawable = context.resources.getDrawable(icon)
        val right_drawable = context.resources.getDrawable(R.mipmap.mine_more_ico)
        left_drawable.setBounds(0, 0, left_drawable.minimumWidth, left_drawable.minimumHeight)
        right_drawable.setBounds(0, 0, right_drawable.minimumWidth, right_drawable.minimumHeight)
        holder.tv_menu.setCompoundDrawables(left_drawable, null, right_drawable, null)
        holder.tv_menu.text = text
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(itemView: View, var menuOnItemClickListener: PersonGroupMenuAdapter.MenuOnItemClickListener?, var menuOnItemLongClickListener: PersonGroupMenuAdapter.MenuOnItemLongClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        var tv_menu: TextView

        init {
            tv_menu = itemView.findViewById(R.id.tv_menu) as TextView
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View) {
            if (menuOnItemClickListener != null) {
                menuOnItemClickListener!!.itemClick(data[adapterPosition].action)
            }
        }

        override fun onLongClick(v: View): Boolean {
            if (menuOnItemLongClickListener != null) {
                menuOnItemLongClickListener!!.itemLongClick(data[adapterPosition].action)
            }
            return true
        }
    }


}


