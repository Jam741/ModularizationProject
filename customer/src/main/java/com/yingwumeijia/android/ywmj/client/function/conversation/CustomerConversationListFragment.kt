package com.yingwumeijia.android.ywmj.client.function.conversation

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.conversation.list.ConversationNotLoggedFragment
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.function.UserManager
import kotlinx.android.synthetic.main.conversation_list_container.*
import com.netease.nim.uikit.recent.RecentContactsFragment


/**
 * Created by jamisonline on 2017/7/14.
 *
 * 会话列表容器 Fragment 解耦会话列表
 */
class CustomerConversationListFragment : JBaseFragment() {

    private val contactsFragment: RecentContactsFragment by lazy { assembleRecentContactsFragment() }

    private fun assembleRecentContactsFragment(): RecentContactsFragment {
        return RecentContactsFragment()
    }


    companion object {
        fun newInstance(): CustomerConversationListFragment {
            return CustomerConversationListFragment()
        }
    }

    val notLoggedFragment by lazy { ConversationNotLoggedFragment() }

    val loggedFragment by lazy { contactsFragment }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.conversation_list_container, container, false)
    }

    //
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topTitle.text = "聊天"
    }

    //
    override fun onResume() {
        super.onResume()
        if (UserManager.isLogin(activity)) putFragment(loggedFragment) else putFragment(notLoggedFragment)
    }

    private fun putFragment(fragment: Fragment) {
        val currentFragment = childFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            childFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        } else if (currentFragment != fragment) {
            childFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }
    }
}