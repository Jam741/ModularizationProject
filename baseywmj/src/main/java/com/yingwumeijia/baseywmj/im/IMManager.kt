package com.yingwumeijia.baseywmj.im

import android.content.Context
import com.netease.nim.uikit.NimUIKit
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.auth.AuthService
import com.netease.nimlib.sdk.msg.MessageBuilder
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.netease.nimlib.sdk.msg.model.RecentContact
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.commonlibrary.utils.SPUtils

/**
 * Created by jamisonline on 2017/7/17.
 */
object IMManager {


    fun token(context: Context): String {
        return SPUtils.get(context, Constant.KEY_IM_TOKEN, "") as String
    }

    fun tokenPut(context: Context, token: String) {
        SPUtils.put(context, Constant.KEY_IM_TOKEN, token)
    }

    fun currentSessionId(context: Context): String {
        return SPUtils.get(context, Constant.KEY_CURRENT_SESSION_ID, "") as String
    }

    fun setCurrentSessionId(context: Context, sessionId: String) {
        SPUtils.put(context, Constant.KEY_CURRENT_SESSION_ID, sessionId)
    }

    fun currentGroupId(context: Context): String {
        return SPUtils.get(context, Constant.KEY_CURRENT_SESSION_ID, "") as String
    }

    fun setCurrentGroupId(context: Context, sessionId: String) {
        SPUtils.put(context, Constant.KEY_CURRENT_SESSION_ID, sessionId)
    }


    /**
     * 发送文本消息
     */
    fun sendTextMessage(sessionId: String, content: String) {

//        val msg = MessageBuilder.createTextMessage(sessionId, SessionTypeEnum.Team, content)
//
//        NIMClient.getService(MsgService::class.java).sendMessage(msg, true)

//        val customerMsg = MessageBuilder.createCustomMessage()

    }


//    /**
//     * 删除会话
//     */
//    fun removeConversation(conversationType: Conversation.ConversationType, sessionId: String, callBack: RongIMClient.ResultCallback<Boolean>) {
//        if (RongIM.getInstance() != null)
//            RongIM.getInstance().removeConversation(conversationType, sessionId, callBack)
//
//    }

    /**
     * 清除未读系统信息
     */
    fun cleanUnreadSystemMessage() {
//        if (RongIM.getInstance() != null)
//            RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.SYSTEM, Constant.SYSTEM_TARGET_ID, object : RongIMClient.ResultCallback<Boolean>() {
//                override fun onError(p0: RongIMClient.ErrorCode?) {
//
//                }
//
//                override fun onSuccess(p0: Boolean?) {
//                }
//            })
    }

    /**
     * 删除会话
     */
    fun removeConversation(sessionId: String) {

        val recentContacts = NIMClient.getService(MsgService::class.java).queryRecentContactsBlock()

        NIMClient.getService(MsgService::class.java).deleteRecentContact2(sessionId, SessionTypeEnum.Team)
        NIMClient.getService(MsgService::class.java).clearChattingHistory(sessionId, SessionTypeEnum.Team)


//        for (recentContact in recentContacts) {
//            if (recentContact.contactId.equals(sessionId) && recentContact.sessionType == SessionTypeEnum.Team) {
//                NIMClient.getService(MsgService::class.java).deleteRecentContact2(recentContact)
//                NIMClient.getService(MsgService::class.java).clearChattingHistory(sessionId, SessionTypeEnum.Team)
//            }
//        }

    }

    fun loginOut() {
        NIMClient.getService(AuthService::class.java).logout()
        NimUIKit.clearCache()
    }


}