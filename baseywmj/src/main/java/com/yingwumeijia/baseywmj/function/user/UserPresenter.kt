package com.yingwumeijia.baseywmj.function.user

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.RegisterResultBean
import com.yingwumeijia.baseywmj.entity.bean.TokenBean
import com.yingwumeijia.baseywmj.entity.bean.UserBean
import com.yingwumeijia.baseywmj.function.user.login.LoginBean
import com.yingwumeijia.baseywmj.utils.RSAUtils
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent
import com.yingwumeijia.commonlibrary.net.HttpUtil
import com.yingwumeijia.commonlibrary.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.AppInfo
import com.yingwumeijia.commonlibrary.utils.NetworkUtils
import com.yingwumeijia.commonlibrary.utils.T
import rx.subjects.PublishSubject

/**
 * Created by jamisonline on 2017/6/15.
 */
class UserPresenter(var context: Context, var view: UserContract.View, var publishSubject: PublishSubject<ActivityLifeCycleEvent>, var userResponseCallBack: UserResponseCallBack?) : UserContract.Presenter {


    val userExtensionInfoBean: LoginBean.UserExtensionInfoBean by lazy {
        createUserExtensionInfoBean()
    }

    private fun createUserExtensionInfoBean(): LoginBean.UserExtensionInfoBean {
        val appInfo = AppInfo(context)
        val userExtensionInfoBean = LoginBean.UserExtensionInfoBean()
        userExtensionInfoBean.equipmentNo = appInfo.deviceId
        userExtensionInfoBean.equipmentModel = appInfo.deviceName
        userExtensionInfoBean.systemInfo = "android:" + Build.VERSION.RELEASE
        userExtensionInfoBean.netType = NetworkUtils.getNetworkType(context).toString()
        return userExtensionInfoBean
    }

    override fun login(phone: String, password: String) {
        val identityInfoBean = LoginBean.IdentityInfoBean()
        identityInfoBean.phone = phone
        identityInfoBean.password = RSAUtils.encryptByPublicKey(password)

        var ob = Api.service.login(LoginBean(identityInfoBean, userExtensionInfoBean))
        HttpUtil.getInstance().toSimpleSubscribe(ob, object : ProgressSubscriber<UserBean>(context) {
            override fun _onNext(t: UserBean?) {
                didLoginSuccess(t!!)
            }
        }, publishSubject, true)
    }

    override fun register(phone: String, password: String, smsCode: String, invaCode: String) {
        val identityInfoBean = LoginBean.IdentityInfoBean()
        identityInfoBean.phone = phone
        identityInfoBean.password = RSAUtils.encryptByPublicKey(password)

        var ob = Api.service.register(LoginBean(identityInfoBean, userExtensionInfoBean))
        HttpUtil.getInstance().toSimpleSubscribe(ob, object : ProgressSubscriber<RegisterResultBean>(context) {
            override fun _onNext(t: RegisterResultBean?) {
                if (t!!.isNeedConfirm) {
                    AlertDialog
                            .Builder(context)
                            .setTitle(R.string.dialog_title)
                            .setMessage(t.message)
                            .setPositiveButton(R.string.btn_confirm, DialogInterface.OnClickListener { dialog, which -> confirm(phone, t.token) })
                            .setNegativeButton(R.string.btn_cancel, DialogInterface.OnClickListener { dialog, which -> T.showShort(context, "已取消") })
                            .show()
                } else {
                    didLoginSuccess(t!!.customerDto)
                }
            }
        }, publishSubject, true)
    }


    /**
     * 确认开通角色
     */
    private fun confirm(phone: String, token: String?) {
        val identityInfoBean = LoginBean.IdentityInfoBean()
        identityInfoBean.phone = phone
        identityInfoBean.token = token
        var ob = Api.service.confirm(LoginBean(identityInfoBean, userExtensionInfoBean))
        HttpUtil.getInstance().toSimpleSubscribe(ob, object : ProgressSubscriber<UserBean>(context) {
            override fun _onNext(t: UserBean?) {
                didLoginSuccess(t!!)
            }
        }, publishSubject, false)

    }

    override fun sendSmsCode(phone: String, source: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getIMToken(userBean: UserBean) {
        var ob = Api.service.getIMToken()
        HttpUtil.getInstance().toSimpleSubscribe(ob, object : ProgressSubscriber<TokenBean>(context) {
            override fun _onNext(t: TokenBean?) {

            }

        }, publishSubject, true)
    }


    fun didLoginSuccess(userBean: UserBean) {
        if (userResponseCallBack != null)
            userResponseCallBack!!.success(userBean)
    }

    /**
     * 链接融云
     */
    fun connectRong(token: String, userBean: UserBean) {


    }
}