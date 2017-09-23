package com.yingwumeijia.baseywmj.nimim.conversation.customer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import com.netease.nim.uikit.NimUIKit
import com.netease.nim.uikit.cache.FriendDataCache
import com.netease.nim.uikit.cache.TeamDataCache
import com.netease.nim.uikit.session.activity.BaseMessageActivity
import com.netease.nim.uikit.session.constant.Extras
import com.netease.nim.uikit.session.fragment.MessageFragment
import com.netease.nim.uikit.session.fragment.TeamMessageFragment
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.netease.nimlib.sdk.msg.model.IMMessage
import com.netease.nimlib.sdk.team.TeamService
import com.netease.nimlib.sdk.team.constant.TeamTypeEnum
import com.netease.nimlib.sdk.team.model.Team
import com.netease.nimlib.sdk.team.model.TeamMember
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.CommonLanguage
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.casedetails.CaseDetailActivity
import com.yingwumeijia.baseywmj.im.ConversationDetailsActivity
import com.yingwumeijia.commonlibrary.utils.SPUtils
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import kotlinx.android.synthetic.main.conversation_beginner_guidance.*
import kotlinx.android.synthetic.main.conversation_status.*
import kotlinx.android.synthetic.main.customer_conversation_toolbar.*
import kotlinx.android.synthetic.main.customer_team_message_activity.*

/**
 * 群聊界面
 *
 *
 * Created by huangjun on 2015/3/5.
 */
class CustomerTeamMessageActivity : BaseMessageActivity(), ConversationControact.View {

    override fun sendMessage(imMessage: IMMessage) {
        messageFragment.sendMessage(imMessage)
    }


    val presenter by lazy { ConversationPresenter(this, sessionId, this) }

    val quickInputPopupWindow by lazy { createInputPopupWindow() }

    val addInputDialog by lazy { createAddInputQuickDialog() }

    val callWindow by lazy { createCallWindow() }


    // model
    private var team: Team? = null

    private var invalidTeamTipView: View? = null

    private var invalidTeamTipText: TextView? = null

    private var fragment: TeamMessageFragment? = null

    private var backToClass: Class<out Activity>? = null

