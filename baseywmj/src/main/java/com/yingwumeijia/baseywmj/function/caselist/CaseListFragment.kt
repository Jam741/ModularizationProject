package com.yingwumeijia.baseywmj.function.caselist

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.widget.recyclerview.XRecyclerView
import kotlinx.android.synthetic.main.case_list_frag.*
import kotlinx.android.synthetic.main.nav_layout.*


/**
 * Created by jamisonline on 2017/5/31.
 */
class CaseListFragment : JBaseFragment(), CaseListContract.View, XRecyclerView.LoadingListener {

    var pageNum = Config.page

    val mCaseFilterOptionBody: CaseFilterOptionBody = CaseFilterOptionBody()

    val navLayoutHeight = 160f

    val caseListAdapter: CaseListAdapter by lazy {
        CaseListAdapter(activity, null)
    }

    val presenter: CaseListContract.Presenter by lazy {
        CaseListPresenter(this@CaseListFragment, this@CaseListFragment, lifecycleSubject)
    }

    val animatorNavBarHide by lazy {
        createHideNavBarAnimator()
    }

    val animatorNavBarShow by lazy {
        createShowNavBarAnimator()
    }

    val animatorGoTopBtnHide by lazy {
        createGoTopBtnHideAnimator()
    }

    val animatorGoTopBtnShow by lazy {
        createGoTopBtnShowAnimator()
    }

    override fun onLoadComplete(page: Int, empty: Boolean) {
//        if (page == Config.page) {
//            rv_case.setIsnomore(false)
//            rv_case.refreshComplete()
//        } else {
//            rv_case.loadMoreComplete()
//            rv_case.setIsnomore(empty)
//        }
    }


    private fun createGoTopBtnHideAnimator(): ObjectAnimator {
        val animatorBtn = ObjectAnimator.ofFloat(btnScrollTop, View.ALPHA, 1f, 0f)
        animatorBtn.duration = 500
        animatorBtn.interpolator = AccelerateDecelerateInterpolator()
        return animatorBtn
    }

    private fun createGoTopBtnShowAnimator(): ObjectAnimator {
        val animatorBtn = ObjectAnimator.ofFloat(btnScrollTop, View.ALPHA, 0f, 1f)
        animatorBtn.interpolator = AccelerateDecelerateInterpolator()
        animatorBtn.duration = 500
        return animatorBtn
    }

    private fun createShowNavBarAnimator(): ObjectAnimator {
        var o = ObjectAnimator.ofFloat(navlayout, View.TRANSLATION_Y, -navLayoutHeight, 0f)
        o.duration = 500
        return o
    }

    private fun createHideNavBarAnimator(): ObjectAnimator {
        var animator = ObjectAnimator.ofFloat(navlayout, View.TRANSLATION_Y, 0f, -navLayoutHeight)
        animator.duration = 500
        return animator
    }


    override fun onRefresh() {
        pageNum = Config.page
        presenter.loadCaseList(pageNum, mCaseFilterOptionBody)
    }

    override fun onLoadMore() {
        pageNum++
        presenter.loadCaseList(pageNum, mCaseFilterOptionBody)
    }

    override fun onResponseList(list: ArrayList<CaseBean>) {
        if (pageNum == Config.page) {
            caseListAdapter.refresh(list)
        } else {
            caseListAdapter.addRange(list)
        }
    }

    override fun showNavLayoutBar(show: Boolean) {
        if (show) animatorNavBarShow.start()
        else animatorNavBarHide.start()
    }

    override fun showGoTopBotton(show: Boolean) {
        if (show) animatorGoTopBtnShow.start()
        else animatorGoTopBtnHide.start()
    }


    override fun showEmpty(empty: Boolean) {

    }


    companion object {

        fun newInstance(): CaseListFragment {
            val args = Bundle()
            val fragment = CaseListFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.case_list_frag, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_case.run {
            layoutManager = LinearLayoutManager(context)
//            setLoadingListener(this@CaseListFragment)
            setAdapter(caseListAdapter)
        }
        presenter.loadCaseList(pageNum, mCaseFilterOptionBody)
    }

}