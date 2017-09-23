package com.yingwumeijia.baseywmj.function.casedetails.realscene

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.baseywmj.function.WebViewManager
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import kotlinx.android.synthetic.main.realscene_720.*
import kotlinx.android.synthetic.main.realscene_base_info.*
import kotlinx.android.synthetic.main.realscene_extra.*
import kotlinx.android.synthetic.main.realscene_frag.*
import kotlinx.android.synthetic.main.realscene_photo.*
import kotlinx.android.synthetic.main.realscene_safeguard.*
import kotlinx.android.synthetic.main.realscene_video.*

/**
 * Created by jamisonline on 2017/6/26.
 */
class RealSceneFragment : JBaseFragment(), RealSceneContract.View {

    override fun initSafeguardLayout(show: Boolean) {
        safeguard_layout.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun initBaseInfoExtra(priceList: List<RealSceneBean.DesignPriceRangeDto>?, viewCount: Int, collectCount: Int, designPriceRangeDtos: RealSceneBean.DesignPriceRangeDto?) {
        realscene_extra_layout.visibility = View.VISIBLE



        if (designPriceRangeDtos != null) {
            softPrice_layout.visibility = View.VISIBLE
            tv_casePriceType.text = if (designPriceRangeDtos.type == 1) "硬装（设计费）" else "软装（设计费）"
            tv_extra_softPrice.text = "￥ " + designPriceRangeDtos.priceStart + "-" + designPriceRangeDtos.priceEnd + "元"
        }


        if (ListUtil.isEmpty(priceList)) {
            lv_serviceStandard.visibility = View.GONE
        } else {
            lv_serviceStandard.run {
                visibility = View.VISIBLE
                adapter = presenter.createServiceStandardAdapter(priceList!!)
            }
        }

        tv_extra_viewCount.text = viewCount.toString() + "次"
        tv_extra_collectCount.text = collectCount.toString() + "次"
    }


    val presenter by lazy { RealScenePresenter(activity, caseId, this) }

    override fun clippingLayoutOf720() {
        ScreenUtils.setLayoutScaleByWidth(layout_of_720, ScreenUtils.screenWidth, 1f / 1f)
    }

    override fun clippingLayoutOfVideo() {
        ScreenUtils.setLayoutScaleByWidth(iv_video_preview, ScreenUtils.screenWidth, 2f / 3f)
    }

    override fun init720Layout(has720: Boolean, caseCoverUrl: String, urlOf720: String?) {
        clippingLayoutOf720()
        if (has720) btn_play_720.visibility = View.VISIBLE else btn_play_720.visibility = View.GONE
        JImageLolder.load720(context, iv_preview_of_720, caseCoverUrl)
        if (!TextUtils.isEmpty(urlOf720))
            layout_of_720.setOnClickListener { WebViewManager.startHasTitle(activity, urlOf720!!, null) }
    }

    override fun initBaseInfoLayout(baseInfo: String, caseName: String, caseStory: String) {
        tv_base_info.text = baseInfo
        tv_caseName.text = caseName
        tv_all_text.text = caseStory
        tv_all_text.post {
            var lineCount: Int = tv_all_text.lineCount
            if (lineCount > 3) {
                tv_all_text.visibility = View.GONE
                tv_caseDescribe.run {
                    visibility = View.VISIBLE
                    text = caseStory
                }
            } else {
                tv_all_text.visibility = View.VISIBLE
                tv_caseDescribe.visibility = View.GONE
            }

        }
    }

    override fun initRealPhotoLayout(scenes: List<RealSceneBean.ScenesBean>) {
        realPhoto_layout.visibility = View.VISIBLE
        rv_photo.run {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.realPhotoAdapter
        }
    }

    override fun initVideoLayout(videoBean: RealSceneBean.DesignVideoBean) {
        clippingLayoutOfVideo()
        video_layout.visibility = View.VISIBLE
        video_layout.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(videoBean.url), "video/mp4")
            ActivityCompat.startActivity(activity, intent, Bundle.EMPTY)
        }
        JImageLolder.load720(context, iv_video_preview, videoBean.url + "?vframe/jpg/offset/" + videoBean.second + "/w/750/h/500")
    }

    override fun initCaseListLayout(caseList: List<CaseBean>) {
        tv_other_case.visibility = View.VISIBLE

        rv_content.run {
            visibility = View.VISIBLE
            layoutManager = GridLayoutManager(context, 2)
            adapter = presenter.caseListAdapter
        }
    }

    val caseId by lazy { arguments.getInt(Constant.KEY_CASE_DETAIL_ID, Constant.DEFAULT_INT_VALUE) }

    companion object {

        fun newInstance(caseId: Int): RealSceneFragment {
            val args = Bundle()
            args.putInt(Constant.KEY_CASE_DETAIL_ID, caseId)
            val fragment = RealSceneFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.realscene_frag, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }
}