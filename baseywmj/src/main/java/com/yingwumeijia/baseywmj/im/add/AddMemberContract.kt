package com.yingwumeijia.baseywmj.im.add


import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.AddSessionMember
import com.yingwumeijia.baseywmj.entity.bean.MemberBean

/**
 * Created by Jam on 16/9/9 上午11:21.
 * Describe:
 */
interface AddMemberContract {

    interface View : JBaseView {


        fun showMemberList(memberBeanList: List<MemberBean>)

        fun showEmptyLayout(isEmpty: Boolean)

        fun insertMember(memberBean: MemberBean)

        fun showAddSuccess()


    }

    interface Presenter : JBasePresenter {

        fun searchCustomer(phoneNum: String)

        fun searchEmployeeWithName(name: String)

        fun searchEmployeeWithCompany(company: String)

        fun addMemberToSession(addSessionMember: AddSessionMember)


    }
}
