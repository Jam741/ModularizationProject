package com.yingwumeijia.baseywmj.im

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ExpandableListView
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.base.JBaseApp
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.MemberBean
import com.yingwumeijia.baseywmj.function.introduction.employee.EmployeeActivity
import com.yingwumeijia.baseywmj.im.add.AddCustomerActivity
import com.yingwumeijia.baseywmj.im.add.AddEmployeeActivity
import com.yingwumeijia.baseywmj.im.add.EditProjectNameActivity
import kotlinx.android.synthetic.main.conversation_details_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/21.
 */
class ConversationDetailsActivity : JBaseActivity(), ConversationDetailsContract.View {


    val sessionId by lazy { intent.getStringExtra(Constant.KEY_SESSION_ID) }

    val presenter by lazy { ConversationDetailsPresenter(context, sessionId, this) }

    var isOnwer = false


    private val REQUST_CODE_RENAME = 1
    private val REQUST_CODE_ADDMEMBER = 2

    companion object {
        fun start(context: Context, sessionId: String) {
            val starter = Intent(context, ConversationDetailsActivity::class.java)
            starter.putExtra(Constant.KEY_SESSION_ID, sessionId)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conversation_details_act)
        topTitle.text = "聊天信息"
        topLeft.setOnClickListener { close() }
        btn_renameProject.setOnClickListener { if (presenter.projectName != null) EditProjectNameActivity.startForResult(context, sessionId, presenter.projectName!!, REQUST_CODE_RENAME) }
        btn_dismiss_conversation.setOnClickListener { showDismissConversationDialog() }
        presenter.start()
    }


    override fun showMemberList(teamListItemBeanList: Map<String, List<MemberBean>>) {
        val teamAdapter = TeamAdapter(teamListItemBeanList, this@ConversationDetailsActivity, sessionId)
        teamAdapter.run {
            setOnChildClickListener { groupPosition, childPosition -> if (groupPosition != null && !getChild(groupPosition, childPosition).userType.equals("c")) EmployeeActivity.start(context, getChild(groupPosition, childPosition).userId, 0) }
            setOnChildLongClickListener { groupPosition, childPosition -> showRemoveMemberDialog(getChild(groupPosition, childPosition)) }
        }
        listTeam.run {
            setAdapter(teamAdapter)
            setOnGroupClickListener(ExpandableListView.OnGroupClickListener { parent, v, groupPosition, id ->
                if (groupPosition == 0) {
                    if (AppTypeManager.isAppC())
                        AddCustomerActivity.start(this@ConversationDetailsActivity, sessionId, REQUST_CODE_ADDMEMBER)
                } else {
                    AddEmployeeActivity.start(this@ConversationDetailsActivity, sessionId, REQUST_CODE_ADDMEMBER)
                }
                return@OnGroupClickListener true
            })
        }

        for (i in 0..teamListItemBeanList.size - 1) {
            listTeam.expandGroup(i)
        }
    }

    private fun showRemoveMemberDialog(child: MemberBean): Boolean {
        AlertDialog.Builder(context)
                .setTitle(R.string.dialog_title)
                .setItems(arrayOf("删除")) { dialog, which -> presenter.removeMember(child.imUid) }
                .show()
        return false
    }


    private fun showDismissConversationDialog() {

        val message = if (isOnwer) "确认退出并解散聊天吗？退出后，相关信息将被删除" else "确认退出聊天吗？退出后，相关信息将被删除"

        AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("确认") { dialog, which ->
                    if (isOnwer)
                        presenter.dismissConversation()
                    else
                        presenter.quitSession()
                }
                .setNegativeButton("取消") { dialog, which -> }
                .setTitle(R.string.dialog_title)
                .show()

    }


    override fun showProjectName(projectName: String) {
        tv_projectName.text = projectName
    }

    override fun showIsSessiOnwne(onwner: Boolean) {
        isOnwer = onwner
        btn_dismiss_conversation.run {
            text = if (onwner) "退出并解散聊天" else "退出聊天"
            setOnClickListener { if (onwner) presenter.dismissConversation() else presenter.quitSession() }
        }


    }

    override fun dismissConversationSuccess() {
//        IMManager.removeConversation(Conversation.ConversationType.GROUP, sessionId)
        close()
        JBaseApp.finishCurrentConversation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUST_CODE_ADDMEMBER -> presenter.start()
                REQUST_CODE_RENAME -> {
                    if (data != null) {
                        tv_projectName.text = data.getStringExtra(Constant.KEY_INPUT_RESULT)
//                        val group = Group(sessionId, data.getStringExtra(Constant.KEY_INPUT_RESULT), Uri.parse(presenter.groupPortrait))
//                        RongIM.getInstance().refreshGroupInfoCache(group)
                    }
                }
            }
        }
    }

}