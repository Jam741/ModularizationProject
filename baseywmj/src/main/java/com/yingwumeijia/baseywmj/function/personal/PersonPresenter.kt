package com.yingwumeijia.baseywmj.function.personal

import android.net.Uri
import android.support.v4.app.Fragment
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.CustomerDetailBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.SPUtils
import io.rong.imkit.RongIM
import io.rong.imlib.model.UserInfo
import rx.Observable
import rx.subjects.PublishSubject
import java.util.ArrayList

/**
 * Created by jamisonline on 2017/6/11.
 */
class PersonPresenter(var fragment: Fragment, var view: PersonContract.View, var lifecycleSubject: PublishSubject<ActivityLifeCycleEvent>) : PersonContract.Presenter {

    val context = fragment.activity


    val isAppC = AppTypeManager.isAppC()


    override fun initPersonInfo() {
        if (!UserManager.isLogin(context)) return Unit
        var ob: Observable<CustomerDetailBean>
        if (isAppC)
            ob = Api.service.getCustomerDetail_C()
        else
            ob = Api.service.getCustomerDetail_E()
        HttpUtil.getInstance().toSubscribe(ob, object : ProgressSubscriber<CustomerDetailBean>(context) {
            override fun _onNext(bean: CustomerDetailBean) {


                if (isAppC) {
                    if (bean.twitterStatus != -1) {
                        SPUtils.put(context, Constant.KEY_TWITTER_URL, bean.twitterBaseUrl)
                        bean.customerDto.userTypeExtension = PersonalFragment.USER_TYPE_C_TWITTER
                    } else {
                        bean.customerDto.userTypeExtension = PersonalFragment.USER_TYPE_C_NORMAL
                    }
                    UserManager.cacheUserData(bean.customerDto)


                    if (RongIM.getInstance() != null)
                        RongIM.getInstance().refreshUserInfoCache(
                                UserInfo(
                                        bean.customerDto.imUid,
                                        bean.customerDto.showName,
                                        Uri.parse(bean.customerDto.showHead))
                        )

                } else {
                    when (bean.employeeDto.userDetailType) {
                        10 -> {
                            if (bean.isHomeAdvisorManager) {
                                bean.employeeDto.userTypeExtension = PersonalFragment.USER_TYPE_E_KFJL
                            } else {
                                bean.employeeDto.userTypeExtension = PersonalFragment.USER_TYPE_E_JJGW
                            }
                            Logger.d("=====" + bean.employeeDto.userTypeExtension)
                        }
                        1 -> {
                            bean.employeeDto.userTypeExtension = PersonalFragment.USER_TYPE_E_DESIGNER
                        }
                    }
                    if (bean.employeeDto.userDetailType != 10 && bean.employeeDto.userDetailType != 1) {
                        bean.employeeDto.userTypeExtension = PersonalFragment.USER_TYPE_E_NORMAL
                    }

                    if (bean.commentNum > 0) {
                        SPUtils.put(context, "KEY_SHOW_DOT_FOR_MINECASE", true)
                    } else {
                        SPUtils.put(context, "KEY_SHOW_DOT_FOR_MINECASE", false)
                    }

                    UserManager.cacheUserData(bean.employeeDto)

                    if (RongIM.getInstance() != null)
                        RongIM.getInstance().refreshUserInfoCache(
                                UserInfo(
                                        bean.employeeDto.imUid,
                                        bean.employeeDto.showName,
                                        Uri.parse(bean.employeeDto.showHead))
                        )
                }
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