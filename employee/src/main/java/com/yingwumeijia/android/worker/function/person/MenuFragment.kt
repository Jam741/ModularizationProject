package com.yingwumeijia.android.worker.function.person

import com.yingwumeijia.android.worker.R
import com.yingwumeijia.baseywmj.function.personal.MenuAction
import com.yingwumeijia.baseywmj.function.personal.MenuInfo
import com.yingwumeijia.baseywmj.function.personal.PersonMenuFragment
import java.util.ArrayList

/**
 * Created by jamisonline on 2017/7/3.
 */
class MenuFragment : PersonMenuFragment() {

    val menusForTwirrer by lazy { createMenuInfoListTwirrer() }

    private fun createMenuInfoListTwirrer(): ArrayList<ArrayList<MenuInfo>> {
        val groupList = ArrayList<ArrayList<MenuInfo>>()
        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.bill, R.mipmap.mine_bill_ico, "我的账单"),
                MenuInfo(MenuAction.favourable, R.mipmap.mine_ticket_ico, "我的优惠")))
        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.collect, R.mipmap.mine_save_ico, "我的收藏"),
                MenuInfo(MenuAction.history, R.mipmap.mine_view_history_ico, "历史浏览")))
        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.apply, R.mipmap.mine_apply_ico, "我要入驻", R.color.color_6)))
        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.advice, R.mipmap.mine_feedback_ico, "我的建议")))
        groupList.add(createMenuGroup(
                MenuInfo(MenuAction.invite, R.mipmap.mine_invite_friends_ico, "推荐给朋友")))
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

    override fun initMenuForUserDetailType(userDetailType: Int) {
        refreshMenu(menusForTwirrer)
    }

    override fun itemClick(action: MenuAction) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun itemLongClick(action: MenuAction) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}