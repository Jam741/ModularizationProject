package com.yingwumeijia.baseywmj.nimim.conversation.customer

import com.netease.nimlib.sdk.msg.model.IMMessage
import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.CommonLanguage

/**
 * Created by jamisonline on 2017/7/14.
 */
interface ConversationControact {


    interface View : JBaseView {

        fun showConversationTitle(title: String)

        fun showBeginnerGuidance(show: Boolean)

        fun showQuickInputPop(show: Boolean)

        fun showAddInputQuickDialog(show: Boolean)

        fun showDeleteInputQuickDialog(commonLanguage: CommonLanguage, position: Int)

        fun showCallContactDialog(show: Boolean)

        fun sendMessage(imMessage: IMMessage)
    }

    interface Presenter : JBasePresenter {

        fun sendWith(textMessage: String)

        fun insertInput(content: String)

        fun deleteInputQuick(id: Int, position: Int)

        fun callContactPhone(sessionId: String)

        fun start()


    }


}