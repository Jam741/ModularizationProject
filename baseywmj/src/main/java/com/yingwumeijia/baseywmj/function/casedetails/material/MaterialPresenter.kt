package com.yingwumeijia.baseywmj.function.casedetails.material

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.google.gson.Gson
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.function.casedetails.material.picAndVideo.InfomationPicAndVideoActivity
import com.yingwumeijia.baseywmj.function.previewPic.PreViewActivity
import com.yingwumeijia.baseywmj.function.previewPic.PreviewModel
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import rx.Observable

/**
 * Created by jamisonline on 2017/6/27.
 */
class MaterialPresenter(var fragment: Fragment, var caseId: Int, var view: MaterialContract.View) : MaterialContract.Presenter {

    var isAppc = AppTypeManager.isAppC()

    private val checkIcons = arrayOf(R.mipmap.setp_one_ic, R.mipmap.setp_two_ic, R.mipmap.setp_three_ic, R.mipmap.setp_four_ic, R.mipmap.setp_five_ic, R.mipmap.setp_six_ic, R.mipmap.setp_seven_ic, R.mipmap.setp_eight_ic, R.mipmap.setp_nine_ic)

    private val previewModelForHouseDesign by lazy { assembleHouseDesignPreviewModel() }

    private val previewModelForDesignDisplay by lazy { assembleDesignDisplayPreviewModel() }

    private fun assembleDesignDisplayPreviewModel(): PreviewModel {
        val pics by lazy { ArrayList<String>() }
        val titles by lazy { ArrayList<String>() }
        val contents by lazy { ArrayList<String>() }

        for (item in infomationBean!!.designMaterials.displayImages) {
            for (pic in item.pics) {
                pics.add(pic)
                titles.add(item.title)
                contents.add(item.explain)
            }
        }
        return PreviewModel(pics, titles, contents)
    }

    private fun assembleHouseDesignPreviewModel(): PreviewModel {
        val pics by lazy { ArrayList<String>() }
        val titles by lazy { ArrayList<String>() }
        val contents by lazy { ArrayList<String>() }
        for (item in infomationBean!!.designMaterials.houseImages) {
            pics.add(item.originHouseImage.pics[0])
            titles.add(item.originHouseImage.title)
            contents.add(item.originHouseImage.explain)
            pics.add(item.designHouseImage.pics[0])
            titles.add(item.designHouseImage.title)
            contents.add(item.designHouseImage.explain)
        }
        return PreviewModel(pics, titles, contents)
    }

    var infomationBean: CaseInfomationBean? = null

    val checksBeanList by lazy { ArrayList<BigChecksBean>() }

    val houseDesignAdapter by lazy {
        createHouseDesignAdapter()
    }
    val designDisplayAdapter by lazy {
        createDesignDisplayAdapter()
    }

    val checksAdapter by lazy {
        createChecksAdapter()
    }

    private fun createChecksAdapter(): CommonRecyclerAdapter<BigChecksBean> {
        return object : CommonRecyclerAdapter<BigChecksBean>(null, fragment, checksBeanList, R.layout.item_infomation_checks) {
            override fun convert(holder: RecyclerViewHolder, t: BigChecksBean, position: Int) {
                holder.run {
                    setTextWith(R.id.tv_checksTitle, t.getChecksType())
                    setVisible(R.id.layout_hint, position == 0)
                }

                var recyclerView = holder.getViewWith(R.id.rv_checks_progress) as RecyclerView
                recyclerView.run {
                    layoutManager = LinearLayoutManager(context)
                    adapter = createChecksProgressAdapter(t.checksBeanList)
                }
            }

        }
    }


