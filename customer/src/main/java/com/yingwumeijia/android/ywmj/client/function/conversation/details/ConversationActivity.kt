package com.yingwumeijia.android.ywmj.client.function.conversation.details

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupWindow
import com.orhanobut.logger.Logger
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.home.CustomerMainActivity
import com.yingwumeijia.baseywmj.base.BaseConversationActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.CommonLanguage
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.casedetails.CaseDetailActivity
import com.yingwumeijia.baseywmj.function.user.login.LoginActivity
import com.yingwumeijia.baseywmj.im.ConversationDetailsActivity
import com.yingwumeijia.baseywmj.im.IMManager
import com.yingwumeijia.commonlibrary.utils.KeyboardChangeListener
import com.yingwumeijia.commonlibrary.utils.SPUtils
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yingwumeijia.commonlibrary.utils.TextViewUtils
import io.rong.imkit.RongIM
import io.rong.imkit.fragment.ConversationFragment
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.conversation_act.*
import kotlinx.android.synthetic.main.conversation_beginner_guidance.*
import kotlinx.android.synthetic.main.conversation_status.*
import kotlinx.android.synthetic.main.conversation_toolbar.*
import java.util.*

/**
 * Created by jamisonline on 2017/7/14.
 */
class ConversationActivity : BaseConversationActivity(), ConversationControact.View {



    val TAG by lazy { ConversationActivity::class.java.simpleName }

    val uri by lazy { intent.data }

    val sessionId by lazy { uri.getQueryParameter("targetId") }

    val titleStr: String? by lazy { uri.getQueryParameter("title") }

    val mConversationType by lazy { Conversation.ConversationType.valueOf(uri.lastPathSegment.toUpperCase(Locale.getDefault())) }

    var isFromPush = false

    val presenter by lazy { ConversationPresenter(this, sessionId, this) }

    val quickInputPopupWindow by lazy { createInputPopupWindow() }

    val addInputDialog by lazy { createAddInputQuickDialog() }

    val callWindow by lazy { createCallWindow() }

    val conversationFragment by lazy { createConversationFragment() }


    private fun createConversationFragment(): Fragment {
        return ConversationFragment().apply {
            uri = Uri.parse("rong://" + applicationInfo.packageName).buildUpon()
                    .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                    .appendQueryParameter("targetId", sessionId).build()
        }
    }

    private fun createCallWindow(): PopupWindow {
        val callPopupView = layoutInflater.inflate(R.layout.conversation_call_window, null)
        callPopupView.findViewById(R.id.btn_call).setOnClickListener {
            presenter.callContactPhone(sessionId)
            showCallContactDialog(false)
        }
        callPopupView.findViewById(R.id.btn_cancel).setOnClickListener { showCallContactDialog(false) }
        return PopupWindow(callPopupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            animationStyle = R.style.popup_window_anim
            isFocusable = true
            isOutsideTouchable = true
            isTouchable = true
            update()
        }

    }

    private fun createAddInputQuickDialog(): AlertDialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.add_input_quick_dialog, null)
        val edContent = dialogView.findViewById(R.id.ed_content) as EditText
        fun inputValue(): String {
            return edContent.text.toString().trim()
        }
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener { addInputDialog.dismiss() }
        dialogView.findViewById(R.id.btn_confirm).setOnClickListener { if (!TextUtils.isEmpty(inputValue())) presenter.insertInput(inputValue()) }

        return AlertDialog.Builder(context)
                .setView(dialogView)
                .create()
    }

    private fun createInputPopupWindow(): PopupWindow {
        val width = ScreenUtils.dp2px(300f, context)
        val height = ScreenUtils.dp2px(318f, context)
        val popupView = layoutInflater.inflate(R.layout.converstation_quick_input, null)
        val rvInputContent = popupView.findViewById(R.id.rv_inputQuick) as RecyclerView
        popupView.findViewById(R.id.btnCloseInputQuick).setOnClickListener { showQuickInputPop(false) }
        popupView.findViewById(R.id.btn_add).setOnClickListener { showAddInputQuickDialog(true) }
        rvInputContent.run {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.inputQuickAdapter
        }
        return PopupWindow(popupView, width, height).apply {
            animationStyle = R.style.popup_window_anim
            setBackgroundDrawable(ColorDrawable(Color.parseColor("#F8F8F8")))
            isFocusable = true
            isOutsideTouchable = true
            update()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conversation_act)
//        Logger.d(intent.data)

        if (!TextUtils.isEmpty(titleStr))
            topTitle.text = titleStr

        isPushMessage(intent)
        presenter.start()
        TextViewUtils.setDrawableToLeft(context, topRight, R.mipmap.im_detail_call_ico)
        showBeginnerGuidance(SPUtils.get(context, Constant.KEY_FIRST_OKEN_CONVERSATION, false) as Boolean)
        KeyboardChangeListener(this).setKeyBoardListener { isShow, _ ->
            if (isShow) showQuickInputPop(false)
        }
        btn_know.setOnClickListener {
            SPUtils.put(context, Constant.KEY_FIRST_OKEN_CONVERSATION, false)
            showBeginnerGuidance(false)
        }
        topLeft.setOnClickListener { close() }
        topRight.setOnClickListener { showCallContactDialog(true) }
        topRightSecond.setOnClickListener { ConversationDetailsActivity.start(context, sessionId) }
        btn_vipInfo.setOnClickListener { StartActivityManager.startVipInfoPage(context) }
        btn_lookBack.setOnClickListener { if (presenter.sessionId != null) CaseDetailActivity.start(context, presenter.sessionInfo!!.relatedCaseInfo.id, true) }
        btn_openInputQuick.setOnClickListener { showQuickInputPop(true) }
    }


