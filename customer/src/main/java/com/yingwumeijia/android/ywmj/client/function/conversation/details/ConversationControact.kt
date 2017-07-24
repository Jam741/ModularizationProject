package com.yingwumeijia.android.ywmj.client.function.conversation.details

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.CommonLanguage

/**
 * Created by jamisonline on 2017/7/14.
 */
interface ConversationControact {


    interface View : JBaseView {

        fun showBeginnerGuidance(show: Boolean)

        fun showQuickInputPop(show: Boolean)

        fun showAddInputQuickDialog(show: Boolean)

        fun showDeleteInputQuickDialog(commonLanguage: CommonLanguage, position: Int)

        fun showCallContactDialog(show: Boolean)

//        fun initCaseState(state: Int)
    }

    interface Presenter : JBasePresenter {

        fun sendWith(textMessage: String)

        fun insertInput(content: String)

        fun deleteInputQuick(id: Int, position: Int)

        fun callContactPhone(sessionId: String)

        fun start()


    }


}