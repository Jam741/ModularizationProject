package com.yingwumeijia.baseywmj.function.casedetails.realscene

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import kotlinx.android.synthetic.main.realscene_720.*
import kotlinx.android.synthetic.main.realscene_base_info.*
import kotlinx.android.synthetic.main.realscene_frag.*
import kotlinx.android.synthetic.main.realscene_photo.*
import kotlinx.android.synthetic.main.realscene_video.*

/**
 * Created by jamisonline on 2017/6/26.
 */
class RealSceneFragment : JBaseFragment(), RealSceneContract.View {

    val presenter by lazy { RealScenePresenter(activity, caseId, this) }

    override fun clippingLayoutOf720() {
        ScreenUtils.setLayoutScaleByWidth(layout_of_720, ScreenUtils.screenWidth, 1f / 1f)
    }

    override fun clippingLayoutOfVideo() {
        ScreenUtils.setLayoutScaleByWidth(iv_video_preview, ScreenUtils.screenWidth, 2f / 3f)
    }

    override fun init720Layout(has720: Boolean, caseCoverUrl: String) {
        clippingLayoutOf720()
        if (has720) btn_play_720.visibility = View.VISIBLE else btn_play_720.visibility = View.GONE
        JImageLolder.load720(context, iv_preview_of_720, caseCoverUrl)
    }

    override fun initBaseInfoLayout(baseInfo: String, caseStory: String) {
        tv_base_info.text = baseInfo

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