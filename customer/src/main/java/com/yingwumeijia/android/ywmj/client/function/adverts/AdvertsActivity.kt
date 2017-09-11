package com.yingwumeijia.android.ywmj.client.function.adverts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.guidance.CustomerGuidanceActivity
import com.yingwumeijia.android.ywmj.client.function.home.CustomerMainActivity
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.AdvertsBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import kotlinx.android.synthetic.main.adverts_act.*

/**
 * Created by jamisonline on 2017/8/2.
 */
class AdvertsActivity : JBaseActivity() {


    val advertsBean by lazy { intent.getSerializableExtra(Constant.KEY_CURRENT) as AdvertsBean }

    val cycles_time: Long = 1000

    val total_time: Long by lazy { ((advertsBean.playTime + 1) * 1000).toLong() }

    val countDownTimer: CountDownTimer by lazy {
        object : CountDownTimer(total_time, cycles_time) {
            override fun onFinish() {
                didNext()
            }

            override fun onTick(millisUntilFinished: Long) {
                if ((millisUntilFinished / cycles_time).toInt() == (advertsBean.playTime + 1)) {

                } else {
                    tv_time.text = (millisUntilFinished / cycles_time).toString() + "s"
                }
            }
        }
    }

    companion object {

        fun start(context: Context, advertsBean: AdvertsBean) {
            val starter = Intent(context, AdvertsActivity::class.java)
            starter.putExtra(Constant.KEY_CURRENT, advertsBean)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adverts_act)

        tv_time.text = advertsBean.playTime.toString() + "s"

        JImageLolder.load(this, iv_adverts, advertsBean.advertsImage)

        countDownTimer.start()

        btn_skip.setOnClickListener {
            countDownTimer.onFinish()
            countDownTimer.cancel()
        }
    }

    fun didNext() {
        UserManager.setAdversId(context, advertsBean.id)
        close()
        if (UserManager.isFirst(context)) {
            CustomerGuidanceActivity.start(context)
        } else {
            CustomerMainActivity.start(context)
        }
    }
}