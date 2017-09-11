package com.yingwumeijia.baseywmj.function.casedetails.realscene

import android.app.Activity
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.WebViewManager
import com.yingwumeijia.baseywmj.function.introduction.employee.EmployeeIntroductionBean
import com.yingwumeijia.baseywmj.function.previewPic.PreViewActivity
import com.yingwumeijia.baseywmj.function.previewPic.PreviewModel
import com.yingwumeijia.baseywmj.utils.MoneyFormatUtils
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yingwumeijia.commonlibrary.utils.adapter.CommonAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.ViewHolder
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import com.yingwumeijia.commonlibrary.widget.SpannableTextView
import rx.Observable

/**
 * Created by jamisonline on 2017/6/26.
 */
class RealScenePresenter(var context: Activity, var caseId: Int, var view: RealSceneContract.View) : RealSceneContract.Presenter {

    var realSceneBena: RealSceneBean? = null

    val isAppc = AppTypeManager.isAppC()

    val realPhotoAdapter: CommonRecyclerAdapter<RealSceneBean.ScenesBean> by lazy {
        createRealPhotoAdapter()
    }

    val caseListAdapter: CommonRecyclerAdapter<CaseBean> by lazy {
        createListAdapter()
    }


    override fun play720() {
        if (realSceneBena != null && !TextUtils.isEmpty(realSceneBena!!.pathOf720)) {
            WebViewManager.startHasTitle(context, realSceneBena!!.pathOf720, null)
        }
    }

    val previewModelForRealPhoto by lazy { assembleRealPhotoPreviewModel() }

    private fun assembleRealPhotoPreviewModel(): PreviewModel {
        val pics by lazy { ArrayList<String>() }
        val titles by lazy { ArrayList<String>() }
        val contents by lazy { ArrayList<String>() }

        for (item in realSceneBena!!.scenes) {
            for (pic in item.pics) {
                pics.add(pic)
                titles.add(item.title)
                contents.add(item.explain)
            }
        }
        return PreviewModel(pics, titles, contents)
    }


    override fun start() {
        var ob: Observable<RealSceneBean>
        if (isAppc) ob = Api.service.getRealSceneBean_C(caseId)
        else ob = Api.service.getRealSceneBean_E(caseId)

        HttpUtil.getInstance().toSimpleSubscribe(ob, object : SimpleSubscriber<RealSceneBean>(context) {
            override fun _onNext(t: RealSceneBean?) {
                if (t != null) {
                    realSceneBena = t
                    view.init720Layout(!TextUtils.isEmpty(t.pathOf720), t.caseCover,t.pathOf720)
                    val baseInfo: String = t.cityName + " / " + t.decorateStyle + " / " + t.houseType + " / " + t.houseArea + "m² / " + t.decorateType + MoneyFormatUtils.fromatWan(t.totalCost) + "万 / " + t.buildingName
                    view.initBaseInfoLayout(baseInfo, t.caseName, t.caseStory)
                    if (!ListUtil.isEmpty(t.scenes))
                        view.initRealPhotoLayout(t.scenes)
                    if (t.designVideo != null)
                        view.initVideoLayout(t.designVideo)
                    if (!ListUtil.isEmpty(t.relativeCases))
                        view.initCaseListLayout(t.relativeCases)

                    view.initBaseInfoExtra(t.designPriceRangeDtos, t.viewCount, t.collectionCount)
                }
            }
        })
    }


    private fun createRealPhotoAdapter(): CommonRecyclerAdapter<RealSceneBean.ScenesBean> {
        return object : CommonRecyclerAdapter<RealSceneBean.ScenesBean>(context, null, realSceneBena!!.scenes as ArrayList<RealSceneBean.ScenesBean>, R.layout.item_realscene_photo) {
            override fun convert(holder: RecyclerViewHolder, t: RealSceneBean.ScenesBean, position: Int) {
                holder.run {
                    setTextWith(R.id.tv_floor, t.title)
                    setTextWith(R.id.tv_title1, t.explain)
                    setImageUrl480(context, R.id.iv_image1, t.pics[0])
                    setOnClickListener(R.id.iv_image1, View.OnClickListener {
                        PreViewActivity.start(context, previewModelForRealPhoto, position)
                    })
                }
            }

        }
    }


    private fun createListAdapter(): CommonRecyclerAdapter<CaseBean> {
        return object : CommonRecyclerAdapter<CaseBean>(context, null, realSceneBena!!.relativeCases as ArrayList<CaseBean>, R.layout.item_casedetail_case) {
            override fun convert(holder: RecyclerViewHolder, t: CaseBean, position: Int) {

                var imageView = holder.getViewWith(R.id.iv_case_img) as ImageView
                var sWidth = ScreenUtils.screenWidth / 2
                ScreenUtils.setLayoutScaleByWidth(imageView, sWidth, 1f / 1f)
                holder.run {
                    setImageUrl480(context, R.id.iv_case_img, t.caseCover)
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            StartActivityManager.startCaseDetailActivity(context, t.caseId)
                        }
                    })
                }
            }

        }
    }


    fun createServiceStandardAdapter(designerPrices: List<RealSceneBean.DesignPriceRangeDto>): CommonAdapter<RealSceneBean.DesignPriceRangeDto> {
        return object : CommonAdapter<RealSceneBean.DesignPriceRangeDto>(context, designerPrices as java.util.ArrayList<RealSceneBean.DesignPriceRangeDto>, R.layout.item_design_price) {
            override fun conver(helper: ViewHolder?, item: RealSceneBean.DesignPriceRangeDto?, position: Int) {
                helper!!.setText(R.id.tv_extra_price, "￥ " + item!!.priceStart + "-" + item.priceEnd + "元")
                        .setText(R.id.tv_severname, item.name)
            }
        }
    }


}