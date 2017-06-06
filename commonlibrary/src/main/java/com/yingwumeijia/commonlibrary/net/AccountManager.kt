package com.yingwumeijia.commonlibrary.net

import android.content.Context
import com.yingwumeijia.commonlibrary.base.BaseApplication

import com.yingwumeijia.commonlibrary.entity.UserSession
import com.yingwumeijia.commonlibrary.utils.SPUtils


/**
 * Created by Jam on 2017/2/17 上午10:39.
 * Describe:
 */

object AccountManager {

    private val KEY_ACCOUNT_ASSESS_TOKEN = "KEY_ACCOUNT_ASSESS_TOKEN"
    private val KEY_ACCOUNT_SESSION_ID = "KEY_ACCOUNT_SESSION_ID"
    private val KEY_ACCOUNT_REFRESH_TOKEN = "KEY_ACCOUNT_REFRESH_TOKEN"

    var isFirst312: Boolean
        get() = SPUtils.get(BaseApplication.appContext(), "KEY_FIRST_312", false) as Boolean
        set(b) = SPUtils.put(BaseApplication.appContext(), "KEY_FIRST_312", b)

    fun refreshAccount(userSession: UserSession) {
        refreshAccessToken(BaseApplication.appContext(), userSession.accessToken)
        refreshSessionId(BaseApplication.appContext(), userSession.sessionId)
        refreshRefreshToken(BaseApplication.appContext(), userSession.refreshToken)
    }

    fun accessToken(): String {
        return SPUtils.get(BaseApplication.appContext(), KEY_ACCOUNT_ASSESS_TOKEN, "") as String
    }

    fun refreshAccessToken(context: Context, token: String) {
        SPUtils.put(context, KEY_ACCOUNT_ASSESS_TOKEN, token)
    }


    fun sessionId(): String {
        return SPUtils.get(BaseApplication.appContext(), KEY_ACCOUNT_SESSION_ID, "") as String
    }

    fun refreshSessionId(context: Context, token: String) {
        SPUtils.put(context, KEY_ACCOUNT_SESSION_ID, token)
    }

    fun refreshToken(): String {
        return SPUtils.get(BaseApplication.appContext(), KEY_ACCOUNT_REFRESH_TOKEN, "") as String
    }

    fun refreshRefreshToken(context: Context, token: String) {
        SPUtils.put(context, KEY_ACCOUNT_REFRESH_TOKEN, token)
    }

}
