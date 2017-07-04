package com.yingwumeijia.android.ywmj.client.function.home

import android.app.Activity
import android.content.Intent
import com.yingwumeijia.android.ywmj.client.function.person.CustomerPersonFragment
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.function.active.ActiveFragment
import com.yingwumeijia.baseywmj.function.caselist.CaseListFragment
import com.yingwumeijia.baseywmj.function.im.ConversationListFragment
import com.yingwumeijia.baseywmj.function.main.MainActivity
import java.util.*

/**
 * Created by jamisonline on 2017/7/3.
 */
class CustomerMainActivity : MainActivity() {

    companion object {
        fun start(context: Activity) {
            val starter = Intent(context, CustomerMainActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun getFragments(): ArrayList<JBaseFragment> {
        return arrayListOf(CaseListFragment.newInstance(false), ActiveFragment.newInstance(), ConversationListFragment.newInstance(), CustomerPersonFragment.newInstance())

    }
}