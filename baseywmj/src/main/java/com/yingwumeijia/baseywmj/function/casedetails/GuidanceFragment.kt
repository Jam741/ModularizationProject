package com.yingwumeijia.baseywmj.function.casedetails

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.function.UserManager
import kotlinx.android.synthetic.main.case_details_guidance.*
import kotlinx.android.synthetic.main.common_sliding_tab_layout.*

/**
 * Created by jamisonline on 2017/8/1.
 */
class GuidanceFragment : JBaseFragment() {


    val mTitles = arrayOf("现场实景", "原创团队", "项目资料")

    private var isLastPage: Boolean = false
    private var isDragPage: Boolean = false
    private var canJumpPage = true


    val views = ArrayList<View>()

    private fun createViews(): ArrayList<View> {
        return ArrayList<View>().apply {
            add(GuidanceView(getContext(), R.mipmap.work_details_guild_1))
            add(GuidanceView(getContext(), R.mipmap.work_details_guild_2))
            add(GuidanceView(getContext(), R.mipmap.work_details_guild_3))
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.case_details_guidance, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views.addAll(createViews())
        vp_guidance.adapter = MyPageAdapter()
        vp_guidance.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                isDragPage = state == 1
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (isLastPage && isDragPage && positionOffsetPixels == 0) {   //当前页是最后一页，并且是拖动状态，并且像素偏移量为0
                    if (canJumpPage) {
                        canJumpPage = false
                        close(false)
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                isLastPage = position == views.size - 1
            }
        })
        tl_title.setViewPager(vp_guidance, mTitles)
        btn_dontShow.setOnClickListener { close(true) }
        btn_know.setOnClickListener { close(false) }
    }

    fun close(dontShow: Boolean) {
        if (dontShow)
            UserManager.setNotGuidanceCaseDetailsNeedShow(getContext())
        (activity as CaseDetailActivity).removeGuidanceFragment()
    }

    internal inner class MyPageAdapter : PagerAdapter() {

        override fun getCount(): Int {
            return views.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(views[position])
            return views[position]
        }

        override fun getItemPosition(`object`: Any?): Int {
            return views.indexOf(`object`)
        }

    }
}