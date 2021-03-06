package com.yingwumeijia.baseywmj.function.setting

import android.app.Activity
import com.netease.nim.uikit.NimUIKit
import com.netease.nimlib.sdk.NIMClient
import com.tencent.smtt.sdk.WebStorage
import com.umeng.analytics.MobclickAgent
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.user.login.LoginActivity
import com.yingwumeijia.baseywmj.im.IMManager
import com.yingwumeijia.baseywmj.utils.net.AccountManager
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.DataCleanManager
import com.yingwumeijia.commonlibrary.utils.DataCleanManager.getCacheSize
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import com.netease.nimlib.sdk.auth.AuthService



/**
 * Created by jamisonline on 2017/6/30.
 */
class SettingPresenter(var activity: Activity, var view: SettingContract.View) : SettingContract.Presenter {


    override fun resume() {
        view.showLoginOutButton(UserManager.isLogin(activity))
    }

    override fun start() {
        view.showCurrentCache(getCacheSize())
    }

    override fun clearnCache() {
        Observable
                .create(Observable.OnSubscribe<Any> { subscriber ->
                    DataCleanManager.cleanInternalCache(activity)
                    subscriber.onNext(Unit)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(object : ProgressSubscriber<Any>(activity) {
                    override fun _onNext(t: Any?) {
                        view.showCurrentCache(getCacheSize())
                    }
                })
    }

    override fun loginOut() {
        val ob = Api.service.setLogout()
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(activity) {
            override fun _onNext(t: String?) {
                IMManager.loginOut()
                WebStorage.getInstance().deleteAllData()
                view.showLoginOutButton(false)
                MobclickAgent.onProfileSignOff()
                UserManager.setLoginStatus(activity, false)
                UserManager.clearNIMLogin(activity)
                AccountManager.clearnAccount()
                LoginActivity.startCurrent(activity)
            }
        })
    }


    fun getCacheSize(): String {
        try {
            return "当前缓存 " + DataCleanManager.getCacheSize(activity.cacheDir)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "当前缓存 0"
    }

}