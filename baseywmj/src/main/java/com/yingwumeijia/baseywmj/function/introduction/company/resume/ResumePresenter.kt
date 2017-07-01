package com.yingwumeijia.baseywmj.function.introduction.company.resume

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.text.TextUtils
import android.view.View
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.function.VideoPlayManager
import com.yingwumeijia.baseywmj.function.WebViewManager
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.NetUtils
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import rx.Observable

/**
 * Created by jamisonline on 2017/6/28.
 */
class ResumePresenter(var activity: Activity, var companyId: Int, var view: ResumeContract.View) : ResumeContract.Presenter {


    var resumeBean: CompanyResumeBean? = null

    var isAppC = AppTypeManager.isAppC()

    val picListAdapter by lazy { createPicListAdapter() }

    private fun createPicListAdapter(): CommonRecyclerAdapter<CompanyResumeBean.PicsBean> {
        return object : CommonRecyclerAdapter<CompanyResumeBean.PicsBean>(activity, null, resumeBean!!.pics as ArrayList<CompanyResumeBean.PicsBean>, R.layout.item_company_resume_photo) {
            override fun convert(holder: RecyclerViewHolder, t: CompanyResumeBean.PicsBean, position: Int) {

                var index: String
                if (position < 9) {
                    index = "0" + (position + 1)
                } else {
                    index = (position + 1).toString()
                }
                holder.run {
                    setTextWith(R.id.tv_floor, index)
                    setTextWith(R.id.tv_title1, t.explain)
                    setImageUrl480(activity!!, R.id.iv_image1, t.pics[0])
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            TODO("图片预览")
                        }

                    })
                }
            }

        }
    }

    override fun start() {
        var ob: Observable<CompanyResumeBean>
        if (isAppC) ob = Api.service.getCompanyResumeData_C(companyId)
        else ob = Api.service.getCompanyResumeData_E(companyId)

        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<CompanyResumeBean>(activity) {
            override fun _onNext(t: CompanyResumeBean?) {
                if (t == null) return Unit
                resumeBean = t
                view.show720Preview(t.cover)
                if (t.video != null && !TextUtils.isEmpty(t.video.url)) {
                    view.showVide0Preview(t.video.url)
                }
                if (!ListUtil.isEmpty(t.pics)) {
                    view.showPicList(t.pics)
                }
            }

        })
    }

    override fun start720() {
        if (resumeBean == null || TextUtils.isEmpty(resumeBean!!.pathOf720)) return
        if (!NetUtils.isWifi(activity))
            AlertDialog.Builder(activity)
                    .setTitle(R.string.dialog_title)
                    .setMessage("当前非WIFI网络,继续查看将耗费手机流量")
                    .setNegativeButton(R.string.btn_cancel, DialogInterface.OnClickListener { _, _ -> })
                    .setPositiveButton(R.string.btn_confirm, DialogInterface.OnClickListener { _, _ ->
                        WebViewManager.start720(activity, resumeBean!!.pathOf720)
                    })
                    .show()
        else WebViewManager.start720(activity, resumeBean!!.pathOf720)
    }

    override fun playVideo() {
        if (resumeBean == null || resumeBean!!.video == null) return
        VideoPlayManager.play(activity, resumeBean!!.video.url)
    }
}