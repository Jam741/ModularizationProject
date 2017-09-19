package com.yingwumeijia.baseywmj.im

import android.content.Context
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

    /**
     * 发送文本消息
     */
//    fun sendTextMessage(sessionId: String, content: String, callBack: IRongCallback.ISendMessageCallback) {
//        val textMessage = TextMessage.obtain(content)
//        val myMessage = Message.obtain(sessionId, Conversation.ConversationType.GROUP, textMessage)
//        if (RongIM.getInstance() != null)
//            RongIM.getInstance().sendMessage(myMessage, content, content, callBack)
//    }

    /**
     * 发送文本消息
     */
    fun sendTextMessage(sessionId: String, content: String) {
//        val textMessage = TextMessage.obtain(content)
//        val myMessage = Message.obtain(sessionId, Conversation.ConversationType.GROUP, textMessage)
//        if (RongIM.getInstance() != null)
//            RongIM.getInstance().sendMessage(myMessage, content, content, object : IRongCallback.ISendMessageCallback {
//                override fun onAttached(p0: Message?) {
//
//                }
//
//                override fun onSuccess(p0: Message?) {
//                }
//
//                override fun onError(p0: Message?, p1: RongIMClient.ErrorCode?) {
//                }
//
//            })
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
//    fun removeConversation(conversationType: Conversation.ConversationType, sessionId: String) {
//        if (RongIM.getInstance() != null)
//            RongIM.getInstance().removeConversation(conversationType, sessionId, object : RongIMClient.ResultCallback<Boolean>() {
//                override fun onSuccess(p0: Boolean?) {
//
//                }
//
//                override fun onError(p0: RongIMClient.ErrorCode?) {
//
//                }
//            })
//    }

    fun loginOut() {
//        if (RongIM.getInstance() != null) {
//            RongIM.getInstance().logout()
//        }
    }


}