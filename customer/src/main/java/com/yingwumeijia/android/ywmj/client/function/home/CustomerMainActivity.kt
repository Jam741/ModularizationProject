package com.yingwumeijia.android.ywmj.client.function.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.orhanobut.logger.Logger
import com.yingwumeijia.android.ywmj.client.function.conversation.CustomerConversationListFragment
import com.yingwumeijia.android.ywmj.client.function.person.CustomerPersonFragment
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.entity.bean.TokenBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.active.ActiveFragment
import com.yingwumeijia.baseywmj.function.caselist.CaseListFragment
import com.yingwumeijia.baseywmj.function.im.ConversationListFragment
import com.yingwumeijia.baseywmj.function.main.MainActivity
import com.yingwumeijia.baseywmj.function.user.login.LoginActivity
import com.yingwumeijia.baseywmj.function.web.OneWebFragment
import com.yingwumeijia.baseywmj.im.IMManager
import com.yingwumeijia.baseywmj.option.PATHUrlConfig
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import java.util.*

/**
 * Created by jamisonline on 2017/7/3.
 */
class CustomerMainActivity : MainActivity() {


    companion object {
        fun start(context: Activity) {
            RongIM.getInstance().startConversationList(context, null)
//            val starter = Intent(context, CustomerMainActivity::class.java)
//            context.startActivity(starter)
        }
    }

    override fun getTitles(): Array<String> {
        return arrayOf("作品", "优惠", "保障", "聊天", "我的")
    }

    override fun getIconUnselectIds(): IntArray {
        return intArrayOf(R.mipmap.tab_work_ico, R.mipmap.tab_favourable_ico, R.mipmap.tab_bz_ico, R.mipmap.tab_messgae_ico, R.mipmap.tab_mine_ico)
    }

    override fun getIconSelectIds(): IntArray {
        return intArrayOf(R.mipmap.tab_work_light_ico, R.mipmap.tab_favourable_light_ico, R.mipmap.tab_bz_light_ico, R.mipmap.tab_message_light_ico, R.mipmap.tab_mine_light_ico)
    }

    override fun getFragments(): ArrayList<JBaseFragment> {
        return arrayListOf(CaseListFragment.newInstance(false), ActiveFragment.newInstance(), OneWebFragment.newInstance("http://192.168.28.50:8083/src/template/safeguard/safeguard.html"), CustomerConversationListFragment.newInstance(), CustomerPersonFragment.newInstance())

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.data != null && UserManager.isLogin(context))
            isFromPush(intent.data)
    }

    private fun isFromPush(uri: Uri) {
        //push
        if (uri.scheme == "rong" && uri.getQueryParameter("push") != null) {

            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
            if (intent.data.getQueryParameter("push") == "true") {
                enterActivity()
            }

        } else {//通知过来
            //程序切到后台，收到消息后点击进入,会执行这里
            if (RongIM.getInstance().currentConnectionStatus == RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED) {
                enterActivity()
            } else {
//                CustomerMainActivity.start(context)
//                close()
            }
        }

    }


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
                getTokenFormSever()
                Log.e("CustomerMainActivity", "---onTokenIncorrect--")
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
