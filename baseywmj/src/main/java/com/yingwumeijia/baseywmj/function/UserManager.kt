package com.yingwumeijia.baseywmj.function

import android.content.Context
import com.yingwumeijia.commonlibrary.utils.SPUtils

/**
 * Created by jamisonline on 2017/6/11.
 */

object UserManager {

    fun isLogin(context: Context): Boolean {
        return SPUtils.get(context, "KEY_LOGIN_STATUS", false) as Boolean
    }

    fun setLoginStatus(context: Context, logIn: Boolean) {
        SPUtils.put(context, "KEY_LOGIN_STATUS", logIn)
    }
}
