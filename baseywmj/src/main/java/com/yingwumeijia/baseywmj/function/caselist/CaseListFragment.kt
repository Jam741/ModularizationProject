package com.yingwumeijia.baseywmj.function.caselist

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.baseywmj.entity.bean.CaseTypeEnum
import com.yingwumeijia.baseywmj.function.main.MainActivity
import com.yingwumeijia.baseywmj.function.search.SearchActivity
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.widget.recycler.LoadingMoreFooter
import com.yingwumeijia.commonlibrary.widget.recycler.XRecyclerView
import kotlinx.android.synthetic.main.case_list_frag.*
import kotlinx.android.synthetic.main.empty_layout.*
import kotlinx.android.synthetic.main.nav_layout.*


/**
 * Created by jamisonline on 2017/5/31.
 * 案例列表
 */
class CaseListFragment : JBaseFragment(), CaseListContract.View, XRecyclerView.LoadingListener, View.OnClickListener {

    var pageNum = Config.page

    val fromSearch: Boolean by lazy { arguments.getBoolean(Constant.KEY_CASE_LIST_SOURCE) }

    val mCaseFilterOptionBody: CaseFilterOptionBody = CaseFilterOptionBody()

    val navLayoutHeight = 160f

    val caseListAdapter: CaseListAdapter by lazy {
        CaseListAdapter(this, null)
    }

