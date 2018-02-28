package com.yingwumeijia.baseywmj.im

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.MemberBean

/**
 * Created by jamisonline on 2017/7/21.
 */
interface ConversationDetailsContract {

    interface View : JBaseView {

        fun showMemberList(teamListItemBeanList: Map<String, List<MemberBean>>)

        fun showProjectName(projectName: String)

        fun showIsSessiOnwne(onwner: Boolean)

        fun dismissConversationSuccess()

    }


    interface Presenter : JBasePresenter {

        fun start()
        
        fun quitSession()

        fun dismissConversation()

        fun removeMember(memberId: String)
    }
}