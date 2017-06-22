package com.yingwumeijia.baseywmj.function.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.CaseTypeEnum
import com.yingwumeijia.baseywmj.function.caselist.CaseListFragment
import com.yingwumeijia.baseywmj.function.main.MainController
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import kotlinx.android.synthetic.main.case_list_option_title.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.search_layout.*

/**
 * Created by jamisonline on 2017/6/20.
 */

class SearchActivity : JBaseActivity(), AdapterView.OnItemClickListener {


    fun start(activity: Activity) {
        val starter = Intent(activity, SearchActivity::class.java)
        activity.startActivity(starter)
    }

    var drawableIndex: Int = 0


    val controller: MainController by lazy {
        MainController(context, lifecycleSubject)
    }

    val caseListFragment by lazy {
        CaseListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_layout)

        controller.didCaseFilterSet()

        if (supportFragmentManager.findFragmentById(R.id.content) == null)
            supportFragmentManager.beginTransaction().add(R.id.content, caseListFragment).commit()

        drawer_root.run {
            setStatusBarBackground(R.color.colorPrimaryDark)
            setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }

        lv_nav.setOnItemClickListener(this)

        exlv_nav.run {
            setOnGroupClickListener { parent, v, groupPosition, id -> false }
            setOnChildClickListener { parent, v, groupPosition, childPosition, id -> exNavChildItemClick(groupPosition, childPosition) }
        }

        val lp = right_drawer.getLayoutParams()
        lp.width = ScreenUtils.screenWidth * 8 / 12
        right_drawer.layoutParams = lp
    }

    fun exNavChildItemClick(groupPosition: Int, childPosition: Int): Boolean {
        controller.classfilyAdapter!!.setSelected(groupPosition, childPosition)
        closeDrawableLayout(controller.classfilyAdapter!!.selectorId)
        return true
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var caseTypeEnum: CaseTypeEnum? = null
        when (drawableIndex) {
            1 -> {
                if (controller.styleAdapter != null) {
                    controller.styleAdapter!!.setSelected(position)
                    caseTypeEnum = controller.styleAdapter!!.getItem(position)
                }
            }
            2 -> {
                if (controller.hoseTypeAdapter != null) {
                    controller.hoseTypeAdapter!!.setSelected(position)
                    caseTypeEnum = controller.hoseTypeAdapter!!.getItem(position)
                }
            }
            3 -> {
                if (controller.hoseAreaAdapter != null) {
                    controller.hoseAreaAdapter!!.setSelected(position)
                    caseTypeEnum = controller.hoseAreaAdapter!!.getItem(position)
                }
            }
            4 -> {
                if (controller.cityAdapter != null) {
                    controller.cityAdapter!!.setSelected(position)
                    caseTypeEnum = controller.cityAdapter!!.getItem(position)
                }
            }
        }

        if (caseTypeEnum != null)
            closeDrawableLayout(caseTypeEnum!!)
    }

    /**
     * 关闭抽屉
     */
    private fun closeDrawableLayout(caseTypeEnum: CaseTypeEnum) {
        drawer_root.closeDrawers()
        caseListFragment.onClose(caseTypeEnum)
    }

    /**
     * 显示筛选抽屉
     */
    fun showDrawableLayout(navPosition: Int) {
        drawableIndex = navPosition
        when (navPosition) {
            0 -> {
                tv_sliding_title.text = "分类"
                lv_nav.visibility = View.GONE
                exlv_nav.run {
                    visibility = View.VISIBLE
                    setAdapter(controller.classfilyAdapter)
                }
                for (i in 0..controller.classfilyAdapter!!.getGroupCount() - 1) {
                    exlv_nav.expandGroup(i)
                }
            }
            1 -> {
                tv_sliding_title.text = "风格"
                lv_nav.run {
                    visibility = View.VISIBLE
                    adapter = controller.styleAdapter
                }
                exlv_nav.visibility = View.GONE
            }
            2 -> {
                tv_sliding_title.text = "房型"
                lv_nav.run {
                    visibility = View.VISIBLE
                    adapter = controller.hoseTypeAdapter
                }
                exlv_nav.visibility = View.GONE
            }
            3 -> {
                tv_sliding_title.text = "面积"
                lv_nav.run {
                    visibility = View.VISIBLE
                    adapter = controller.hoseAreaAdapter
                }
                exlv_nav.visibility = View.GONE
            }
            4 -> {
                tv_sliding_title.text = "城市"
                lv_nav.run {
                    visibility = View.VISIBLE
                    adapter = controller.cityAdapter
                }
                exlv_nav.visibility = View.GONE
            }
        }

        drawer_root.openDrawer(Gravity.RIGHT)
    }


}
