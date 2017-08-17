package com.yingwumeijia.android.ywmj.client.function.person

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.orhanobut.logger.Logger
import com.yingwumeijia.android.ywmj.client.function.coupon.details.SiginInfo
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.function.WebViewManager
import com.yingwumeijia.baseywmj.function.personal.BaseLoggedFragment
import com.yingwumeijia.baseywmj.function.personal.PersonMenuFragment
import com.yingwumeijia.baseywmj.function.personal.PersonalFragment
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber

/**
 * Created by jamisonline on 2017/7/3.
 */
class CustomerPersonFragment : PersonalFragment() {


    companion object {
        fun newInstance(): CustomerPersonFragment {
            return CustomerPersonFragment()
        }
    }

    override fun getLoggedHeaderFragment(): BaseLoggedFragment {
        return LoggedFragment.newInstance()
    }


    override fun getMenuFragment(): PersonMenuFragment {
        return MenuFragment.newInstance()
    }

    override fun startScan() {
        IntentIntegrator(activity).initiateScan() // `this` is the current Activity
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Logger.d("CustomerPersonFragment onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        var contentStr: String? = null
        if (result != null) {
            if (result.contents == null) {
                toastWith("取消")
            } else {
                Log.d("jam", "====" + result.contents)
                contentStr = result.contents
                try {
                    val gson = Gson()
                    val info = gson.fromJson(result.contents, SiginInfo::class.java)
                    if (info != null) {
                        if (!TextUtils.isEmpty(info.sign) && info.activityId != 0)
                            Log.d("jam", "====" + result.contents)
                        else {
                            toastWith("识别失败")
                        }
                    } else {
                        toastWith("识别失败")
                    }
                } catch (e: Exception) {
                    if (contentStr!!.contains("http")) {
                        if (!TextUtils.isEmpty(Uri.parse(contentStr).getQueryParameter("qrcodeData"))) {
                            val sina = Uri.parse(contentStr).getQueryParameter("qrcodeData")
                            goToOrderPay(sina)
                        } else {
                            toastWith("识别失败")
                        }
                    } else {
                        toastWith("识别失败")
                    }
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    private fun goToOrderPay(sina: String) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.scanOrderConfirm(sina), object : ProgressSubscriber<String>(activity) {
            override fun _onNext(t: String?) {
                WebViewManager.startFullScreen(activity, t!!)
            }
        })
    }
}