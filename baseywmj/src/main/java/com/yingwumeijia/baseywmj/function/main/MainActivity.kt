package com.yingwumeijia.baseywmj.function.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.empty.TabEntity
import com.yingwumeijia.baseywmj.function.active.ActiveFragment
import com.yingwumeijia.baseywmj.function.caselist.CaseListFragment
import com.yingwumeijia.baseywmj.function.im.ConversationListFragment
import com.yingwumeijia.baseywmj.function.personal.PersonalFragment
import com.yingwumeijia.commonlibrary.base.BaseActivity
import kotlinx.android.synthetic.main.main_act.*
import kotlinx.android.synthetic.main.main_page.*

class MainActivity : BaseActivity(), OnTabSelectListener, ViewPager.OnPageChangeListener {


    /**
     * MainViewPage
     */
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        tl_main.currentTab = position
    }

    /**
     * TabLayout
     */
    override fun onTabSelect(position: Int) {
        viewpager.setCurrentItem(position, false)
    }

    override fun onTabReselect(position: Int) {
    }


    val mTitles = arrayOf("作品", "优惠", "聊天", "我的")

    val mIconUnselectIds = intArrayOf(R.mipmap.tab_work_ico, R.mipmap.tab_favourable_ico, R.mipmap.tab_messgae_ico, R.mipmap.tab_mine_ico)

    val mIconSelectIds = intArrayOf(R.mipmap.tab_work_light_ico, R.mipmap.tab_favourable_light_ico, R.mipmap.tab_message_light_ico, R.mipmap.tab_mine_light_ico)

    val mTabEntities = ArrayList<CustomTabEntity>()

    val mFragments = arrayListOf(CaseListFragment.newInstance(), ActiveFragment.newInstance(), ConversationListFragment.newInstance(), PersonalFragment.newInstance())

    var mPageAdapter = MainPageAdapter(mFragments, supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)


        for (i in mTitles.indices) {
            mTabEntities.add(i, TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }

        viewpager.adapter = mPageAdapter
        viewpager.addOnPageChangeListener(this@MainActivity)
        tl_main.setTabData(mTabEntities)
        tl_main.setOnTabSelectListener(this@MainActivity)

    }


}
