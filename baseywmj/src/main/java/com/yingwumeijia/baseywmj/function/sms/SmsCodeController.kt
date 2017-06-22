package com.yingwumeijia.baseywmj.function.sms

import android.content.Context
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.commonlibrary.net.HttpUtil
import com.yingwumeijia.commonlibrary.net.subscriber.ProgressSubscriber
import rx.functions.Action1

/**
 * Created by jamisonline on 2017/6/16.
 */
class SmsCodeController(var context: Context, var phone: String, var source: Int, var smsCodeListener: OnSmsCodeListener) {


    //  请求来源：1 = 用户注册，2 = 找回密码，3 = 登录验证，8 = 公司入驻申请，9 = 推客报备客户, 10 = 邀请成为推客

    object SmsCodeSource {

        val register: Int = 1
        val findPassword = 2
        val loginVerify = 3
        val companyEnterApply = 8
        val twitterReport = 9
        val inviteTwitter = 10
    }

    fun sendSms() {
        var ob = Api.service.sendSmsCode(phone, source)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                smsCodeListener.success(source)
            }
        })
    }


    interface OnSmsCodeListener {

        fun success(source: Int)

    }
}