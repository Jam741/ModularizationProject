package com.yingwumeijia.baseywmj.function.introduction.employee

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.rx.android.jamspeedlibrary.utils.view.MyListView
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.WebViewManager
import com.yingwumeijia.baseywmj.function.introduction.serviceStandard.ServiceStandardActivity
import com.yingwumeijia.baseywmj.function.previewPic.PreViewActivity
import com.yingwumeijia.baseywmj.function.previewPic.PreviewModel
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.TextViewUtils
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import com.yingwumeijia.commonlibrary.widget.recycler.XRecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.case_list.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/6/28.
 */
class EmployeeActivity : JBaseActivity(), EmployeeContract.View, View.OnClickListener, XRecyclerView.LoadingListener {


    val employeeId by lazy { intent.getIntExtra(Constant.KEY_EMPLOYEE_ID, Constant.DEFAULT_INT_VALUE) }
    val caseId by lazy { intent.getIntExtra(Constant.KEY_CASE_DETAIL_ID, Constant.DEFAULT_INT_VALUE) }
    val presenter by lazy { EmployeePresenter(context, this, employeeId, caseId) }


    val photosPic by lazy { ArrayList<String>() }

    var isCollected = false

    var pageNum = Config.page

    val headerView by lazy { LayoutInflater.from(context).inflate(R.layout.employee_introduction_head, null) }

    val headerViewHolder: HeaderViewHolder by lazy { HeaderViewHolder(context, headerView) }


    companion object {
        fun start(context: Context, employeeId: Int, caseId: Int) {
            val starter = Intent(context, EmployeeActivity::class.java)
            starter.putExtra(Constant.KEY_EMPLOYEE_ID, employeeId)
            starter.putExtra(Constant.KEY_CASE_DETAIL_ID, caseId)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.employee_introduction_frag)
        topTitle.text = "个人简介"
        TextViewUtils.setDrawableToLeft(context, topRight, R.mipmap.bottom_collect_ico)
        topLeft.setOnClickListener(this)
        topRight.setOnClickListener(this)
        presenter.start()
        presenter.loadOtherCase(pageNum)

        rv_case.run {
            addHeaderView(headerView)
            layoutManager = GridLayoutManager(context, 2)
            setPullRefreshEnabled(false)
            setLoadingListener(this@EmployeeActivity)
            adapter = presenter.otherCaseAdapter
        }

    }

    override fun onRefresh() {}

    override fun onLoadMore() {
        pageNum++
        presenter.loadOtherCase(pageNum)
    }

    override fun setEmployeePortrait(portraitUrl: String) {
        JImageLolder.loadPortrait256(context, headerViewHolder.ivPortrait, portraitUrl)
    }

    override fun showEmployeeName(name: String) {
        headerViewHolder.tvName.text = name
    }

    override fun showEmployeeJob(job: String) {
        headerViewHolder.tv_job.text = job
    }

    override fun showCompanyName(company: String) {
        headerViewHolder.tv_companyName.text = company
    }

    override fun showDescribe(describe: String) {
        headerViewHolder.tvDescribe.text = describe
    }

    override fun showEmployeePhoto(photo: ArrayList<String>) {
        if (!ListUtil.isEmpty(photo))
            JImageLolder.load720(context, headerViewHolder.iv_personShow, photo[0])
        photosPic.addAll(photo)
    }

    override fun initEmployeePhotos(phontos: List<String>) {
        headerViewHolder.tv_personShow.text = "个人展示（" + phontos.size + "）"
    }

    override fun showArticles(articlesBeanList: List<EmployeeIntroductionBean.ArticlesBean>) {
        headerViewHolder.lv_articles.run {
            visibility = View.GONE
            adapter = presenter.articleAdapter
            setOnItemClickListener { _, _, position, _ -> WebViewManager.startHasTitle(context, presenter.articleAdapter.getItem(position).url, null) }
        }

    }

    override fun initServiceStandard(serviceStandardDtoBean: EmployeeIntroductionBean.ServiceStandardDtoBean) {
        headerViewHolder.layoutServiceStandard.visibility = View.VISIBLE
        headerViewHolder.lvServiceStandard.run {
            adapter = presenter.serviceStandardAdapter

            setOnItemClickListener { parent, view, position, id ->
                var bean = serviceStandardDtoBean.serviceStandards[position]
                ServiceStandardActivity.start(context, bean.serviceName, bean.serviceType, employeeId, 2)
            }
        }

        if (!ListUtil.isEmpty(serviceStandardDtoBean.serviceStandards))
            presenter.serviceStandardAdapter.refresh(serviceStandardDtoBean.serviceStandards as java.util.ArrayList<EmployeeIntroductionBean.ServiceStandardDtoBean.ServiceStandardsBean>)
    }

    override fun responseOtherCase(list: List<CaseBean>) {
        presenter.otherCaseAdapter.addRange(list as ArrayList<CaseBean>)
    }

    override fun loadOtherCaseComplete(pageNum: Int, empty: Boolean) {
        rv_case.setIsnomore(empty)
        rv_case.loadMoreComplete()
    }

    override fun showOtherCaseCount(count: Int) {
        headerViewHolder.tvOtherCase.text = "全部作品 ($count)"
        if (count == 0) {
            rv_case.run {
                setIsnomore(false)
                setLoadingMoreEnabled(false)
            }
        }
    }

    override fun showIsCollected(isCollected: Boolean) {
        topRight.isEnabled = true
        this.isCollected = isCollected
        TextViewUtils.setDrawableToLeft(context, topRight, if (isCollected) R.mipmap.bottom_collect_light_ico else R.mipmap.bottom_collect_ico)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.topLeft -> close()
            R.id.topRight -> {
                if (!UserManager.precedent(context)) return
                if (isCollected) presenter.cancelCollect() else presenter.collect()
            }
        }
    }

    class HeaderViewHolder(var activity: Activity, var rootView: View) : View.OnClickListener {
        override fun onClick(v: View?) {
            when (v!!.id) {
                R.id.tv_personShow -> PreViewActivity.start(activity, PreviewModel((activity as EmployeeActivity).photosPic, null, null), 0)
                R.id.iv_personShow -> PreViewActivity.start(activity, PreviewModel((activity as EmployeeActivity).photosPic, null, null), 0)
            }
        }

        val ivPortrait: ImageView by lazy { rootView.findViewById(R.id.iv_portrait) as ImageView }
        val tvName: TextView by lazy { rootView.findViewById(R.id.tv_name) as TextView }
        val tv_job: TextView by lazy { rootView.findViewById(R.id.tv_job) as TextView }
        val tv_companyName: TextView by lazy { rootView.findViewById(R.id.tv_companyName) as TextView }
        val tvDescribe: ExpandableTextView by lazy { rootView.findViewById(R.id.tv_describe) as ExpandableTextView }
        val tv_personShow: TextView by lazy { rootView.findViewById(R.id.tv_personShow) as TextView }
        val iv_personShow: ImageView  by lazy { rootView.findViewById(R.id.iv_personShow) as ImageView }
        val lv_articles: MyListView  by lazy { rootView.findViewById(R.id.lv_articles) as MyListView }
        val tvOtherCase: TextView  by lazy { rootView.findViewById(R.id.tv_other_case) as TextView }
        val lvServiceStandard: MyListView by lazy { rootView.findViewById(R.id.lv_serviceStandard) as MyListView }
        val layoutServiceStandard: LinearLayout by lazy { rootView.findViewById(R.id.layout_service_standard) as LinearLayout }

        init {
            tv_personShow.setOnClickListener(this)
            iv_personShow.setOnClickListener(this)
        }
    }

}