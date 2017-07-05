package com.yingwumeijia.baseywmj.function.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.personal.c.NotLoggedFragment
import com.yingwumeijia.baseywmj.function.setting.SettingActivity
import com.yingwumeijia.baseywmj.function.user.login.LoginActivity
import kotlinx.android.synthetic.main.person_title_layout.*

/**
 * Created by jamisonline on 2017/5/31.
 */


abstract class PersonalFragment : JBaseFragment(), PersonContract.View {

    val presenter: PersonContract.Presenter by lazy {
        PersonPresenter(this, this, lifecycleSubject)
    }

    val notLogginCFragment by lazy { NotLoggedFragment.newInstance() }


    val loggedFragment by lazy { getLoggedHeaderFragment() }

    abstract fun getLoggedHeaderFragment(): BaseLoggedFragment

    val personMenuFragment by lazy { getMenuFragment() }

    abstract fun getMenuFragment(): PersonMenuFragment


    override fun showLoginView(logIn: Boolean) {
        if (isAppC) {
            if (logIn) {
                changeHeadFragment(loggedFragment)
            } else {
                changeHeadFragment(notLogginCFragment)
            }
        } else {
            if (logIn) {
                changeHeadFragment(loggedFragment)
            } else {
                activity.finish()
                LoginActivity.start(context.activity, false)
            }
        }
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

    override fun showMenus() {
        val currentFragment = childFragmentManager.findFragmentById(R.id.menu_content)
        if (currentFragment == null)
            childFragmentManager.beginTransaction().add(R.id.menu_content, personMenuFragment).commit()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.person_frag, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_setting.setOnClickListener { SettingActivity.start(activity) }
        btn_message.setOnClickListener { StartActivityManager.startMessageActivity(activity) }
        showMenus()
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
        if (UserManager.isLogin(activity)) {
            loggedFragment.onUserDataChanged()
            personMenuFragment.initMenuForUserDetailType(UserManager.getUserData()!!.userDetailType)
        }

    }

}