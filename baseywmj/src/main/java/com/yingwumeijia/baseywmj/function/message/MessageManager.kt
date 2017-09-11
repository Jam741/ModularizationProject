package com.yingwumeijia.baseywmj.function.message

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.commonlibrary.utils.SPUtils
import java.util.ArrayList

/**
 * Created by jamisonline on 2017/7/5.
 */
object MessageManager {

    val KEY_MESSAGE = "KEY_MESSAGE"

    fun insert(context: Context, messageBean: MessageBean) {
        var list: MutableList<MessageBean>? = getMessageList(context)
        if (list == null) list = ArrayList()
        list!!.add(0, messageBean)
        val listType = object : TypeToken<List<MessageBean>>() {}.type
        val jsonStr = Gson().toJson(list, listType)
        Log.d("jam", "==insertMESSAGE===" + jsonStr)
        SPUtils.put(context, KEY_MESSAGE, jsonStr)
    }


    fun getMessageList(context: Context): ArrayList<MessageBean>? {
        val result = SPUtils.get(context, KEY_MESSAGE, "") as String
        Log.d("jam", "==result===" + result)
        if (TextUtils.isEmpty(result)) return null
        val listType = object : TypeToken<ArrayList<MessageBean>>() {}.type

        val list = ArrayList<MessageBean>()
        Gson().fromJson<ArrayList<MessageBean>>(result, listType)?.filterTo(list) { it.getMessageUserId().equals(""+UserManager.getUserData()!!.id) }
        return list
    }


    fun remove(context: Context, messageBean: MessageBean) {
        val list = getMessageList(context) ?: return Unit
        list.indices
                .filter { messageBean.getMessageId() === list[it].getMessageId() }
                .forEach { list.removeAt(it) }

        val listType = object : TypeToken<List<MessageBean>>() {}.type
        val jsonStr = Gson().toJson(list, listType)
        SPUtils.put(context, KEY_MESSAGE, jsonStr)
    }
}