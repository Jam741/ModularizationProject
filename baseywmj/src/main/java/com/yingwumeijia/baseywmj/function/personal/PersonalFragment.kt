package com.yingwumeijia.baseywmj.function.personal

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.personal.c.LoggedFragment
import com.yingwumeijia.baseywmj.function.personal.c.NotLoggedFragment
import com.yingwumeijia.baseywmj.function.setting.SettingActivity
import com.yingwumeijia.baseywmj.function.user.login.LoginActivity
import kotlinx.android.synthetic.main.person_frag.*
import kotlinx.android.synthetic.main.person_title_layout.*

/**
 * Created by jamisonline on 2017/5/31.
 */

enum class MenuAction {
    order, //订单
    production, //作品
    bill, //账单
    favourable, //优惠
    collect, //收藏
    twitter, //推客
    apply, //申请入驻
    advice, //我的建议
    beginner, //新手引导
    testH5, //测试H5入口
    history, //历史浏览
    invite//邀请
}

class PersonalFragment : JBaseFragment(), PersonContract.View, PersonGroupMenuAdapter.MenuOnItemClickListener, PersonGroupMenuAdapter.MenuOnItemLongClickListener {

    val presenter: PersonContract.Presenter by lazy {
        PersonPresenter(this, this, lifecycleSubject)
    }


    val loggedCFragment by lazy { LoggedFragment.newInstance() }

    val loggedEFragment by lazy { LoggedFragment.newInstance() }

    val notLogginCFragment by lazy { NotLoggedFragment.newInstance() }

    val personGroupMenuAdapter: PersonGroupMenuAdapter by lazy {
        PersonGroupMenuAdapter(activity, this, this)
    }

    override fun itemClick(action: MenuAction) {
        when (action) {
            MenuAction.order -> ""
        }
    }

    override fun itemLongClick(action: MenuAction) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoginView(logIn: Boolean) {
        Logger.d(logIn)
        if (isAppC) {
            if (logIn) {
                changeHeadFragment(loggedCFragment)
            } else {
                changeHeadFragment(notLogginCFragment)
            }
        } else {
            if (logIn) {
                changeHeadFragment(loggedCFragment)
            } else {
                activity.finish()
                LoginActivity.start(context.activity, false)
            }
        }
    }

    override fun refreshLoginView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun changeHeadFragment(fragment: JBaseFragment) {
        val currentFragment = childFragmentManager.findFragmentById(R.id.head_content)
        if (currentFragment == null) {
            childFragmentManager.beginTransaction().add(R.id.head_content, fragment).commit()
        } else {
            if (currentFragment != fragment)
                childFragmentManager.beginTransaction().replace(R.id.head_content, fragment).commit()
        }
    }

    override fun showMenus(menuInfosList: ArrayList<ArrayList<MenuInfo>>) {
        personGroupMenuAdapter.refreshMenu(menuInfosList)
    }

    companion object {
        fun newInstance(): PersonalFragment {
            return PersonalFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.person_frag, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initMenu()
        rv_menu.run {
            layoutManager = LinearLayoutManager(context)
            adapter = personGroupMenuAdapter
        }

        btn_setting.setOnClickListener { SettingActivity.start(activity) }
        btn_message.setOnClickListener { TODO("去站内信页面") }

    }


    override fun onResume() {
        super.onResume()
        if (UserManager.isLogin(activity))
            presenter.initPersonInfo()
        showLoginView(UserManager.isLogin(activity))
    }

    /**
     * 界面是否对用户可见状态回调
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            showLoginView(UserManager.isLogin(activity))
        } else {

        }
    }

    override fun didUpDateUserData() {
        if (UserManager.isLogin(activity))
            if (isAppC) loggedCFragment.onUserDataChanged() else loggedEFragment.onUserDataChanged()
    }


}