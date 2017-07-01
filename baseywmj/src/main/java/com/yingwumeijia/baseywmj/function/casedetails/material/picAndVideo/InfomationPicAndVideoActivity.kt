package com.yingwumeijia.baseywmj.function.casedetails.material.picAndVideo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.function.VideoPlayManager
import com.yingwumeijia.baseywmj.function.casedetails.material.CaseInfomationBean
import com.yingwumeijia.baseywmj.function.previewPic.PreViewActivity
import com.yingwumeijia.baseywmj.function.previewPic.PreviewModel
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import kotlinx.android.synthetic.main.infomation_media_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/6/30.
 */
class InfomationPicAndVideoActivity : JBaseActivity() {

    private val checksBean: CaseInfomationBean.ChecksBean by lazy { Gson().fromJson(intent.getStringExtra(Constant.KEY_COMPANY_ID), CaseInfomationBean.ChecksBean::class.java) }

    private val videoAdapter by lazy { createVideoAdapter() }

    private val picAdapter by lazy { createPicAdapter() }

    private val preViewModelForPic by lazy { assemblePicPreviewModel() }

    private fun assemblePicPreviewModel(): PreviewModel {
        val pics by lazy { ArrayList<String>() }
        val titles by lazy { ArrayList<String>() }
        val contents by lazy { ArrayList<String>() }
        for (item in checksBean.images) {
            for (pic in item.pics) {
                pics.add(pic)
                titles.add(item.title)
                contents.add(item.explain)
            }
        }
        return PreviewModel(pics, titles, contents)
    }

    private fun createPicAdapter(): CommonRecyclerAdapter<CaseInfomationBean.ChecksBean.ImagesBean> {
        return object : CommonRecyclerAdapter<CaseInfomationBean.ChecksBean.ImagesBean>(context, null, checksBean.images as ArrayList<CaseInfomationBean.ChecksBean.ImagesBean>, R.layout.item_infomation_check_photo) {
            override fun convert(holder: RecyclerViewHolder, t: CaseInfomationBean.ChecksBean.ImagesBean, position: Int) {
                holder.run {
                    setTextWith(R.id.tv_floor, t.title)
                    setTextWith(R.id.tv_title1, t.explain)
                    setImageUrl480(context, R.id.iv_image1, t.pics[0])
                    setOnClickListener(R.id.iv_image1, View.OnClickListener { PreViewActivity.start(context, preViewModelForPic, position) })
                }
            }
        }
    }

    private fun createVideoAdapter(): CommonRecyclerAdapter<CaseInfomationBean.ChecksBean.VideosBean> {
        return object : CommonRecyclerAdapter<CaseInfomationBean.ChecksBean.VideosBean>(context, null, checksBean.videos as ArrayList<CaseInfomationBean.ChecksBean.VideosBean>, R.layout.item_infomation_video) {
            override fun convert(holder: RecyclerViewHolder, t: CaseInfomationBean.ChecksBean.VideosBean, position: Int) {
                ScreenUtils.setLayoutScaleByWidth(holder.getViewWith(R.id.previewVideoLayout), ScreenUtils.screenWidth, 2f / 3f)
                holder.run {
                    setTextWith(R.id.tv_video_title, t.name)
                    setTextWith(R.id.tv_video_describe, t.description)
                    setImageUrlOriginal(context, R.id.iv_video_preview, t.url + "?vframe/jpg/offset/" + t.second + "/w/750/h/500")
                    setOnClickListener(R.id.iv_video_preview, View.OnClickListener { VideoPlayManager.play(context, t.url) })
                }
            }
        }
    }

    companion object {
        fun start(context: Context, checksBean: String) {
            val starter = Intent(context, InfomationPicAndVideoActivity::class.java)
            starter.putExtra(Constant.KEY_CHECK_BEAN, checksBean)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.infomation_media_act)

        topTitle.text = checksBean.phaseName
        tv_title.text = checksBean.phaseDescription
        topLeft.setOnClickListener { close() }
        rv_video.run {
            layoutManager = LinearLayoutManager(context)
            adapter = videoAdapter
        }
        rv_pic.run {
            layoutManager = LinearLayoutManager(context)
            adapter = picAdapter
        }
    }

}