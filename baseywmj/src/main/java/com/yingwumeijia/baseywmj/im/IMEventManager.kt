package com.yingwumeijia.baseywmj.im

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import com.google.gson.Gson
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.SessionDetailBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.db.DBManager
import com.yingwumeijia.baseywmj.function.message.MessageActivity
import com.yingwumeijia.baseywmj.function.message.MessageBean
import com.yingwumeijia.baseywmj.function.message.MessageManager
import com.yingwumeijia.baseywmj.im.message.PayMessageContent
import com.yingwumeijia.baseywmj.im.message.PayMessageItemProvider
import com.yingwumeijia.baseywmj.im.message.PayOrderMessageContent
import com.yingwumeijia.baseywmj.im.message.PayOrderMessageItemProvider
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import io.rong.imkit.DefaultExtensionModule
import io.rong.imkit.IExtensionModule
import io.rong.imkit.RongExtensionManager
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Group
import io.rong.imlib.model.Message
import io.rong.imlib.model.UserInfo
import io.rong.message.InformationNotificationMessage
import io.rong.message.TextMessage

/**
 * Created by jamisonline on 2017/7/28.
 */
class IMEventManager(var context: Context) {


    /**
     * 会话页面点击事件
     */
    val conversationBehaviorListener = object : RongIM.ConversationBehaviorListener {
        override fun onUserPortraitLongClick(p0: Context?, p1: Conversation.ConversationType?, p2: UserInfo?): Boolean {
            return false
        }

        override fun onMessageLinkClick(p0: Context?, p1: String?): Boolean {
            return false
        }

        override fun onMessageLongClick(p0: Context?, p1: View?, p2: Message?): Boolean {
            return false
        }

        override fun onUserPortraitClick(p0: Context?, p1: Conversation.ConversationType?, p2: UserInfo?): Boolean {
            return false
        }

        override fun onMessageClick(p0: Context?, p1: View?, p2: Message?): Boolean {
            return false
        }
    }

    val sendMessageListener = object : RongIM.OnSendMessageListener {
        override fun onSend(p0: Message?): Message {
            return p0!!
        }

        override fun onSent(message: Message?, p1: RongIM.SentMessageErrorCode?): Boolean {
            val dbManager = DBManager.getInstnace(context)
            if (message == null) return false
            if (AppTypeManager.isAppC()) {
                if (message!!.uId != null) {
                    if (dbManager.isFirstConversation(message!!.targetId, message.senderUserId)) {
                        postFirstMessage(message!!.targetId, message.senderUserId)
                    }
                }
            }


            return false
        }
    }

    fun postFirstMessage(sessionId: String, sendId: String) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.isFirstSession(sessionId, sendId), object : SimpleSubscriber<Boolean>(context) {
            override fun _onNext(t: Boolean?) {
                val dbManager = DBManager.getInstnace(context)
                dbManager.insertForFIRST_CALL(DBManager.FirstConversationBean(null, sendId, "" + UserManager.getUserData()!!.id, sessionId, 1))
            }

        })

    }


    val receiveMessageListener = RongIMClient.OnReceiveMessageListener { message, i ->




        if (message.content is InformationNotificationMessage) {
            if ((message.content as InformationNotificationMessage).extra != null && (message.content as InformationNotificationMessage).extra == "GROUP_RENAME") {
                //修改群名称
                refreshGroupInfo(message.targetId)
            }
        }

        if (message.content is TextMessage) {


            if ((message.content as TextMessage).extra != null) {
                if ((message.content as TextMessage).extra == "INTERNAL_MESSAGE") {
                    val messageBean = assembleMessageBean(message)
                    MessageManager.insert(context, messageBean)

                    //发送广播
                    val mIntent = Intent(MessageActivity.ACTION_MESSAGE)
                    mIntent.putExtra(MessageActivity.KEY_MESSAGE, messageBean)
                    sendBroadcast(context, mIntent)
                }


            }
        }
        return@OnReceiveMessageListener false
    }

    private fun assembleMessageBean(message: io.rong.imlib.model.Message): MessageBean {
        val bean = Gson().fromJson<MessageBean>((message.content as TextMessage).content, MessageBean::class.java!!)
        bean.messageSendId = message.senderUserId
        bean.messageUserId = "" + UserManager.getUserData()!!.id
        return bean
    }

    private fun sendBroadcast(mContext: Context, mIntent: Intent) {
        mContext.sendBroadcast(mIntent)
    }

    private fun refreshGroupInfo(targetId: String) {

        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getSessionInfo(targetId), object : SimpleSubscriber<SessionDetailBean>(context) {
            override fun _onNext(t: SessionDetailBean?) {
                val group = Group(
                        targetId,
                        t!!.sessionInfo.name,
                        Uri.parse(t!!.relatedCaseInfo.caseCover)
                )
                RongIM.getInstance().refreshGroupInfoCache(group)
            }

        })
    }

    init {
        RongIM.setUserInfoProvider(MyUserInfoProvider(), true)
        RongIM.setGroupInfoProvider(MyGroupInfoProvider(), true)
        RongIM.setConversationBehaviorListener(conversationBehaviorListener)//设置会话界面操作的监听器。
        RongIM.setLocationProvider { context, locationCallback -> }
        RongIM.getInstance().setSendMessageListener(sendMessageListener)
        RongIM.setOnReceiveMessageListener(receiveMessageListener)

        RongIM.registerMessageType(PayMessageContent::class.java)
        RongIM.registerMessageTemplate(PayMessageItemProvider())
        RongIM.registerMessageType(PayOrderMessageContent::class.java)
        RongIM.registerMessageTemplate(PayOrderMessageItemProvider())
        setInputProvider()
    }


    private fun setInputProvider() {

        val moduleList = RongExtensionManager.getInstance().extensionModules
        var defaultModule: IExtensionModule? = null
        if (moduleList != null) {
            for (module in moduleList) {
                if (module is DefaultExtensionModule) {
                    defaultModule = module
                    break
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule)
                if (AppTypeManager.isAppC())
                    RongExtensionManager.getInstance().registerExtensionModule(CustomerExtensionModule())
                else
                    RongExtensionManager.getInstance().registerExtensionModule(EmployeeExtensionModule())

            }
        }
    }


}