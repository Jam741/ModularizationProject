package com.yingwumeijia.baseywmj.function.introduction.company

import android.app.Activity
import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.collect.CollectType
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yingwumeijia.commonlibrary.utils.adapter.CommonAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.ViewHolder
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import com.yingwumeijia.commonlibrary.widget.SpannableTextView
import rx.Observable
import java.util.ArrayList

/**
 * Created by jamisonline on 2017/6/27.
 */
class CompanyPresenter(var activity: Activity, var companyId: Int, var caseId: Int?, var view: CompanyContract.View) : CompanyContract.Presenter {

    var isAppc = AppTypeManager.isAppC()

    val otherCaseAdapter by lazy { createOtherCaseAdapter() }

    var companyBean: CompanyIntriductionBean? = null

    val serviceStandardAdapter by lazy { createServiceStandardAdapter() }

    private fun createOtherCaseAdapter(): CommonRecyclerAdapter<CaseBean> {
        return object : CommonRecyclerAdapter<CaseBean>(activity, null, null, R.layout.item_casedetail_case) {
            override fun convert(holder: RecyclerViewHolder, t: CaseBean, position: Int) {
                val sWidth = ScreenUtils.screenWidth / 2
                ScreenUtils.setLayoutScaleByWidth(holder.getViewWith(R.id.iv_case_img), sWidth, 1f / 1f)
                holder.run {
                    setImageUrl480(activity!!, R.id.iv_case_img, t.caseCover)
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            StartActivityManager.startCaseDetailActivity(activity!!, t.caseId)
                        }

                    })
                }
            }

        }
    }

    private fun createServiceStandardAdapter(): CommonAdapter<CompanyIntriductionBean.ServiceStandardDtoBean.ServiceStandardsBean> {
        return object : CommonAdapter<CompanyIntriductionBean.ServiceStandardDtoBean.ServiceStandardsBean>(activity, companyBean!!.serviceStandardDto.serviceStandards as ArrayList<CompanyIntriductionBean.ServiceStandardDtoBean.ServiceStandardsBean>, R.layout.item_servicestandard) {
            override fun conver(helper: ViewHolder?, item: CompanyIntriductionBean.ServiceStandardDtoBean.ServiceStandardsBean?, position: Int) {


                var price = ""
                if (item!!.priceEnd !== 0) {
                    price = "¥ " + item!!.priceStart + "-" + item.priceEnd
                }

                var priceView: SpannableTextView? = null
                helper!!.run {
                    setText(R.id.tv_serviceName, item!!.serviceName)
                    priceView = getView(R.id.tv_servicePrice)
                }
                if (!TextUtils.isEmpty(price))
                    createSpannableTextViewForReplay(priceView!!, price, " / m²")

            }
        }
    }


    fun createSpannableTextViewForReplay(tv: SpannableTextView, title: String, content: String) {
        // clear pieces
        tv.reset()
        // Add the first piece
        tv.addPiece(SpannableTextView.Piece.Builder(title).textColor(activity.resources.getColor(R.color.color_6))
                .style(Typeface.BOLD).textSize(activity.resources.getDimension(R.dimen.font4_sp).toInt()).build())

        // Add the second piece
        tv.addPiece(SpannableTextView.Piece.Builder(content).textColor(activity.resources.getColor(R.color.color_1))
                .style(Typeface.NORMAL).textSize(activity.resources.getDimension(R.dimen.font4_sp).toInt()).build())

        // Display the final, styled text
        tv.display()
    }

    override fun start() {

        if (caseId == null) caseId = 0

        var ob: Observable<CompanyIntriductionBean>
        if (isAppc) ob = Api.service.getCompanyIntrductionData_C(companyId, caseId!!)
        else ob = Api.service.getCompanyIntrductionData_E(companyId, caseId!!)

        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<CompanyIntriductionBean>(activity) {
            override fun _onNext(t: CompanyIntriductionBean?) {
                if (t == null) return
                companyBean = t
                view.run {
                    showCompanyPortrait(t.logo)
                    showCompanyName(t.name)
                    showDescribe(t.resume)
                    if (!TextUtils.isEmpty(t.cover))
                        showCompanyPic(t.cover)
                    showOtherCaseCount(t.otherCasesCount)
                    showHasResume(t.isHasPresent)
                    showIsCollected(t.isIsCollected)
                    if (t.serviceStandardDto != null)
                        initServiceStandard(t.serviceStandardDto)
                }
            }

        })
    }

    override fun loadOtherCaseList(pageNum: Int) {
        var ob: Observable<List<CaseBean>>
        if (isAppc) ob = Api.service.getCompanyOtherCaseData_C(companyId, pageNum, Config.size)
        else ob = Api.service.getCompanyOtherCaseData_E(companyId, pageNum, Config.size)

        HttpUtil.getInstance().toNolifeSubscribe(ob, object : SimpleSubscriber<List<CaseBean>>(activity) {
            override fun _onNext(t: List<CaseBean>?) {
                view.onLoadOtherComplete(pageNum, t == null)
                if (t != null)
                    view.responseOtherCase(t)
            }

        })
    }

    override fun collect() {
        val ob = Api.service.collect(companyId, CollectType.COMPANY)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(activity) {
            override fun _onNext(t: String?) {
                Toast.makeText(activity, "收藏公司成功", Toast.LENGTH_SHORT).show()
                view.showIsCollected(true)
            }

        })
    }

    override fun cancelCollect() {
        val ob = Api.service.cancelCollect(companyId, CollectType.COMPANY)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(activity) {
            override fun _onNext(t: String?) {
                Toast.makeText(activity, "取消收藏成功", Toast.LENGTH_SHORT).show()
                view.showIsCollected(false)
            }

        })
    }

}