package com.yingwumeijia.baseywmj.function.enter

import android.content.Context
import android.os.CountDownTimer
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.ApplyJoinBean
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber

/**
 * Created by jamisonline on 2017/7/8.
 */
class EditEnterPresenter(var context: Context, var view: EditEnterContract.View) : EditEnterContract.Presenter {

    private val toal_time = 60000
    private val cycles_time = 1000
    private var myCountDownTimer: MyCountDownTimer? = null

    override fun sendSmsCode(phoneNum: String?) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.sendSmsCode(phoneNum!!, 8), object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                view.lockSendSmsButton()
                if (myCountDownTimer != null) {
                    myCountDownTimer!!.cancel()
                    myCountDownTimer = null
                }
                myCountDownTimer = MyCountDownTimer(toal_time.toLong(), cycles_time.toLong())
                myCountDownTimer!!.start()
            }

        })
    }

    override fun commit(applyJoinBean: ApplyJoinBean?) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.componyJoin(applyJoinBean!!), object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                view.joinSuccess()
            }
        })
    }

    /**
     * 短信验证码倒计时
     */
    internal inner class MyCountDownTimer
    /**
     * @param millisInFuture    The number of millis in the future from the call
     * *                          to [.start] until the countdown is done and [.onFinish]
     * *                          is called.
     * *
     * @param countDownInterval The interval along the way to receive
     * *                          [.onTick] callbacks.
     */
    (millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            view.setSendSmsCodeText((millisUntilFinished / cycles_time).toString() + "s")
        }

        override fun onFinish() {
            view.unLockSendSmsButton()
        }
    }
}