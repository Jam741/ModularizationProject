package com.yingwumeijia.baseywmj.im

import io.rong.imkit.RongIM
import io.rong.imlib.IRongCallback
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message
import io.rong.message.TextMessage

/**
 * Created by jamisonline on 2017/7/17.
 */
object IMManager {


    /**
     * 发送文本消息
     */
    fun sendTextMessage(sessionId: String, content: String, callBack: IRongCallback.ISendMessageCallback) {
        val textMessage = TextMessage.obtain(content)
        val myMessage = Message.obtain(sessionId, Conversation.ConversationType.GROUP, textMessage)
        if (RongIM.getInstance() != null)
            RongIM.getInstance().sendMessage(myMessage, content, content, callBack)
    }

    /**
     * 发送文本消息
     */
    fun sendTextMessage(sessionId: String, content: String) {
        val textMessage = TextMessage.obtain(content)
        val myMessage = Message.obtain(sessionId, Conversation.ConversationType.GROUP, textMessage)
        if (RongIM.getInstance() != null)
            RongIM.getInstance().sendMessage(myMessage, content, content, object : IRongCallback.ISendMessageCallback {
                override fun onAttached(p0: Message?) {

                }

                override fun onSuccess(p0: Message?) {
                }

                override fun onError(p0: Message?, p1: RongIMClient.ErrorCode?) {
                }

            })
    }


    /**
     * 删除会话
     */
    fun removeConversation(conversationType: Conversation.ConversationType, sessionId: String, callBack: RongIMClient.ResultCallback<Boolean>) {
        if (RongIM.getInstance() != null)
            RongIM.getInstance().removeConversation(conversationType, sessionId, callBack)

    }

    /**
     * 删除会话
     */
    fun removeConversation(conversationType: Conversation.ConversationType, sessionId: String) {
        if (RongIM.getInstance() != null)
            RongIM.getInstance().removeConversation(conversationType, sessionId, object : RongIMClient.ResultCallback<Boolean>() {
                override fun onSuccess(p0: Boolean?) {

                }

                override fun onError(p0: RongIMClient.ErrorCode?) {

                }
            })
    }


}