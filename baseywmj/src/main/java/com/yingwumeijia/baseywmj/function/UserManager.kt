package com.yingwumeijia.baseywmj.function

import android.app.Activity
import android.content.Context
import com.orhanobut.hawk.Hawk
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.UserBean
import com.yingwumeijia.baseywmj.function.user.login.LoginActivity
import com.yingwumeijia.commonlibrary.utils.SPUtils

/**
 * Created by jamisonline on 2017/6/11.
 */

object UserManager {

    val KEY_USER_CACHE_DATA = "KEY_USER_CACHE_DATA"
    val KEY_TWITTER_CACHE_DATA = "KEY_TWITTER_CACHE_DATA"

    val KEY_IS_FIRST_LOGIN = "KEY_IS_FIRST_LOGIN"

    val KEY_GUIDANCE_CASE_DETAILS = "KEY_GUIDANCE_CASE_DETAILS"

    val KEY_ADVERTS_ID = "KEY_ADVERTS_ID"
//    val KEY_ADVERTS_VIEWED = "KEY_ADVERTS_VIEWED"

    fun advertsId(context: Context): Int {
        return SPUtils.get(context, KEY_ADVERTS_ID, 0) as Int
    }

    fun setAdversId(context: Context, id: Int) {
        SPUtils.put(context, KEY_ADVERTS_ID, id)
    }
//
//    fun setAdversView(context: Context, viewed: Boolean) {
//        SPUtils.put(context, KEY_ADVERTS_VIEWED, true)
//    }
//
//    fun adversViewed(context: Context): Boolean {
//        return SPUtils.get(context, KEY_ADVERTS_VIEWED, false) as Boolean
//    }


    fun guidanceCaseDetailsNeedShow(context: Context): Boolean {
        return SPUtils.get(context, KEY_GUIDANCE_CASE_DETAILS, true) as Boolean
    }


    fun setNotGuidanceCaseDetailsNeedShow(context: Context) {
        SPUtils.put(context, KEY_GUIDANCE_CASE_DETAILS, false)
    }

    fun isFirst(context: Context): Boolean {
        return SPUtils.get(context, KEY_IS_FIRST_LOGIN, true) as Boolean
    }

    fun setNotFirst(context: Context) {
        SPUtils.put(context, KEY_IS_FIRST_LOGIN, false)
    }

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

    fun refreshProtrait(protrait: String) {

    }

    fun refreshNikeName(nikeName: String) {

    }

    /**
     * 执行用户权限的操作 需要先通过先决条件
     */
    fun precedent(context: Activity): Boolean {
        if (isLogin(context)) {
            return true
        } else {
            LoginActivity.startCurrent(context)
            return false
        }
    }


    /***********************推客相关*******************************/
    fun cacheTwitterStatus(twitterStatus: Int) {
        Hawk.put(KEY_TWITTER_CACHE_DATA, twitterStatus)
    }


    fun getTwitterStatus(): Int {
        return Hawk.get(KEY_TWITTER_CACHE_DATA, Constant.DEFAULT_INT_VALUE)
    }
}
