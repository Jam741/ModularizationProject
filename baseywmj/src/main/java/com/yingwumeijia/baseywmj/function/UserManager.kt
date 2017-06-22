package com.yingwumeijia.baseywmj.function

import android.content.Context
import com.orhanobut.hawk.Hawk
import com.yingwumeijia.baseywmj.entity.bean.UserBean
import com.yingwumeijia.commonlibrary.utils.SPUtils

/**
 * Created by jamisonline on 2017/6/11.
 */

object UserManager {

    val KEY_USER_CACHE_DATA = "KEY_USER_CACHE_DATA"

    fun isLogin(context: Context): Boolean {
        return SPUtils.get(context, "KEY_LOGIN_STATUS", false) as Boolean
    }

    fun setLoginStatus(context: Context, logIn: Boolean) {
        SPUtils.put(context, "KEY_LOGIN_STATUS", logIn)
    }

    fun cacheUserData(userBean: UserBean) {
        Hawk.put(KEY_USER_CACHE_DATA, userBean)
    }

    fun getUserData(): UserBean? {
        var userBean: UserBean? = null
        try {
            userBean = Hawk.get<UserBean>(KEY_USER_CACHE_DATA)
        } catch (e: Exception) {
            throw NullPointerException("not cache user data")
        }
        return userBean
    }

    fun chearUserData() {
        if (Hawk.contains(KEY_USER_CACHE_DATA))
            Hawk.delete(KEY_USER_CACHE_DATA)
    }
}
