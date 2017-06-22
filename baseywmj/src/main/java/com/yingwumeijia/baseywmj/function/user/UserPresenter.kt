package com.yingwumeijia.baseywmj.function.user

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.RegisterResultBean
import com.yingwumeijia.baseywmj.entity.bean.TokenBean
import com.yingwumeijia.baseywmj.entity.bean.UserBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.sms.SmsCodeController
import com.yingwumeijia.baseywmj.utils.RSAUtils
import com.yingwumeijia.baseywmj.utils.VerifyUtils
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


    val total_time: Long = 60000
    val cycles_time: Long = 1000

    val countDownTimer: CountDownTimer by lazy {
        object : CountDownTimer(total_time, cycles_time) {

            override fun onTick(millisUntilFinished: Long) {
                (view as UserContract.SendSmsView).didSendSmsButtonText((millisUntilFinished / cycles_time).toString() + "s")
            }

            override fun onFinish() {
                (view as UserContract.SendSmsView).resetSendSmsButtom()
            }
        }
    }

    val userExtensionInfoBean: LoginBean.UserExtensionInfoBean by lazy {
        createUserExtensionInfoBean()
    }


    /**
     * 创建登录用户扩展信息
     */
    private fun createUserExtensionInfoBean(): LoginBean.UserExtensionInfoBean {
        val appInfo = AppInfo(context)
        val userExtensionInfoBean = LoginBean.UserExtensionInfoBean()
        userExtensionInfoBean.equipmentNo = appInfo.deviceId
        userExtensionInfoBean.equipmentModel = appInfo.deviceName
        userExtensionInfoBean.systemInfo = "android:" + Build.VERSION.RELEASE
        userExtensionInfoBean.netType = NetworkUtils.getNetworkType(context).toString()
        return userExtensionInfoBean
    }


    /**
     * 登录
     */
    override fun login(phone: String, password: String) {

        if (!verifyPhone(phone) || !verifyPassword(password)) return Unit

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


    /**
     * 注册
     */
    override fun register(phone: String, password: String, smsCode: String, invaCode: String?) {

        if (!verifyPhone(phone) || !verifyPassword(password) || !verifySmsCode(smsCode)) return Unit


        val identityInfoBean = LoginBean.IdentityInfoBean()
        identityInfoBean.phone = phone
        identityInfoBean.password = RSAUtils.encryptByPublicKey(password)
        identityInfoBean.smsCode = smsCode

        val ob = Api.service.register(LoginBean(identityInfoBean, userExtensionInfoBean, invaCode))
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
     * 找回密码
     */
    override fun findPasssword(phone: String, password: String, smsCode: String) {
        if (!verifyPhone(phone) || !verifyPassword(password) || !verifySmsCode(smsCode)) return Unit
        val identityInfoBean = LoginBean.IdentityInfoBean()
        identityInfoBean.phone = phone
        identityInfoBean.password = RSAUtils.encryptByPublicKey(password)
        identityInfoBean.smsCode = smsCode

        val ob = Api.service.findPassword(LoginBean(identityInfoBean, userExtensionInfoBean))
        HttpUtil.getInstance().toSimpleSubscribe(ob, object : ProgressSubscriber<UserBean>(context) {
            override fun _onNext(t: UserBean?) {
                didLoginSuccess(t!!)
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


    /**
     * 发送短信验证码
     */
    override fun sendSmsCode(phone: String, source: Int) {
        if (!verifySmsCode(phone)) return Unit
        SmsCodeController(context, phone, source, object : SmsCodeController.OnSmsCodeListener {
            override fun success(source: Int) {
                T.showShort(context, R.string.sendSmsCode_succ)
                (view as UserContract.SendSmsView).launchSendSmsButtonText()
                countDownTimer.start()
            }
        })
    }

    fun getIMToken(userBean: UserBean) {
        var ob = Api.service.getIMToken()
        HttpUtil.getInstance().toSimpleSubscribe(ob, object : ProgressSubscriber<TokenBean>(context) {
            override fun _onNext(t: TokenBean?) {

            }

        }, publishSubject, true)
    }


    /**
     * 登录成功
     */
    fun didLoginSuccess(userBean: UserBean) {
        UserManager.cacheUserData(userBean)
        if (userResponseCallBack != null) {
            userResponseCallBack!!.success(userBean)
            userResponseCallBack!!.completed()
        }
    }

    /**
     * 链接融云
     */
    fun connectRong(token: String, userBean: UserBean) {


    }


    /**
     * 校验电话号码
     */
    fun verifyPhone(phone: String?): Boolean {
        if (!VerifyUtils.verifyMobilePhoneNumber(phone)) {
            view.showInputErrorFromPhone()
            return false
        }
        return true
    }


    /**
     * 校验密码
     */
    fun verifyPassword(password: String?): Boolean {
        if (!VerifyUtils.verifyPassword(password)) {
            view.showInputErrorFromPassword()
            return false
        }
        return true
    }

    /**
     * 校验密码
     */
    fun verifySmsCode(smsCode: String?): Boolean {
        if (!VerifyUtils.verifyPassword(smsCode)) {
            (view as UserContract.SendSmsView).showInputErrorFromSmsCode()
            return false
        }
        return true
    }
}