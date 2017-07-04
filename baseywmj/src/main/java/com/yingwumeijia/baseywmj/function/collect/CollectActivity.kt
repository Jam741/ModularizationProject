package com.yingwumeijia.baseywmj.function.collect

import android.os.Bundle
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.function.adapter.TabWithPagerAdapter
import com.yingwumeijia.baseywmj.function.collect.cases.CaseCollectFragment
import com.yingwumeijia.baseywmj.function.collect.company.CompanyCollectFragment
import com.yingwumeijia.baseywmj.function.collect.employee.EmployeeCollectFragment
import kotlinx.android.synthetic.main.collect_act.*
import kotlinx.android.synthetic.main.common_sliding_tab_layout.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/6/30.
 */
class CollectActivity : JBaseActivity() {


    val titles = arrayOf("作品", "业者", "公司")

    val fragments = arrayListOf(CaseCollectFragment(), EmployeeCollectFragment(), CompanyCollectFragment())

    val adapter by lazy { TabWithPagerAdapter(supportFragmentManager, titles, fragments) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collect_act)

        topTitle.text = "收藏"
        topLeft.setOnClickListener { close() }

        vp_content.run {
            adapter = this@CollectActivity.adapter
            offscreenPageLimit = 2
        }
        tl_title.setViewPager(vp_content)
    }

}