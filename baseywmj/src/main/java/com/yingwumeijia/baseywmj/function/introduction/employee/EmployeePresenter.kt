package com.yingwumeijia.baseywmj.function.introduction.employee

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
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yingwumeijia.commonlibrary.utils.adapter.CommonAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.ViewHolder
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import com.yingwumeijia.commonlibrary.widget.SpannableTextView
import rx.Observable
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jamisonline on 2017/6/29.
 */
class EmployeePresenter(var activity: Activity, var view: EmployeeContract.View, var employeeId: Int, var caseId: Int) : EmployeeContract.Presenter {

    val isAppC = AppTypeManager.isAppC()

    val articleAdapter by lazy { createArticleAdapter() }

    var employeeBean: EmployeeIntroductionBean? = null

    val serviceStandardAdapter by lazy { createServiceStandardAdapter() }

    val otherCaseAdapter by lazy {
        createOtherCaseAdapter()
    }

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


    private fun createArticleAdapter(): CommonAdapter<EmployeeIntroductionBean.ArticlesBean> {
        return object : CommonAdapter<EmployeeIntroductionBean.ArticlesBean>(activity, employeeBean!!.articles as ArrayList<EmployeeIntroductionBean.ArticlesBean>, R.layout.item_article_lsit) {
            override fun conver(helper: ViewHolder?, item: EmployeeIntroductionBean.ArticlesBean?, position: Int) {
                helper!!.run {
                    setText(R.id.tv_title, item!!.title)
                    setImageURL256(R.id.iv_title, item.thumb, activity)
                }
            }

        }
    }

    private fun createServiceStandardAdapter(): CommonAdapter<EmployeeIntroductionBean.ServiceStandardDtoBean.ServiceStandardsBean> {
        return object : CommonAdapter<EmployeeIntroductionBean.ServiceStandardDtoBean.ServiceStandardsBean>(activity,null, R.layout.item_servicestandard) {
            override fun conver(helper: ViewHolder?, item: EmployeeIntroductionBean.ServiceStandardDtoBean.ServiceStandardsBean?, position: Int) {

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
        var ob: Observable<EmployeeIntroductionBean>
        if (isAppC) ob = Api.service.getEmployeeIntroductionData_C(employeeId, caseId)
        else ob = Api.service.getEmployeeIntroductionData_E(employeeId, caseId)

        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<EmployeeIntroductionBean>(activity) {
            override fun _onNext(t: EmployeeIntroductionBean?) {
                if (t == null) return Unit
                employeeBean = t
                view.setEmployeePortrait(t.headImage)
                view.showEmployeeName(t.name)
                view.showEmployeeJob(t.title)
                view.showCompanyName(t.companyName)
                view.showDescribe(t.resume)
                view.showIsCollected(t.isCollected)

                if (!ListUtil.isEmpty(t.presentPics)) {
                    view.showEmployeePhoto(t.presentPics as ArrayList<String>)
                }

                if (!ListUtil.isEmpty(t.articles)) {
                    view.showArticles(t.articles)
                }

                view.initServiceStandard(t.serviceStandardDto)
                view.showOtherCaseCount(t.otherCasesCount)
            }
        })

    }

    override fun loadOtherCase(page: Int) {
        var ob: Observable<List<CaseBean>>
        if (isAppC) ob = Api.service.getEmployeeOtherCaseData_C(employeeId, page, Config.size)
        else ob = Api.service.getEmployeeOtherCaseData_E(employeeId, page, Config.size)

        HttpUtil.getInstance().toNolifeSubscribe(ob, object : SimpleSubscriber<List<CaseBean>>(activity) {
            override fun _onNext(t: List<CaseBean>?) {
                view.loadOtherCaseComplete(page, t == null)
                if (t != null)
                    view.responseOtherCase(t)
            }

        })
    }

    override fun collect() {
        val ob = Api.service.collect(employeeId, CollectType.EMPLOYEE)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(activity) {
            override fun _onNext(t: String?) {
                Toast.makeText(activity, "收藏业者成功", Toast.LENGTH_SHORT).show()
                view.showIsCollected(true)
            }

        })
    }

    override fun cancelCollect() {
        val ob = Api.service.cancelCollect(employeeId, CollectType.EMPLOYEE)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(activity) {
            override fun _onNext(t: String?) {
                Toast.makeText(activity, "取消收藏成功", Toast.LENGTH_SHORT).show()
                view.showIsCollected(false)
            }

        })
    }

}