package com.yingwumeijia.android.ywmj.client.function.conversation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.function.user.login.LoginActivity
import kotlinx.android.synthetic.main.conversation_list_notlogged.*

/**
 * Created by jamisonline on 2017/7/14.
 */
class ConversationNotLoggedFragment : JBaseFragment() {

    companion object {
        fun newInstance(): ConversationNotLoggedFragment {
            return ConversationNotLoggedFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.conversation_list_notlogged, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener { LoginActivity.startCurrent(activity) }
    }
}