    val caseListScrollListener: RecyclerView.OnScrollListener by lazy {
        createCaseListScrollListener()
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


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_classify -> {
                drawableIndex = 0
                if (fromSearch)
                    (activity as SearchActivity).showDrawableLayout(drawableIndex)
                else
                    (activity as MainActivity).showDrawableLayout(drawableIndex)
            }
            R.id.btn_style -> {
                drawableIndex = 1
                if (fromSearch)
                    (activity as SearchActivity).showDrawableLayout(drawableIndex)
                else
                    (activity as MainActivity).showDrawableLayout(drawableIndex)
            }
            R.id.btn_fx -> {
                drawableIndex = 2
                if (fromSearch)
                    (activity as SearchActivity).showDrawableLayout(drawableIndex)
                else
                    (activity as MainActivity).showDrawableLayout(drawableIndex)
            }
            R.id.btn_area -> {
                drawableIndex = 3
                if (fromSearch)
                    (activity as SearchActivity).showDrawableLayout(drawableIndex)
                else
                    (activity as MainActivity).showDrawableLayout(drawableIndex)
            }
            R.id.btn_city -> {
                drawableIndex = 4
                if (fromSearch)
                    (activity as SearchActivity).showDrawableLayout(drawableIndex)
                else
                    (activity as MainActivity).showDrawableLayout(drawableIndex)
            }
        }
    }

    override fun onLoadComplete(page: Int, empty: Boolean) {
        if (page == Config.page) {
            rv_case.setIsnomore(false)
            rv_case.refreshComplete()
        } else {
            rv_case.loadMoreComplete()
            rv_case.setIsnomore(empty)
        }
    }


    private var isshow = true
    private var isshowTopBtn = false
    private var disy = 0

    private fun createCaseListScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var firstVisibleItem = (rv_case.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (firstVisibleItem == 0) {//当前Item的第一个是否是列表的第一个，如果是第一个已改显示
                    if (!isshow) {//如果此时不在显示中  得显示
                        isshow = true
                        animatorNavBarShow.start()
                    }
                } else {//不是第一个
                    if (disy > 25 && isshow) {
                        animatorNavBarHide.start()
                        isshow = false
                        disy = 0//归零

                        if (isshowTopBtn) {
                            isshowTopBtn = false
                            animatorGoTopBtnHide.start()
                        }
                    }
                    if (disy < -25 && !isshow) {
                        animatorNavBarShow.start()
                        isshow = true
                        disy = 0

                        if (!isshowTopBtn) {
                            isshowTopBtn = true
                            animatorGoTopBtnShow.start()
                        }
                    }
                }

                if (firstVisibleItem == 1) {
                    if (isshowTopBtn) {
                        isshowTopBtn = false
                        animatorGoTopBtnHide.start()
                    }
                }

                if (isshow && dy > 0 || !isshow && dy < 0) {//增加的滑动距离只有在触发两种状态才叠加
                    disy += dy
                }
            }
        }
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
        if (empty) {
            if (fromSearch) rv_case.visibility = View.GONE
            empty_layout.visibility = View.VISIBLE
        } else {
            if (fromSearch) rv_case.visibility = View.VISIBLE
            empty_layout.visibility = View.GONE
        }
    }


    fun onClose(caseTypeEnum: CaseTypeEnum) {
        refreshNavigationStatus(caseTypeEnum)
        pageNum = Config.page
        presenter.loadCaseList(pageNum, mCaseFilterOptionBody)
    }


    var drawableIndex: Int = 0


    fun refreshNavigationStatus(caseTypeEnum: CaseTypeEnum) {
        var showText = caseTypeEnum.name
        if (showText == null) showText = "全部"
        when (drawableIndex) {
            0 -> {
                if (showText!!.contains("全部")) {
                    tv_classify.setText("分类")
                } else {
                    tv_classify.setText(showText)
                }
                mCaseFilterOptionBody.decorateType = caseTypeEnum.id
                mCaseFilterOptionBody.caseType = caseTypeEnum.categoryCode
            }
            1 -> {
                if (showText == "全部") {
                    tv_style.setText(R.string.case_list_nav_style)
                } else {
                    tv_style.setText(showText)
                }
                mCaseFilterOptionBody.style = caseTypeEnum.id
            }
            2 -> {
                if (showText == "全部") {
                    tv_fx.setText(R.string.case_list_nav_fx)
                } else {
                    tv_fx.setText(showText)
                }
                mCaseFilterOptionBody.houseType = caseTypeEnum.id
            }
            3 -> {
                if (showText == "全部") {
                    tv_area.setText("面积")
                } else {
                    tv_area.setText(showText)
                }
                mCaseFilterOptionBody.areaType = caseTypeEnum.id
            }
            4 -> {
                if (showText == "全部") {
                    tv_city.setText("城市")
                } else {
                    tv_city.setText(showText)
                }
                mCaseFilterOptionBody.cityType = caseTypeEnum.id
            }
        }

    }

    companion object {

        /**
         * @param forSearchPage 用于搜索页面
         */
        fun newInstance(fromSearch: Boolean): CaseListFragment {
            val args = Bundle()
            args.putBoolean(Constant.KEY_CASE_LIST_SOURCE, fromSearch)
            val fragment = CaseListFragment()
            fragment.arguments = args
            return fragment
        }
    }


    /**
     * 显示titleLayout
     */
    fun showTitleLayout(show: Boolean) {
        if (show) {
            title_layout.visibility = View.VISIBLE
        } else {
            title_layout.visibility = View.GONE
        }
    }

    /**
     * 搜索
     */
    fun search(keyWords: String) {
        mCaseFilterOptionBody.reset()
        mCaseFilterOptionBody.refresh = false
        mCaseFilterOptionBody.queryKey = keyWords
        pageNum = Config.page
        presenter.loadCaseList(pageNum, mCaseFilterOptionBody)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.case_list_frag, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_empty.text = "抱歉，没有匹配的作品\n请选择其他类型或搜索"
        iv_empty.setImageResource(R.mipmap.work_search_no_result_ico)
        btn_classify.setOnClickListener(this@CaseListFragment)
        btn_style.setOnClickListener(this@CaseListFragment)
        btn_fx.setOnClickListener(this@CaseListFragment)
        btn_area.setOnClickListener(this@CaseListFragment)
        btn_city.setOnClickListener(this@CaseListFragment)
        btnScrollTop.setOnClickListener { rv_case.smoothScrollToPosition(0) }
        iv_search.setOnClickListener { SearchActivity.start(activity) }
        rv_case.run {
            layoutManager = LinearLayoutManager(context)
            setLoadingListener(this@CaseListFragment)
            setAdapter(caseListAdapter)
            addFootView(LoadingMoreFooter(context, getString(R.string.no_more_case_load)))
            addOnScrollListener(caseListScrollListener)
            loadMoreComplete()
        }
        if (fromSearch) {
            showTitleLayout(false)
            showEmpty(true)
        } else {
            showTitleLayout(true)
            presenter.loadCaseList(pageNum, mCaseFilterOptionBody)
        }
    }

}