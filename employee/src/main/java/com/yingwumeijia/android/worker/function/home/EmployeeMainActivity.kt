package com.yingwumeijia.android.worker.function.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.yingwumeijia.android.worker.function.person.EmployeePersonFragment
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.function.active.ActiveFragment
import com.yingwumeijia.baseywmj.function.caselist.CaseListFragment
import com.yingwumeijia.baseywmj.function.main.MainActivity
import java.util.*

/**
 * Created by jamisonline on 2017/7/3.
 */
class EmployeeMainActivity : MainActivity() {

    companion object {
        fun start(context: Activity) {
            val starter = Intent(context, EmployeeMainActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun getTitles(): Array<String> {
        return arrayOf("作品", "优惠", "聊天", "我的")
    }

    override fun getIconUnselectIds(): IntArray {
        return intArrayOf(R.mipmap.tab_work_ico, R.mipmap.tab_chip_ic, R.mipmap.tab_messgae_ico, R.mipmap.tab_mine_ico)
    }

    override fun getIconSelectIds(): IntArray {
        return intArrayOf(R.mipmap.tab_work_light_ico, R.mipmap.tab_chip_light_ic, R.mipmap.tab_message_light_ico, R.mipmap.tab_mine_light_ico)
    }


    override fun getFragments(): ArrayList<JBaseFragment> {
        return arrayListOf(CaseListFragment.newInstance(false), ActiveFragment.newInstance(), ActiveFragment.newInstance(), EmployeePersonFragment.newInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}