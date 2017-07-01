package com.yingwumeijia.baseywmj.function.introduction.company.resume

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import kotlinx.android.synthetic.main.company_resume_frag.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/6/28.
 *
 * 公司展示页面
 */
class ResumeActivity : JBaseActivity(), ResumeContract.View, View.OnClickListener {

    val companyId by lazy { intent.getIntExtra(Constant.KEY_COMPANY_ID, Constant.DEFAULT_INT_VALUE) }
    val presenter by lazy { ResumePresenter(context, companyId, this) }

    companion object {
        fun start(context: Context, companyId: Int) {
            val stater = Intent(context, ResumeActivity::class.java)
            stater.putExtra(Constant.KEY_COMPANY_ID, companyId)
            context.startActivity(stater)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.company_resume_frag)
        presenter.start()

        topTitle.text = "公司展示"
        topLeft.setOnClickListener(this)
        layout_of_720.setOnClickListener(this)
        btn_play_video.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.topLeft -> close()
            R.id.layout_of_720 -> presenter.start720()
            R.id.btn_play_video -> presenter.playVideo()
        }
    }

    override fun show720Preview(url: String) {
        layout_of_720.visibility = View.VISIBLE
        JImageLolder.load720(context, iv_preview_of_720, url)
    }

    override fun showVide0Preview(url: String) {
        video_layout.visibility = View.VISIBLE
        JImageLolder.load480(context, iv_video_preview, url)
    }

    override fun showPicList(picsBeen: List<CompanyResumeBean.PicsBean>) {
        lv_pics.run {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.picListAdapter
        }
    }
}