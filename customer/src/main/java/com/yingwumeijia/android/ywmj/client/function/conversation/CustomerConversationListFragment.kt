package com.yingwumeijia.android.ywmj.client.function.conversation

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.netease.nim.uikit.recent.RecentContactsCallback
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.conversation.list.ConversationNotLoggedFragment
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.function.UserManager
import kotlinx.android.synthetic.main.conversation_list_container.*
import com.netease.nim.uikit.recent.RecentContactsFragment
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment
import com.netease.nimlib.sdk.msg.model.RecentContact
import com.netease.nimlib.sdk.msg.model.IMMessage
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.yingwumeijia.baseywmj.function.StartActivityManager


/**
 * Created by jamisonline on 2017/7/14.
 *
 * 会话列表容器 Fragment 解耦会话列表
 */
class CustomerConversationListFragment : JBaseFragment() {

    private val contactsFragment: RecentContactsFragment by lazy { assembleRecentContactsFragment() }

    private fun assembleRecentContactsFragment(): RecentContactsFragment {
        val recentContactsFragment = RecentContactsFragment()
        recentContactsFragment.setCallback(contactssCallback())
        return recentContactsFragment
    }


    companion object {
        fun newInstance(): CustomerConversationListFragment {
            return CustomerConversationListFragment()
        }
    }

    val notLoggedFragment by lazy { ConversationNotLoggedFragment() }

    val loggedFragment by lazy { contactsFragment }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.conversation_list_container, container, false)
    }

    //
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topTitle.text = "聊天"
    }

    //
    override fun onResume() {
        super.onResume()
        if (UserManager.isLogin(activity)) putFragment(loggedFragment) else putFragment(notLoggedFragment)
    }

    private fun putFragment(fragment: Fragment) {
        val currentFragment = childFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            childFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        } else if (currentFragment != fragment) {
            childFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }
    }


    /**
     * 会话列表事件回调
     */
    inner class contactssCallback : RecentContactsCallback {
        override fun onRecentContactsLoaded() {
            // 最近联系人列表加载完毕
        }

        override fun onUnreadCountChange(unreadCount: Int) {
            // 未读数发生变化
        }

        override fun onItemClick(recent: RecentContact?) {
            when (recent!!.sessionType) {
                SessionTypeEnum.Team -> StartActivityManager.startConversation(getContext(), recent.contactId)
            }
        }

        override fun getDigestOfAttachment(recent: RecentContact?, attachment: MsgAttachment?): String? {
            // 设置自定义消息的摘要消息，展示在最近联系人列表的消息缩略栏上
            // 当然，你也可以自定义一些内建消息的缩略语，例如图片，语音，音视频会话等，自定义的缩略语会被优先使用。
            return null
        }

        override fun getDigestOfTipMsg(recent: RecentContact?): String? {
            val msgId = recent!!.recentMessageId
            val uuids = ArrayList<String>()
            uuids.add(msgId)
            val msgs = NIMClient.getService(MsgService::class.java).queryMessageListByUuidBlock(uuids)
            if (msgs != null && !msgs.isEmpty()) {
                val msg = msgs[0]
                val content = msg.remoteExtension
                if (content != null && !content.isEmpty()) {
                    return content["content"] as String
                }
            }
            return null
        }

    }
}