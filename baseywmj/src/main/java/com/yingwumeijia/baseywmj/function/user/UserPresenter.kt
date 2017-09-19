package com.yingwumeijia.baseywmj.function.user

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.widget.Toast
import com.netease.nim.uikit.NimUIKit
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.auth.LoginInfo
import com.orhanobut.logger.Logger
import com.umeng.analytics.MobclickAgent
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.RegisterResultBean
import com.yingwumeijia.baseywmj.entity.bean.TokenBean
import com.yingwumeijia.baseywmj.entity.bean.UserBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.sms.SmsCodeController
import com.yingwumeijia.baseywmj.im.IMManager
import com.yingwumeijia.baseywmj.nimim.NIMIMCache
import com.yingwumeijia.baseywmj.utils.RSAUtils
import com.yingwumeijia.baseywmj.utils.VerifyUtils
import com.yingwumeijia.baseywmj.utils.net.AccountManager
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent
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
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<UserBean>(context) {
            override fun _onNext(t: UserBean?) {
                AccountManager.refreshAccount(t!!.userSession)
                getIMToken(t!!)
            }
        })
    }


    private fun cacheLoginInfo(account: String, token: String) {
        NIMIMCache.setAccount(account.toLowerCase())
        UserManager.refreshNIMLogin(context, LoginInfo(account, token))
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

        HttpUtil.getInstance().toNolifeSubscribe(Api.service.register(LoginBean(identityInfoBean, userExtensionInfoBean, invaCode)), object : ProgressSubscriber<RegisterResultBean>(context) {
            override fun _onNext(t: RegisterResultBean?) {
                if (t!!.isNeedConfirm) {
                    AlertDialog
                            .Builder(context)
                            .setTitle(R.string.dialog_title)
                            .setMessage(t.message)
                            .setPositiveButton(R.string.btn_confirm, { dialog, which -> confirm(phone, t.token) })
                            .setNegativeButton(R.string.btn_cancel, { dialog, which -> T.showShort(context, "已取消") })
                            .show()
                } else {
                    AccountManager.refreshAccount(t!!.customerDto.userSession)
                    getIMToken(t!!.customerDto)
                }
            }
        })
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

        HttpUtil.getInstance().toNolifeSubscribe(Api.service.findPassword(LoginBean(identityInfoBean, userExtensionInfoBean)), object : ProgressSubscriber<UserBean>(context) {
            override fun _onNext(t: UserBean?) {
                AccountManager.refreshAccount(t!!.userSession)
                getIMToken(t!!)
            }
        })
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
                AccountManager.refreshAccount(t!!.userSession)
                getIMToken(t!!)
            }
        }, publishSubject, false)

    }


    /**
     * 发送短信验证码
     */
    override fun sendSmsCode(phone: String, source: Int) {
        if (!verifyPhone(phone)) return Unit
        SmsCodeController(context, phone, source, object : SmsCodeController.OnSmsCodeListener {
            override fun success(source: Int) {
                T.showShort(context, R.string.sendSmsCode_succ)
                (view as UserContract.SendSmsView).launchSendSmsButtonText()
                countDownTimer.start()
            }
        }).sendSms()
    }


//    /**
//     * 登录成功前的操作，链接融云
//     */
//    fun didLoginSuccessBefor(userBean: UserBean) {
//        AccountManager.refreshAccount(userBean.userSession)
//        connectRong(userBean)
//    }

    fun getIMToken(userBean: UserBean) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getIMToken(), object : SimpleSubscriber<TokenBean>(context) {
            override fun _onNext(t: TokenBean?) {
                Logger.d("IMManager" + t!!.token)
                IMManager.tokenPut(context, t!!.token)
                cacheLoginInfo(t.imUid, t!!.token)
                logigToNIM(userBean)
            }

        })
    }


    /**
     * 登录成功
     */
    fun didLoginSuccess(userBean: UserBean) {
        UserManager.cacheUserData(userBean)
        AccountManager.refreshAccount(userBean.userSession)
        UserManager.setLoginStatus(context, true)
        MobclickAgent.onProfileSignIn(userBean.id.toString())
        if (userResponseCallBack != null) {
            userResponseCallBack!!.success(userBean)
            userResponseCallBack!!.completed()
        }
    }


    /**
     * 登录到网易云信
     */
    fun logigToNIM(userBean: UserBean) {


        NimUIKit.doLogin(UserManager.getNIMLoginInfo(context), object : RequestCallback<LoginInfo> {
            override fun onFailed(code: Int) {
                Logger.d(code)
                if (code == 302 || code == 404) {
                    Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            override fun onException(p0: Throwable?) {
                Logger.d(p0!!.message)
                p0.printStackTrace()
                Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
            }

            override fun onSuccess(p0: LoginInfo?) {
                didLoginSuccess(userBean)
            }
        })
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
        if (!VerifyUtils.verifySmsCode(smsCode)) {
            (view as UserContract.SendSmsView).showInputErrorFromSmsCode()
            return false
        }
        return true
    }
}