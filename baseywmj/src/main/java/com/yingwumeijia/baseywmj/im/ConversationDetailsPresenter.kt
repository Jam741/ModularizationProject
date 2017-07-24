package com.yingwumeijia.baseywmj.im

import android.app.Activity
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.MemberBean
import com.yingwumeijia.baseywmj.entity.bean.SessionDetailBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import java.util.*

/**
 * Created by jamisonline on 2017/7/21.
 */
class ConversationDetailsPresenter(var context: Activity, var sessionId: String, var view: ConversationDetailsContract.View) : ConversationDetailsContract.Presenter {

    var groupPortrait: String? = null

    var projectName: String? = null

    override fun start() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getSessionInfo(sessionId), object : ProgressSubscriber<SessionDetailBean>(context) {
            override fun _onNext(t: SessionDetailBean?) {
                if (t == null) return
                projectName = t.sessionInfo.name
                view.showProjectName(t.sessionInfo.name)
                val allMembers by lazy { HashMap<String, List<MemberBean>>() }
                val cList by lazy { ArrayList<MemberBean>() }
                val eList by lazy { ArrayList<MemberBean>() }
                for (m in t.sessionInfo.members) {
                    if (m.userType.equals("c")) {
                        cList.add(m)
                    } else if (m.userType.equals("e") && m.userDetailType != 10) {
                        eList.add(m)
                    }
                }
                if (cList != null)
                    allMembers.put("c", cList)
                if (eList != null)
                    allMembers.put("e", eList)
                view.showMemberList(allMembers)

                cList
                        .filter { UserManager.getUserData()!!.imUid.equals(it.imUid) }
                        .forEach { view.showIsSessiOnwne(it.userJoinType.equals("1")) }
                groupPortrait = t.relatedCaseInfo.caseCover
            }

        })
    }


    override fun quitSession() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.quitSession(sessionId), object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                view.dismissConversationSuccess()
            }
        })
    }

    override fun dismissConversation() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.dismissConversation(sessionId), object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                view.dismissConversationSuccess()
            }
        })
    }

    override fun removeMember(memberId: String) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.deleteMemberFromSession(sessionId, memberId), object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                start()
            }
        })
    }
}