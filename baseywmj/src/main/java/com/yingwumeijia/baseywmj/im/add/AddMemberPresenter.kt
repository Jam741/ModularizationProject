package com.yingwumeijia.baseywmj.im.add

import android.content.Context
import android.widget.Toast
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.AddSessionMember
import com.yingwumeijia.baseywmj.entity.bean.MemberBean
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil

/**
 * Created by jamisonline on 2017/7/24.
 */
class AddMemberPresenter(var context: Context, var view: AddMemberContract.View, var sessionId: String) : AddMemberContract.Presenter {
    
    override fun addMemberToSession(addSessionMember: AddSessionMember) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.addMemberToSession(addSessionMember), object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                view.showAddSuccess()
            }
        })
    }

    override fun searchCustomer(phoneNum: String) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.searchCustomer(sessionId, phoneNum), object : ProgressSubscriber<MemberBean>(context) {
            override fun _onNext(t: MemberBean?) {
                view.showEmptyLayout(t == null)
                if (t != null)
                    view.insertMember(t)
                else
                    Toast.makeText(context, "什么都没有搜到", Toast.LENGTH_SHORT).show()

            }

        })
    }

    override fun searchEmployeeWithName(name: String) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.searchEmployeeList(sessionId, name, null), object : ProgressSubscriber<List<MemberBean>>(context) {
            override fun _onNext(t: List<MemberBean>?) {
                view.showEmptyLayout(ListUtil.isEmpty(t))
                if (!ListUtil.isEmpty(t))
                    view.showMemberList(t!!)
                else
                    Toast.makeText(context, "什么都没有搜到", Toast.LENGTH_SHORT).show()

            }

        })
    }

    override fun searchEmployeeWithCompany(company: String) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.searchEmployeeList(sessionId, null, company), object : ProgressSubscriber<List<MemberBean>>(context) {
            override fun _onNext(t: List<MemberBean>?) {
                view.showEmptyLayout(ListUtil.isEmpty(t))
                if (!ListUtil.isEmpty(t))
                    view.showMemberList(t!!)
                else
                    Toast.makeText(context, "什么都没有搜到", Toast.LENGTH_SHORT).show()

            }

        })
    }
}