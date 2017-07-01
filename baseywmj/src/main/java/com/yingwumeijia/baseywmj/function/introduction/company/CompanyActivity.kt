package com.yingwumeijia.baseywmj.function.introduction.company

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
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
import com.yingwumeijia.baseywmj.function.introduction.company.resume.ResumeActivity
import com.yingwumeijia.baseywmj.function.introduction.serviceStandard.ServiceStandardActivity
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.utils.TextViewUtils
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import com.yingwumeijia.commonlibrary.widget.recycler.XRecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.case_list.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/6/27.
 */
class CompanyActivity : JBaseActivity(), CompanyContract.View, XRecyclerView.LoadingListener {


    val companyId: Int by lazy { intent.getIntExtra(Constant.KEY_COMPANY_ID, Constant.DEFAULT_INT_VALUE) }

    val caseId: Int by lazy { intent.getIntExtra(Constant.KEY_CASE_DETAIL_ID, Constant.DEFAULT_INT_VALUE) }

    val presenter by lazy { CompanyPresenter(this, companyId, caseId, this) }

    val headerView by lazy { LayoutInflater.from(context).inflate(R.layout.company_introduction_head, null) }

    val headerViewHolder: HeaderViewHolder by lazy { HeaderViewHolder(context, headerView, companyId) }

    var isCollected: Boolean = false

    var pageNum = Config.page

    companion object {

        fun start(context: Context, companyId: Int, caseId: Int) {
            val starter = Intent(context, CompanyActivity::class.java)
            starter.putExtra(Constant.KEY_COMPANY_ID, companyId)
            starter.putExtra(Constant.KEY_CASE_DETAIL_ID, caseId)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.company_introduction_frag)

        presenter.start()
        presenter.loadOtherCaseList(pageNum)
        topTitle.text = "公司简介"
        rv_case.run {
            addHeaderView(headerView)
            layoutManager = GridLayoutManager(context, 2)
            setPullRefreshEnabled(false)
            setLoadingListener(this@CompanyActivity)
            adapter = presenter.otherCaseAdapter
        }

        TextViewUtils.setDrawableToLeft(context, topRight, R.mipmap.bottom_collect_ico)
        topLeft.setOnClickListener { close() }
        topRight.setOnClickListener {
            if (!UserManager.precedent(context)) return@setOnClickListener
            if (isCollected) presenter.cancelCollect() else presenter.collect()
        }
    }

    override fun onRefresh() {}

    override fun onLoadMore() {
        pageNum++
        presenter.loadOtherCaseList(pageNum)
    }

    override fun showCompanyPortrait(url: String) {
        JImageLolder.load256(context, headerViewHolder.ivPortrait, url)
    }

    override fun showCompanyName(name: String) {
        headerViewHolder.tvCompanyName.text = name
    }

    override fun showDescribe(describe: String) {
        headerViewHolder.tvDescribe.text = describe
    }

    override fun showCompanyPic(picUrl: String?) {
        headerViewHolder.ivCompanyShow.visibility = View.VISIBLE
        JImageLolder.load720(context, headerViewHolder.ivCompanyShow, picUrl)

    }

    override fun responseOtherCase(caseBeanList: List<CaseBean>) {
        presenter.otherCaseAdapter.addRange(caseBeanList as ArrayList<CaseBean>)
    }

    override fun onLoadOtherComplete(pageNum: Int, empty: Boolean) {
        rv_case.setIsnomore(empty)
        rv_case.loadMoreComplete()
    }

    override fun showOtherCaseCount(count: Int) {
        headerViewHolder.tvOtherCase.text = "公司作品 ($count)"
        if (count == 0) {
            rv_case.setIsnomore(false)
            rv_case.setLoadingMoreEnabled(false)
        }
    }

    override fun showHasResume(hasResume: Boolean) {
        headerViewHolder.tvCompanyShow.visibility = if (hasResume) View.VISIBLE else View.GONE
    }

    override fun showIsCollected(isCollected: Boolean) {
        if (topRight != null) {
            topRight.isEnabled = true
            this.isCollected = isCollected
            TextViewUtils.setDrawableToLeft(context, topRight, if (isCollected) R.mipmap.bottom_collect_light_ico else R.mipmap.bottom_collect_ico)
        }
    }

    override fun initServiceStandard(serviceStandardDtoBean: CompanyIntriductionBean.ServiceStandardDtoBean) {
        headerViewHolder.layoutServiceStandard.visibility = View.VISIBLE
        headerViewHolder.lvServiceStandard.run {
            adapter = presenter.serviceStandardAdapter

            setOnItemClickListener { parent, view, position, id ->
                var bean = serviceStandardDtoBean.serviceStandards[position]
                ServiceStandardActivity.start(context, bean.serviceName, bean.serviceType, companyId, 1)
            }
        }
    }


    class HeaderViewHolder(var activity: Activity, var rootView: View, var companyId: Int) : View.OnClickListener {
        override fun onClick(v: View?) {
            when (v!!.id) {
                R.id.tv_companyShow -> ResumeActivity.start(activity, companyId)
                R.id.iv_companyShow -> ResumeActivity.start(activity, companyId)
            }
        }

        val ivPortrait: CircleImageView by lazy { rootView.findViewById(R.id.iv_portrait) as CircleImageView }
        val tvCompanyName: TextView by lazy { rootView.findViewById(R.id.tv_companyName) as TextView }
        val tvDescribe: ExpandableTextView by lazy { rootView.findViewById(R.id.tv_describe) as ExpandableTextView }
        val tvCompanyShow: TextView by lazy { rootView.findViewById(R.id.tv_companyShow) as TextView }
        val ivCompanyShow: ImageView  by lazy { rootView.findViewById(R.id.iv_companyShow) as ImageView }
        val tvOtherCase: TextView  by lazy { rootView.findViewById(R.id.tv_other_case) as TextView }
        val lvServiceStandard: MyListView by lazy { rootView.findViewById(R.id.lv_serviceStandard) as MyListView }
        val layoutServiceStandard: LinearLayout by lazy { rootView.findViewById(R.id.layout_service_standard) as LinearLayout }

        init {
            tvCompanyShow.setOnClickListener(this)
            ivCompanyShow.setOnClickListener(this)
        }
    }

}