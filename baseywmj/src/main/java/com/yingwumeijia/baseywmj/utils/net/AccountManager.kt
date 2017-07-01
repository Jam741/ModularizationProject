package com.yingwumeijia.baseywmj.utils.net

import com.yingwumeijia.baseywmj.entity.UserSession
import com.yingwumeijia.commonlibrary.base.BaseApplication
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
        get() = SPUtils.get(com.yingwumeijia.commonlibrary.base.BaseApplication.Companion.appContext(), "KEY_FIRST_312", false) as Boolean
        set(b) = SPUtils.put(com.yingwumeijia.commonlibrary.base.BaseApplication.Companion.appContext(), "KEY_FIRST_312", b)

    fun refreshAccount(userSession: UserSession) {
        AccountManager.refreshAccessToken(BaseApplication.appContext(), userSession.accessToken)
        AccountManager.refreshSessionId(BaseApplication.appContext(), userSession.sessionId)
        AccountManager.refreshRefreshToken(BaseApplication.appContext(), userSession.refreshToken)
    }

    fun clearnAccount() {
        AccountManager.refreshAccessToken(BaseApplication.appContext(), "")
        AccountManager.refreshSessionId(BaseApplication.appContext(), "")
        AccountManager.refreshRefreshToken(BaseApplication.appContext(), "")
    }

    fun accessToken(): String {
        return SPUtils.get(com.yingwumeijia.commonlibrary.base.BaseApplication.Companion.appContext(), com.yingwumeijia.baseywmj.utils.net.AccountManager.KEY_ACCOUNT_ASSESS_TOKEN, "") as String
    }

    fun refreshAccessToken(context: android.content.Context, token: String) {
        SPUtils.put(context, com.yingwumeijia.baseywmj.utils.net.AccountManager.KEY_ACCOUNT_ASSESS_TOKEN, token)
    }


    fun sessionId(): String {
        return SPUtils.get(com.yingwumeijia.commonlibrary.base.BaseApplication.Companion.appContext(), com.yingwumeijia.baseywmj.utils.net.AccountManager.KEY_ACCOUNT_SESSION_ID, "") as String
    }

    fun refreshSessionId(context: android.content.Context, token: String) {
        SPUtils.put(context, com.yingwumeijia.baseywmj.utils.net.AccountManager.KEY_ACCOUNT_SESSION_ID, token)
    }

    fun refreshToken(): String {
        return com.yingwumeijia.commonlibrary.utils.SPUtils.get(com.yingwumeijia.commonlibrary.base.BaseApplication.Companion.appContext(), com.yingwumeijia.baseywmj.utils.net.AccountManager.KEY_ACCOUNT_REFRESH_TOKEN, "") as String
    }

    fun refreshRefreshToken(context: android.content.Context, token: String) {
        SPUtils.put(context, com.yingwumeijia.baseywmj.utils.net.AccountManager.KEY_ACCOUNT_REFRESH_TOKEN, token)
    }

}