    private fun createChecksProgressAdapter(checksBeanList: List<CaseInfomationBean.ChecksBean>): CommonRecyclerAdapter<CaseInfomationBean.ChecksBean> {
        return object : CommonRecyclerAdapter<CaseInfomationBean.ChecksBean>(null, fragment, checksBeanList as ArrayList<CaseInfomationBean.ChecksBean>, R.layout.item_check_progress_bottom) {
            override fun convert(holder: RecyclerViewHolder, checksBean: CaseInfomationBean.ChecksBean, position: Int) {


                val videoNum = if (ListUtil.isEmpty(checksBean.videos)) "" else "视频 " + checksBean.videos.size
                var picNum = ""
                if (TextUtils.isEmpty(videoNum))
                    picNum = if (ListUtil.isEmpty(checksBean.images)) "" else "图片" + checksBean.images.size
                else {
                    picNum = if (ListUtil.isEmpty(checksBean.images)) "" else "  ∙  图片" + checksBean.images.size
                }

                holder.run {
                    setVisible(R.id.line, position != checksBeanList.size - 1)
                    setImageResource(R.id.iv_position, checkIcons[position])
                    setTextWith(R.id.tv_progressPicNum, picNum)
                    setTextWith(R.id.tv_progressVideoNum, videoNum)
                    setTextWith(R.id.tv_progressTitle, checksBean.phaseName)
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            InfomationPicAndVideoActivity.start(mContext!!, Gson().toJson(checksBean))
                        }
                    })

                }


            }
        }
    }

    private fun createHouseDesignAdapter(): CommonRecyclerAdapter<CaseInfomationBean.DesignMaterialsBean.HouseImagesBean> {
        return object : CommonRecyclerAdapter<CaseInfomationBean.DesignMaterialsBean.HouseImagesBean>(null, fragment, infomationBean!!.designMaterials.houseImages as ArrayList<CaseInfomationBean.DesignMaterialsBean.HouseImagesBean>, R.layout.item_realscene_plane) {
            override fun convert(holder: RecyclerViewHolder, t: CaseInfomationBean.DesignMaterialsBean.HouseImagesBean, position: Int) {

                val pics1 = t.originHouseImage.pics
                val pics2 = t.designHouseImage.pics
                holder.run {
                    setTextWith(R.id.tv_floor1, t.originHouseImage.title)
                    setTextWith(R.id.tv_floor2, t.designHouseImage.title)
                    setTextWith(R.id.tv_describe, t.remakeIllustration)
                    setImageUrl480(fragment!!, R.id.iv_image1, pics1[0])
                    setImageUrl480(fragment!!, R.id.iv_image2, pics2[0])
                    setOnClickListener(R.id.iv_image1, View.OnClickListener {
                        PreViewActivity.start(mContext!!, previewModelForHouseDesign, position * 2)
                    })
                    setOnClickListener(R.id.iv_image2, View.OnClickListener {
                        PreViewActivity.start(mContext!!, previewModelForHouseDesign, position * 2 + 1)
                    })
                }
            }

        }
    }

    private fun createDesignDisplayAdapter(): CommonRecyclerAdapter<CaseInfomationBean.DesignMaterialsBean.DisplayImagesBean> {
        return object : CommonRecyclerAdapter<CaseInfomationBean.DesignMaterialsBean.DisplayImagesBean>(null, fragment, infomationBean!!.designMaterials.displayImages as ArrayList<CaseInfomationBean.DesignMaterialsBean.DisplayImagesBean>, R.layout.item_realscene_plane) {
            override fun convert(holder: RecyclerViewHolder, t: CaseInfomationBean.DesignMaterialsBean.DisplayImagesBean, position: Int) {

                val pics = t.pics
                holder.run {
                    setTextWith(R.id.tv_title1, t.explain)
                    setImageUrl480(fragment!!, R.id.iv_image1, pics[0])
                    setOnClickListener(R.id.iv_image1, View.OnClickListener {
                        PreViewActivity.start(mContext!!, previewModelForDesignDisplay, position)
                    })
                }
            }

        }
    }

    override fun start() {
        var ob: Observable<CaseInfomationBean>
        if (isAppc) ob = Api.service.getCaseDetailInfomation_C(caseId)
        else ob = Api.service.getCaseDetailInfomation_E(caseId)

        HttpUtil.getInstance().toNolifeSubscribe(ob, object : SimpleSubscriber<CaseInfomationBean>(fragment.context) {
            override fun _onNext(t: CaseInfomationBean?) {
                if (t == null) return
                infomationBean = t
                view.initCost(t.costs, t.totalPrice, t.caseCover, t.houseArea)

                val listCostBrand by lazy { ArrayList<CaseInfomationBean.CostBrandsBean>() }
                if (!ListUtil.isEmpty(t.costs))
                    for (costBean in t.costs) {
                        val costBrandsBean = CaseInfomationBean.CostBrandsBean()
                        costBrandsBean.costInfo = costBean
                        listCostBrand.add(costBrandsBean)
                    }
                if (!ListUtil.isEmpty(t.costBrands))
                    listCostBrand.addAll(t.costBrands)
                if (!ListUtil.isEmpty(listCostBrand))
                    view.initCostBrands(listCostBrand)

                if (t.designMaterials != null) {
                    if (!ListUtil.isEmpty(t.designMaterials.houseImages))
                        view.initDesignHouseImage(t.designMaterials.houseImages)
                    if (!ListUtil.isEmpty(t.designMaterials.displayImages))
                        view.initDesignDisplayImage(t.designMaterials.displayImages)
                }

                if (t.hardChecks != null) {
                    checksBeanList.add(BigChecksBean(t.hardChecks, "硬装施工"))
                }
                if (t.softChecks != null) {
                    checksBeanList.add(BigChecksBean(t.softChecks, "软装施工"))
                }
                if (!ListUtil.isEmpty(checksBeanList))
                    view.initChecks(checksBeanList)
            }

        })
    }

}