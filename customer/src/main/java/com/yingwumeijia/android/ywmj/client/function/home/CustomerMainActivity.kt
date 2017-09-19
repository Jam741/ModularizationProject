package com.yingwumeijia.android.ywmj.client.function.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.orhanobut.logger.Logger
import com.yingwumeijia.android.ywmj.client.function.conversation.CustomerConversationListFragment
import com.yingwumeijia.android.ywmj.client.function.person.CustomerPersonFragment
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.function.active.ActiveFragment
import com.yingwumeijia.baseywmj.function.caselist.CaseListFragment
import com.yingwumeijia.baseywmj.function.main.MainActivity
import com.yingwumeijia.baseywmj.function.web.OneWebFragment
import com.yingwumeijia.baseywmj.option.PATHUrlConfig
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

    override fun getTitles(): Array<String> {
        return arrayOf("作品", "优惠", "保障", "聊天", "我的")
    }

    override fun getIconUnselectIds(): IntArray {
        return intArrayOf(R.mipmap.tab_work_ico, R.mipmap.tab_chip_ic, R.mipmap.tab_bz_ico, R.mipmap.tab_messgae_ico, R.mipmap.tab_mine_ico)
    }

    override fun getIconSelectIds(): IntArray {
        return intArrayOf(R.mipmap.tab_work_light_ico, R.mipmap.tab_chip_light_ic, R.mipmap.tab_bz_light_ico, R.mipmap.tab_message_light_ico, R.mipmap.tab_mine_light_ico)
    }

    override fun getFragments(): ArrayList<JBaseFragment> {
        val url = PATHUrlConfig.baseH5Url().replace("appv/", "")
        Logger.d(url)
//        return arrayListOf(CaseListFragment.newInstance(false), ActiveFragment.newInstance(), OneWebFragment.newInstance("http://192.168.28.50:8089/src/template/safeguard/safeguard.html"), CustomerConversationListFragment.newInstance(), CustomerPersonFragment.newInstance())
        return arrayListOf(CaseListFragment.newInstance(false), ActiveFragment.newInstance(), OneWebFragment.newInstance(url + "template/safeguard/safeguard.html?backArrow=0"), CustomerConversationListFragment.newInstance(), CustomerPersonFragment.newInstance())

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}