    protected fun findViews() {
        invalidTeamTipView = findView<View>(R.id.invalid_team_tip)
        invalidTeamTipText = findView<TextView>(R.id.invalid_team_text)

        topLeft.setOnClickListener { finish() }
        topRightSecond.setOnClickListener { ConversationDetailsActivity.start(this@CustomerTeamMessageActivity, sessionId) }
        topRight.setOnClickListener { showCallContactDialog(true) }
        btn_vipInfo.setOnClickListener { StartActivityManager.startVipInfoPage(this@CustomerTeamMessageActivity) }
        btn_lookBack.setOnClickListener { if (presenter.sessionId != null) CaseDetailActivity.start(this@CustomerTeamMessageActivity, presenter.sessionInfo!!.relatedCaseInfo.id, true) }
        btn_openInputQuick.setOnClickListener { showQuickInputPop(true) }
        btn_know.setOnClickListener {
            SPUtils.put(this, Constant.KEY_FIRST_OKEN_CONVERSATION, false)
            showBeginnerGuidance(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        backToClass = intent.getSerializableExtra(Extras.EXTRA_BACK_TO_CLASS) as Class<out Activity>

        findViews()

        registerTeamUpdateObserver(true)

        presenter.start()


    }

    override fun onDestroy() {
        super.onDestroy()

        registerTeamUpdateObserver(false)
    }

    override fun onResume() {
        super.onResume()
        requestTeamInfo()
    }

    /**
     * 请求群基本信息
     */
    private fun requestTeamInfo() {
        // 请求群基本信息
        val t = NIMClient.getService(TeamService::class.java).queryTeamBlock(sessionId)
        if (t != null) {
            TeamDataCache.getInstance().addOrUpdateTeam(t)
            updateTeamInfo(t)
        } else {
            TeamDataCache.getInstance().fetchTeamById(sessionId) { success, result ->
                if (success && result != null) {
                    updateTeamInfo(result)
                } else {
                    onRequestTeamInfoFailed()
                }
            }
        }
    }

    private fun onRequestTeamInfoFailed() {
        Toast.makeText(this@CustomerTeamMessageActivity, "获取群组信息失败!", Toast.LENGTH_SHORT).show()
        finish()
    }

    /**
     * 更新群信息

     * @param d
     */
    private fun updateTeamInfo(d: Team?) {
        if (d == null) {
            return
        }

        team = d
        fragment!!.setTeam(team)

        topTitle.text = team!!.name

//        topTitle!!.text = if (team == null) sessionId else team!!.name + "(" + team!!.memberCount + "人)"

        invalidTeamTipText!!.setText(if (team!!.type == TeamTypeEnum.Normal) R.string.normal_team_invalid_tip else R.string.team_invalid_tip)
        invalidTeamTipView!!.visibility = if (team!!.isMyTeam) View.GONE else View.VISIBLE
    }

    /**
     * 注册群信息更新监听

     * @param register
     */
    private fun registerTeamUpdateObserver(register: Boolean) {
        if (register) {
            TeamDataCache.getInstance().registerTeamDataChangedObserver(teamDataChangedObserver)
            TeamDataCache.getInstance().registerTeamMemberDataChangedObserver(teamMemberDataChangedObserver)
        } else {
            TeamDataCache.getInstance().unregisterTeamDataChangedObserver(teamDataChangedObserver)
            TeamDataCache.getInstance().unregisterTeamMemberDataChangedObserver(teamMemberDataChangedObserver)
        }
        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register)
    }

    /**
     * 群资料变动通知和移除群的通知（包括自己退群和群被解散）
     */
    internal var teamDataChangedObserver: TeamDataCache.TeamDataChangedObserver = object : TeamDataCache.TeamDataChangedObserver {
        override fun onUpdateTeams(teams: List<Team>) {
            if (team == null) {
                return
            }
            for (t in teams) {
                if (t.id == team!!.id) {
                    updateTeamInfo(t)
                    break
                }
            }
        }

        override fun onRemoveTeam(team: Team?) {
            if (team == null) {
                return
            }
            if (team.id == this@CustomerTeamMessageActivity.team!!.id) {
                updateTeamInfo(team)
            }
        }
    }

    /**
     * 群成员资料变动通知和移除群成员通知
     */
    internal var teamMemberDataChangedObserver: TeamDataCache.TeamMemberDataChangedObserver = object : TeamDataCache.TeamMemberDataChangedObserver {

        override fun onUpdateTeamMember(members: List<TeamMember>) {
            fragment!!.refreshMessageList()
        }

        override fun onRemoveTeamMember(member: TeamMember) {}
    }

    internal var friendDataChangedObserver: FriendDataCache.FriendDataChangedObserver = object : FriendDataCache.FriendDataChangedObserver {
        override fun onAddedOrUpdatedFriends(accounts: List<String>) {
            fragment!!.refreshMessageList()
        }

        override fun onDeletedFriends(accounts: List<String>) {
            fragment!!.refreshMessageList()
        }

        override fun onAddUserToBlackList(account: List<String>) {
            fragment!!.refreshMessageList()
        }

        override fun onRemoveUserFromBlackList(account: List<String>) {
            fragment!!.refreshMessageList()
        }
    }

    override fun fragment(): MessageFragment {
        // 添加fragment
        val arguments = intent.extras
        arguments.putSerializable(Extras.EXTRA_TYPE, SessionTypeEnum.Team)
        fragment = TeamMessageFragment()
        fragment!!.arguments = arguments
        fragment!!.containerId = R.id.message_fragment_container
        return fragment as MessageFragment
    }

    override fun getContentViewId(): Int {
        return R.layout.customer_team_message_activity
    }

    override fun initToolBar() {


        //
        //        ToolBarOptions options = new ToolBarOptions();
        //        options.titleString = "群聊";
        //        setToolBar(R.id.toolbar, options);
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (backToClass != null) {
            val intent = Intent()
            intent.setClass(this, backToClass)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }

    companion object {

        fun start(context: Context, tid: String) {
            val intent = Intent()
            intent.putExtra(Extras.EXTRA_ACCOUNT, tid)
            intent.putExtra(Extras.EXTRA_CUSTOMIZATION, NimUIKit.getCommonTeamSessionCustomization())
            intent.setClass(context, CustomerTeamMessageActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)

            context.startActivity(intent)
        }
    }


    override fun showConversationTitle(title: String) {

    }

    override fun showBeginnerGuidance(show: Boolean) {
        if (show) first_layout.visibility = View.VISIBLE else first_layout.visibility = View.GONE
    }

    override fun showQuickInputPop(show: Boolean) {
        if (show) quickInputPopupWindow.showAtLocation(btn_openInputQuick, Gravity.BOTTOM, 0, ScreenUtils.dp2px(200f, this)) else quickInputPopupWindow.dismiss()
    }

    override fun showAddInputQuickDialog(show: Boolean) {
        if (show) addInputDialog.show() else addInputDialog.dismiss()
    }

    override fun showDeleteInputQuickDialog(commonLanguage: CommonLanguage, position: Int) {
        AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage("确认删除？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确认") { dialog, which -> presenter.deleteInputQuick(commonLanguage.id, position) }
                .show()
    }

    override fun showCallContactDialog(show: Boolean) {
        if (show) {
            rootLayout.setBackgroundColor(Color.parseColor("#40000000"))
            callWindow.showAtLocation(rootLayout, Gravity.BOTTOM, 0, 0)
        } else {
            callWindow.dismiss()
            rootLayout.setBackgroundColor(resources.getColor(R.color.bg_whide))
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
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_input_quick_dialog, null)
        val edContent = dialogView.findViewById(R.id.ed_content) as EditText
        fun inputValue(): String {
            return edContent.text.toString().trim()
        }
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener { addInputDialog.dismiss() }
        dialogView.findViewById(R.id.btn_confirm).setOnClickListener { if (!TextUtils.isEmpty(inputValue())) presenter.insertInput(inputValue()) }

        return AlertDialog.Builder(this)
                .setView(dialogView)
                .create()
    }

    private fun createInputPopupWindow(): PopupWindow {
        val width = ScreenUtils.dp2px(300f, this)
        val height = ScreenUtils.dp2px(318f, this)
        val popupView = layoutInflater.inflate(R.layout.converstation_quick_input, null)
        val rvInputContent = popupView.findViewById(R.id.rv_inputQuick) as RecyclerView
        popupView.findViewById(R.id.btnCloseInputQuick).setOnClickListener { showQuickInputPop(false) }
        popupView.findViewById(R.id.btn_add).setOnClickListener { showAddInputQuickDialog(true) }
        (popupView.findViewById(R.id.tv_title) as TextView).text = "选择常见问题"

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


}
