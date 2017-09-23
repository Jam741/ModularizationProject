package com.yingwumeijia.baseywmj.nimim.conversation.employee

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
import android.widget.*
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
import com.yingwumeijia.baseywmj.entity.bean.CommonLanguage
import com.yingwumeijia.baseywmj.entity.bean.GreetingLanguage
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.casedetails.CaseDetailActivity
import com.yingwumeijia.baseywmj.im.ConversationDetailsActivity
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import kotlinx.android.synthetic.main.conversation_status.*
import kotlinx.android.synthetic.main.employee_conversation_toolbar.*
import kotlinx.android.synthetic.main.employee_team_message_activity.*

/**
 * 群聊界面
 *
 *
 * Created by huangjun on 2015/3/5.
 */
class EmployeeTeamMessageActivity : BaseMessageActivity(), ConversationControact.View {


    override fun sendMessage(imMessage: IMMessage) {
        messageFragment.sendMessage(imMessage)
    }

    val presenter by lazy { ConversationPresenter(this, sessionId, this) }

    val quickInputPopupWindow by lazy { createInputPopupWindow() }

    val addInputDialog by lazy { createAddInputQuickDialog() }

//    val conversationFragment by lazy { createConversationFragment() }

    var greetInputPop: PopupWindow? = null

    val putGreetsingDialog by lazy { createPutGreeetsingDialog() }


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
        topRight.setOnClickListener { ConversationDetailsActivity.start(this@EmployeeTeamMessageActivity, sessionId) }

        if (UserManager.getUserData()!!.userDetailType == 10) {
            greetings_layout.visibility = View.VISIBLE
            btn_openInputQuick.visibility = View.GONE
        } else {
            greetings_layout.visibility = View.GONE
            btn_openInputQuick.visibility = View.VISIBLE
        }

        btn_vipInfo.setOnClickListener { StartActivityManager.startVipInfoPage(this) }
        btn_lookBack.setOnClickListener { if (presenter.sessionId != null) CaseDetailActivity.start(this, presenter.sessionInfo!!.relatedCaseInfo.id, true) }
        btn_openInputQuick.setOnClickListener { showQuickInputPop(true) }
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
        Toast.makeText(this@EmployeeTeamMessageActivity, "获取群组信息失败!", Toast.LENGTH_SHORT).show()
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
            if (team.id == this@EmployeeTeamMessageActivity.team!!.id) {
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
        return R.layout.employee_team_message_activity
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
            intent.setClass(context, EmployeeTeamMessageActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)

            context.startActivity(intent)
        }
    }


    fun createPutGreeetsingDialog(): AlertDialog {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_input_greetings_dialog, null)
        val ed_Content_greet = dialogView.findViewById(R.id.ed_content) as EditText
        (dialogView.findViewById(R.id.tv_default) as TextView).text = presenter.greetingLanguage!!.defaultGreeting
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener { putGreetsingDialog.dismiss() }

        dialogView.findViewById(R.id.btn_confirm).setOnClickListener(View.OnClickListener {
            if (ed_Content_greet.text == null) return@OnClickListener
            if (!TextUtils.isEmpty(ed_Content_greet.text.toString().trim({ it <= ' ' }))) {
                presenter.putGreetingsInput(ed_Content_greet.text.toString())
            } else {
                presenter.putGreetingsInput(null)
            }
        })
        return AlertDialog.Builder(this)
                .setView(dialogView)
                .create()
    }

    override fun createGreetPopWindow(greetingLanguage: GreetingLanguage) {

        val width = ScreenUtils.dp2px(300f, this)
        val height = ScreenUtils.dp2px(318f, this)

        val popupView = layoutInflater.inflate(R.layout.converstation_greetings_input, null)


        val btnCloseInputQuick = popupView.findViewById(R.id.btnCloseInputQuick) as ImageView
        val btnAdd = popupView.findViewById(R.id.btn_add) as Button
        val tvContent1 = popupView.findViewById(R.id.tv_content1) as TextView
        val tvContent2 = popupView.findViewById(R.id.tv_content2) as TextView
        tvContent1.text = greetingLanguage.defaultGreeting
        if (!TextUtils.isEmpty(greetingLanguage.appendGreeting))
            tvContent2.text = greetingLanguage.appendGreeting
        btnCloseInputQuick.setOnClickListener { showGreetInputPop(false) }
        btnAdd.setOnClickListener { showPutInputGreetingsDialog(true) }

        //  创建PopupWindow对象，指定宽度和高度
        greetInputPop = PopupWindow(popupView, width, height).apply {
            animationStyle = R.style.popup_window_anim
            setBackgroundDrawable(ColorDrawable(Color.parseColor("#F8F8F8")))
            isFocusable = true
            isOutsideTouchable = true
            update()
        }
        greetInputPop!!.showAtLocation(btn_openInputQuick, Gravity.BOTTOM, 0, ScreenUtils.dp2px(200f, this))

    }


//    private fun createConversationFragment(): Fragment {
//        return ConversationFragment().apply {
//            uri = Uri.parse("rong://" + applicationInfo.packageName).buildUpon()
//                    .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
//                    .appendQueryParameter("targetId", sessionId).build()
//        }
//    }


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


    /**
     * 创建快捷回复的  弹窗
     */
    private fun createInputPopupWindow(): PopupWindow {
        val width = ScreenUtils.dp2px(300f, this)
        val height = ScreenUtils.dp2px(318f, this)
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


    override fun showGreetInputPop(show: Boolean) {
        if (show) {
            presenter.getGreetLanguage()

        } else {
            if (greetInputPop != null)
                greetInputPop!!.dismiss()
        }
    }

    override fun showPutInputGreetingsDialog(show: Boolean) {
        if (show) putGreetsingDialog.show()
        else putGreetsingDialog.dismiss()
    }


    override fun showConversationTitle(title: String) {
        topTitle.text = title
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
        if (show) quickInputPopupWindow.showAtLocation(btn_openInputQuick, Gravity.BOTTOM, 0, ScreenUtils.dp2px(200f, this)) else quickInputPopupWindow.dismiss()
    }


    /**
     * 显示新手引导View
     */
    override fun showBeginnerGuidance(show: Boolean) {
//        if (show) first_layout.visibility = View.VISIBLE else first_layout.visibility = View.GONE
    }


    /**
     * 显示删除快捷回复Dialog
     *
     * @param commonLanguage
     * *
     * @param position
     */
    override fun showDeleteInputQuickDialog(commonLanguage: CommonLanguage, position: Int) {
        AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage("确认删除？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确认") { dialog, which -> presenter.deleteInputQuick(commonLanguage.id, position) }
                .show()
    }


}
