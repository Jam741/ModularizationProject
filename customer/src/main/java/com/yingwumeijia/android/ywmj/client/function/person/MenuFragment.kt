package com.yingwumeijia.android.ywmj.client.function.person

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.camitor.pdfviewlibrary.PDFViewManager
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.bill.MyBillActivity
import com.yingwumeijia.android.ywmj.client.function.coupon.CouponActivity
import com.yingwumeijia.android.ywmj.client.function.enter.EnterActivity
import com.yingwumeijia.android.ywmj.client.function.historyView.HistoryViewActivity
import com.yingwumeijia.android.ywmj.client.function.invite.InviteActivity
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.AdvisorInfoBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.WebViewManager
import com.yingwumeijia.baseywmj.function.collect.CollectActivity
import com.yingwumeijia.baseywmj.function.opinion.OpinionActivity
import com.yingwumeijia.baseywmj.function.personal.MenuAction
import com.yingwumeijia.baseywmj.function.personal.MenuInfo
import com.yingwumeijia.baseywmj.function.personal.PersonMenuFragment
import com.yingwumeijia.baseywmj.function.personal.PersonalFragment
import com.yingwumeijia.baseywmj.option.PATHUrlConfig
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.CallUtils
import com.yingwumeijia.commonlibrary.utils.SPUtils
import com.yingwumeijia.commonlibrary.utils.TextViewUtils
import com.yingwumeijia.commonlibrary.utils.adapter.CommonAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.ViewHolder
import kotlinx.android.synthetic.main.person_menu.*

/**
 * Created by jamisonline on 2017/7/3.
 */
class MenuFragment : PersonMenuFragment() {


    val menusForNormal by lazy { createMenuInfoListNormal() }

    val menusForTwitter by lazy { createMenuInfoListTwitter() }

    val topMenus by lazy { createMenuInfoListTop() }

    val topAdapter by lazy { createTopAdapter() }

    fun createTopAdapter(): CommonAdapter<MenuInfo> {
        return object : CommonAdapter<MenuInfo>(getContext(), topMenus, R.layout.item_person_top) {
            override fun conver(helper: ViewHolder?, item: MenuInfo?, position: Int) {
                var tvIcon = helper!!.getView<TextView>(R.id.icon)
                tvIcon.text = item!!.text
                TextViewUtils.setDrawableToTop(mContext, tvIcon, item.icon)
            }

        }
    }

    private fun createMenuInfoListTwitter(): ArrayList<ArrayList<MenuInfo>> {
        val groupList = menusForNormal
        groupList[2].add(MenuInfo(MenuAction.twitter, R.mipmap.mine_apply_tk_ico, "我的推客"))
        return groupList
    }

    private fun createMenuInfoListNormal(): ArrayList<ArrayList<MenuInfo>> {
        val groupList = ArrayList<ArrayList<MenuInfo>>()



        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.collect, R.mipmap.mine_save_ico, "我的收藏"),
                MenuInfo(MenuAction.history, R.mipmap.mine_view_history_ico, "历史浏览")
        ))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.bill, R.mipmap.mine_bill_ico, "活动账单")
        ))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.apply, R.mipmap.mine_apply_ico, "我要入驻", R.color.color_6)
                //MenuInfo(MenuAction.twitter, R.mipmap.mine_apply_tk_ico, "我的推客")
        ))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.advice, R.mipmap.mine_feedback_ico, "我的建议"),
                MenuInfo(MenuAction.invite, R.mipmap.mine_invite_friends_ico, "推荐给朋友")
        ))

        return groupList
    }


    private fun createMenuInfoListTop(): ArrayList<MenuInfo> {
        val groupList = ArrayList<MenuInfo>()
        groupList.add(MenuInfo(MenuAction.help, R.mipmap.mine_service_ic, "我要帮助"))
        groupList.add(MenuInfo(MenuAction.favourable, R.mipmap.mine_favourable_ic, "我的优惠"))
        groupList.add(MenuInfo(MenuAction.order, R.mipmap.mine_order_ic, "我的订单"))

        return groupList
    }

    private fun createMenuGroup(vararg menuInfos: MenuInfo): ArrayList<MenuInfo> {
        val group = ArrayList<MenuInfo>()
        for (m in menuInfos) {
            group.add(m)
        }
        return group
    }


    companion object {
        fun newInstance(): MenuFragment {
            return MenuFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.person_menu, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_menu.run {
            layoutManager = LinearLayoutManager(context)
            adapter = personGroupMenuAdapter
        }

        gv_top.run {
            adapter = topAdapter
            setOnItemClickListener { parent, view, position, id ->
                itemClick(topAdapter.getItem(position).action)
            }
        }

        refreshMenu(menusForNormal)
    }

    private fun getHelp() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getAdvisorInfo(), object : ProgressSubscriber<AdvisorInfoBean>(getContext()) {
            override fun _onNext(t: AdvisorInfoBean?) {
                if (t != null)
                    CallUtils.callPhone(t.userPhone, activity)
            }
        })
    }

    override fun initMenuForUserDetailType(userTypeExtension: Int) {
        when (userTypeExtension) {
            PersonalFragment.USER_TYPE_C_NORMAL -> refreshMenu(menusForNormal)
            PersonalFragment.USER_TYPE_C_TWITTER -> refreshMenu(menusForTwitter)
        }
    }


    override fun itemClick(action: MenuAction) {
        when (action) {
            MenuAction.help -> getHelp()
            MenuAction.favourable -> if (UserManager.precedent(activity)) CouponActivity.start(activity, true)
            MenuAction.order -> if (UserManager.precedent(activity)) WebViewManager.startFullScreen(activity, PATHUrlConfig.baseH5Url() + "#/orderList")
            MenuAction.bill -> if (UserManager.precedent(activity)) MyBillActivity.start(activity)
            MenuAction.collect -> if (UserManager.precedent(activity)) CollectActivity.start(activity)
            MenuAction.history -> if (UserManager.precedent(activity)) HistoryViewActivity.start(activity)
            MenuAction.apply -> EnterActivity.start(activity)
            MenuAction.twitter -> if (UserManager.precedent(activity)) WebViewManager.startFullScreen(activity, PATHUrlConfig.baseH5Url() +"#/twitterInfo")
            MenuAction.advice -> OpinionActivity.start(activity)
            MenuAction.invite -> InviteActivity.start(activity)

        }
    }

    override fun itemLongClick(action: MenuAction) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}