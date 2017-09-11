package com.yingwumeijia.android.worker.function.person

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.yingwumeijia.android.worker.R
import com.yingwumeijia.android.worker.function.minecase.MineCaseActivity
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.WebViewManager
import com.yingwumeijia.baseywmj.function.collect.CollectActivity
import com.yingwumeijia.baseywmj.function.opinion.OpinionActivity
import com.yingwumeijia.baseywmj.function.personal.MenuAction
import com.yingwumeijia.baseywmj.function.personal.MenuInfo
import com.yingwumeijia.baseywmj.function.personal.PersonMenuFragment
import com.yingwumeijia.baseywmj.function.personal.PersonalFragment
import com.yingwumeijia.baseywmj.option.PATHUrlConfig
import com.yingwumeijia.baseywmj.utils.net.SeverUrlManager
import com.yingwumeijia.commonlibrary.utils.SPUtils
import kotlinx.android.synthetic.main.person_menu.*
import java.util.ArrayList

/**
 * Created by jamisonline on 2017/7/3.
 */
class MenuFragment : PersonMenuFragment() {

    val menusForNormal by lazy { createMenuInfoListNormal() }

    val menusForJJGW by lazy { createMenuInfoListJJGW() }
    val menusForKFJL by lazy { createMenuInfoListKFJL() }
    val menusForDesigner by lazy { createMenuInfoListDesigner() }

    private fun createMenuInfoListNormal(): ArrayList<ArrayList<MenuInfo>> {
        val groupList = ArrayList<ArrayList<MenuInfo>>()
        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.order, R.mipmap.mine_booking_ico, "我的订单")))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.mineWorker, R.mipmap.mine_my_work_ico, "我的作品"),
                MenuInfo(MenuAction.collect, R.mipmap.mine_save_ico, "我的收藏")))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.advice, R.mipmap.mine_feedback_ico, "意见反馈")))
        return groupList
    }

    private fun createMenuInfoListJJGW(): ArrayList<ArrayList<MenuInfo>> {
        val groupList = ArrayList<ArrayList<MenuInfo>>()
        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.order, R.mipmap.mine_booking_ico, "我的订单")))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.mineCustomer, R.mipmap.mine_my_customer_ic, "我的客户")))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.collect, R.mipmap.mine_save_ico, "我的收藏")))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.advice, R.mipmap.mine_feedback_ico, "意见反馈")))
        return groupList
    }

    private fun createMenuInfoListKFJL(): ArrayList<ArrayList<MenuInfo>> {
        val groupList = ArrayList<ArrayList<MenuInfo>>()
        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.order, R.mipmap.mine_booking_ico, "我的订单"),
                MenuInfo(MenuAction.mineCustomer, R.mipmap.mine_my_customer_ic, "我的客户")))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.mineTeam, R.mipmap.mine_my_team_ic, "我的团队")))


        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.collect, R.mipmap.mine_save_ico, "我的收藏")))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.advice, R.mipmap.mine_feedback_ico, "意见反馈")))
        return groupList
    }

    private fun createMenuInfoListDesigner(): ArrayList<ArrayList<MenuInfo>> {
        val groupList = ArrayList<ArrayList<MenuInfo>>()
        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.material, R.mipmap.mine_zcbt_ic, "主材补贴")))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.order, R.mipmap.mine_booking_ico, "我的订单")))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.mineWorker, R.mipmap.mine_my_work_ico, "我的作品"),
                MenuInfo(MenuAction.collect, R.mipmap.mine_save_ico, "我的收藏")))

        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.advice, R.mipmap.mine_feedback_ico, "意见反馈")))
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
        refreshMenu(menusForNormal)
    }

    override fun initMenuForUserDetailType(userTypeExtension: Int) {
        when (userTypeExtension) {
            PersonalFragment.USER_TYPE_E_NORMAL -> refreshMenu(menusForNormal)
            PersonalFragment.USER_TYPE_E_JJGW -> refreshMenu(menusForJJGW)
            PersonalFragment.USER_TYPE_E_KFJL -> refreshMenu(menusForKFJL)
            PersonalFragment.USER_TYPE_E_DESIGNER -> refreshMenu(menusForDesigner)
        }

        if (userTypeExtension == PersonalFragment.USER_TYPE_E_NORMAL || userTypeExtension == PersonalFragment.USER_TYPE_E_DESIGNER) {
            if (SPUtils.get(activity, "KEY_SHOW_DOT_FOR_MINECASE", false) as Boolean) {
                showDotForMineCase(true)
            } else {
                showDotForMineCase(false)
            }
        }


    }

    fun showDotForMineCase(show: Boolean) {
        for (item in personGroupMenuAdapter.data) {
            for (menu in item) {
                if (menu.action == MenuAction.mineWorker) menu.msgView = show
            }
        }
        personGroupMenuAdapter.notifyDataSetChanged()
    }


    override fun itemClick(action: MenuAction) {

        when (action) {
            MenuAction.order -> WebViewManager.startFullScreen(activity, PATHUrlConfig.baseH5Url() + "#/orderListE")
            MenuAction.material -> WebViewManager.startFullScreen(activity, SeverUrlManager.baseWebUrl() + "#/materialSubsidyE")
            MenuAction.collect -> CollectActivity.start(activity)
            MenuAction.mineWorker -> MineCaseActivity.start(activity)
            MenuAction.advice -> OpinionActivity.start(activity)
            MenuAction.mineCustomer -> WebViewManager.startFullScreen(activity, SeverUrlManager.baseWebUrl() + "#/mineCustomer")
            MenuAction.mineTeam -> WebViewManager.startFullScreen(activity, SeverUrlManager.baseWebUrl() + "#/consultantList")
        }
    }

    override fun itemLongClick(action: MenuAction) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}