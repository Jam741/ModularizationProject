package com.yingwumeijia.android.worker.function.home

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.yingwumeijia.android.worker.function.conversation.EmployeeConversationFragment
import com.yingwumeijia.android.worker.function.person.EmployeePersonFragment
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.entity.bean.TokenBean
import com.yingwumeijia.baseywmj.function.active.ActiveFragment
import com.yingwumeijia.baseywmj.function.caselist.CaseListFragment
import com.yingwumeijia.baseywmj.function.main.MainActivity
import com.yingwumeijia.baseywmj.function.user.login.LoginActivity
import com.yingwumeijia.baseywmj.im.IMManager
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import java.util.*

/**
 * Created by jamisonline on 2017/7/3.
 */
class EmployeeMainActivity : MainActivity() {

    companion object {
        fun start(context: Activity) {
            RongIM.getInstance().startConversationList(context, null)
//            val starter = Intent(context, EmployeeMainActivity::class.java)
//            context.startActivity(starter)
        }
    }

    override fun getTitles(): Array<String> {
        return arrayOf("作品", "优惠", "聊天", "我的")
    }

    override fun getIconUnselectIds(): IntArray {
        return intArrayOf(R.mipmap.tab_work_ico, R.mipmap.tab_chip_ic, R.mipmap.tab_messgae_ico, R.mipmap.tab_mine_ico)
    }

    override fun getIconSelectIds(): IntArray {
        return intArrayOf(R.mipmap.tab_work_light_ico, R.mipmap.tab_chip_light_ic, R.mipmap.tab_message_light_ico, R.mipmap.tab_mine_light_ico)
    }


    override fun getFragments(): ArrayList<JBaseFragment> {
        return arrayListOf(CaseListFragment.newInstance(false), ActiveFragment.newInstance(), EmployeeConversationFragment.newInstance(), EmployeePersonFragment.newInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getLastActivity()
    }

//
//    private fun isFromPush(uri: Uri) {
//        //push
//        if (uri.scheme == "rong" && uri.getQueryParameter("push") != null) {
//
//            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
//            if (intent.data.getQueryParameter("push") == "true") {
//                enterActivity()
//            }
//
//        } else {//通知过来
//            //程序切到后台，收到消息后点击进入,会执行这里
//            if (RongIM.getInstance().currentConnectionStatus == RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED) {
//                enterActivity()
//            } else {
////                CustomerMainActivity.start(context)
////                close()
//            }
//        }
//
//    }

//
//    private fun getLastActivity() {
//        HttpUtil.getInstance().toNolifeSubscribe(Api.service.activeLast(), object : Subscriber<Int>() {
//            override fun onCompleted() {
//
//            }
//
//            override fun onError(e: Throwable?) {
//            }
//
//            override fun onNext(t: Int?) {
//                SPUtils.put(context, "KEY_LAST_ACTIVITY", t)
//                if (SPUtils.get(context, "KEY_LAST_ACTIVITY", 0) == t) {
//                    tl_main.hideMsg(1)
//                } else {
//                    tl_main.showDot(1)
//                    tl_main.setMsgMargin(1, -5f, 5f)
//                }
//            }
//        })
//
//    }


    /**
     * 收到 push 消息后，选择进入哪个 Activity
     * 如果程序缓存未被清理，进入 MainActivity
     * 程序缓存被清理，进入 LoginActivity，重新获取token
     *
     *
     * 作用：由于在 manifest 中 intent-filter 是配置在 ConversationListActivity 下面，所以收到消息后点击notifacition 会跳转到 DemoActivity。
     * 以跳到 MainActivity 为例：
     * 在 ConversationListActivity 收到消息后，选择进入 MainActivity，这样就把 MainActivity 激活了，当你读完收到的消息点击 返回键 时，程序会退到
     * MainActivity 页面，而不是直接退回到 桌面。
     */
    private fun enterActivity() {
        val token = IMManager.token(context)
        if (TextUtils.isEmpty(token)) {
            LoginActivity.start(context, false)
            close()
        } else {
            reconnect()
        }
    }

    private fun reconnect() {
        RongIM.connect(IMManager.token(context), object : RongIMClient.ConnectCallback() {
            override fun onTokenIncorrect() {
                Log.e("CustomerMainActivity", "---onTokenIncorrect--")
                getTokenFormSever()
            }

            override fun onSuccess(s: String) {
                Log.i("CustomerMainActivity", "---onSuccess--" + s)
                progressDialog.dismiss()
//                CustomerMainActivity.start(context)
//                close()
            }

            override fun onError(e: RongIMClient.ErrorCode) {
                Log.e("CustomerMainActivity", "---onError--" + e)
            }
        })

    }


    private fun getTokenFormSever() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getIMToken(), object : SimpleSubscriber<TokenBean>(context) {
            override fun _onNext(t: TokenBean?) {
                if (t != null) IMManager.tokenPut(context, t.token)
                reconnect()
            }

        })
    }
}