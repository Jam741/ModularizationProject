package com.yingwumeijia.baseywmj.function.personal

import android.support.v4.app.Fragment
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CustomerDetailBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import rx.Observable
import rx.subjects.PublishSubject
import java.util.ArrayList

/**
 * Created by jamisonline on 2017/6/11.
 */
class PersonPresenter(var fragment: Fragment, var view: PersonContract.View, var lifecycleSubject: PublishSubject<ActivityLifeCycleEvent>) : PersonContract.Presenter {

    val context = fragment.activity


    override fun initPersonInfo() {
        if (!UserManager.isLogin(context)) return Unit
        var ob: Observable<CustomerDetailBean>
        if (AppTypeManager.isAppC())
            ob = Api.service.getCustomerDetail_C()
        else
            ob = Api.service.getCustomerDetail_E()
        HttpUtil.getInstance().toSubscribe(ob, object : ProgressSubscriber<CustomerDetailBean>(context) {
            override fun _onNext(bean: CustomerDetailBean) {
                UserManager.cacheUserData(bean.customerDto)
                UserManager.cacheTwitterStatus(bean.twitterStatus)
                view.didUpDateUserData()
            }
        }, "cacheKey", ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, false)
    }


    private fun createMenuInfoList_E(): ArrayList<ArrayList<MenuInfo>> {
        val groupList = ArrayList<ArrayList<MenuInfo>>()
        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.order, R.mipmap.mine_bill_ico, "我的订单")))
        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.production, R.mipmap.mine_bill_ico, "我的作品"),
                MenuInfo(MenuAction.collect, R.mipmap.mine_save_ico, "我的收藏")))
        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.advice, R.mipmap.mine_feedback_ico, "我的建议")))
        return groupList
    }


    private fun createMenuGroup(vararg menuInfos: MenuInfo): ArrayList<MenuInfo> {
        val group = ArrayList<MenuInfo>()
        for (m in menuInfos) {
            group.add(m)
        }
        return group
    }
}