//    /**
//     * 初始化作品状态
//     *
//     * @param caseStatus 作品状态：1 = 上架，2 = 下架，3 = 强制下架，4 = 待完善
//     */
//    override fun initCaseState(state: Int) {
//       when(state){
//           2 -> {
//               case_layout.visibility = View.GONE
//               state_layput.visibility= View.VISIBLE
//               tv_case_state.text = "您所关注的本案作品已被下架"
//           }
//           3 -> {
//               case_layout.visibility = View.GONE
//               state_layput.visibility= View.VISIBLE
//               tv_case_state.text = "您所关注的本案作品已被下架"
//           }
//       }
//    }
//

    override fun showConversationTitle(title: String) {
        topTitle.text = title
    }

    /**
     * 显示拨打电话PopupWindow
     */
    override fun showCallContactDialog(show: Boolean) {
        if (show) {
            rootLayout.setBackgroundColor(Color.parseColor("#40000000"))
            callWindow.showAtLocation(rootLayout, Gravity.BOTTOM, 0, 0)
        } else {
            callWindow.dismiss()
            rootLayout.setBackgroundColor(resources.getColor(R.color.bg_whide))
        }
    }

    /**
     * 显示新增快捷输入Dialog
     */
    override fun showAddInputQuickDialog(show: Boolean) {
        if (show) addInputDialog.show() else addInputDialog.dismiss()
    }


    /**
     * 显示快捷输入Dialog
     */
    override fun showQuickInputPop(show: Boolean) {
        if (show) quickInputPopupWindow.showAtLocation(btn_openInputQuick, Gravity.BOTTOM, 0, ScreenUtils.dp2px(200f, context)) else quickInputPopupWindow.dismiss()
    }


    /**
     * 显示新手引导View
     */
    override fun showBeginnerGuidance(show: Boolean) {
        if (show) first_layout.visibility = View.VISIBLE else first_layout.visibility = View.GONE
    }


    /**
     * 显示删除快捷回复Dialog
     *
     * @param commonLanguage
     * *
     * @param position
     */
    override fun showDeleteInputQuickDialog(commonLanguage: CommonLanguage, position: Int) {
        AlertDialog.Builder(context)
                .setTitle(R.string.dialog_title)
                .setMessage("确认删除？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确认") { dialog, which -> presenter.deleteInputQuick(commonLanguage.id, position) }
                .show()
    }


    /**
     * 判断是否是 Push 消息，判断是否需要做 connect 操作
     */
    private fun isPushMessage(intent: Intent?) {

        if (intent == null || intent.data == null)
            return
        //push
        if (intent.data.scheme == "rong" && intent.data.getQueryParameter("isFromPush") != null) {

            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
            if (uri.getQueryParameter("isFromPush") == "true") {
                //只有收到系统消息和不落地 push 消息的时候，pushId 不为 null。而且这两种消息只能通过 server 来发送，客户端发送不了。
                //RongIM.getInstance().getRongIMClient().recordNotificationEvent(id);
                progressDialog.dismiss()
                isFromPush = true
                enterActivity()
            } else if (RongIM.getInstance().currentConnectionStatus == RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED) {
                progressDialog.dismiss()
                if (uri.path.contains("conversation/system")) {
                    //系统消息
                    close()
                    CustomerMainActivity.start(context)
//                    intent1.putExtra("systemconversation", true)
//                    startActivity(intent1)
//                    SealAppContext.getInstance().popAllActivity()
                    return
                }
                enterActivity()
            } else {
                if (uri.path.contains("conversation/system")) {
                    close()
                    CustomerMainActivity.start(context)
                    return
                }
                enterFragment()
            }

        } else {
            if (RongIM.getInstance().currentConnectionStatus == RongIMClient.ConnectionStatusListener.ConnectionStatus.DISCONNECTED) {
                progressDialog.show()
                Handler().postDelayed({ enterActivity() }, 300)
            } else {
                enterFragment()
            }
        }
    }


    /**
     * 收到 push 消息后，选择进入哪个 Activity
     * 如果程序缓存未被清理，进入 MainActivity
     * 程序缓存被清理，进入 LoginActivity，重新获取token
     *
     *
     * 作用：由于在 manifest 中 intent-filter 是配置在 ConversationActivity 下面，所以收到消息后点击notifacition 会跳转到 DemoActivity。
     * 以跳到 MainActivity 为例：
     * 在 ConversationActivity 收到消息后，选择进入 MainActivity，这样就把 MainActivity 激活了，当你读完收到的消息点击 返回键 时，程序会退到
     * MainActivity 页面，而不是直接退回到 桌面。
     */
    private fun enterActivity() {

        val token = IMManager.token(context)

        if (TextUtils.isEmpty(token)) {
            progressDialog.dismiss()
            Logger.e("ConversationActivity push", "push2")
            close()
            LoginActivity.start(context, false)
        } else {
            Logger.e("ConversationActivity push", "push3")
            reconnect(token)
        }
    }


    private fun reconnect(token: String) {
        RongIM.connect(token, object : RongIMClient.ConnectCallback() {
            override fun onTokenIncorrect() {
                Log.e(TAG, "---onTokenIncorrect--")
            }

            override fun onSuccess(s: String) {
                Log.i(TAG, "---onSuccess--" + s)
                Logger.d("ConversationActivity push", "push4")

                progressDialog.dismiss()

                enterFragment()

            }

            override fun onError(e: RongIMClient.ErrorCode) {
                Log.e(TAG, "---onError--" + e)
                progressDialog.dismiss()
                enterFragment()
            }
        })

    }


    fun enterFragment() {
        supportFragmentManager.beginTransaction().add(R.id.conversation, conversationFragment).commitAllowingStateLoss()
    }

}