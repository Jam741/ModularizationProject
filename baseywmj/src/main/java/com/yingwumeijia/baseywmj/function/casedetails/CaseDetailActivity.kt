package com.yingwumeijia.baseywmj.function.casedetails

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.flyco.tablayout.utils.UnreadMsgUtils
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.adapter.TabWithPagerAdapter
import com.yingwumeijia.baseywmj.function.casedetails.realscene.RealSceneFragment
import com.yingwumeijia.baseywmj.function.casedetails.team.CaseTeamFragment
import com.yingwumeijia.baseywmj.function.casedetails.team.MaterialFragment
import com.yingwumeijia.baseywmj.function.comment.CommentActivity
import com.yingwumeijia.commonlibrary.utils.TextViewUtils
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import kotlinx.android.synthetic.main.case_details_act.*
import kotlinx.android.synthetic.main.case_details_bottom.*
import kotlinx.android.synthetic.main.common_sliding_tab_layout.*

/**
 * Created by Jam on 2017/6/25.
 * 案例详情页面
 */
class CaseDetailActivity : JBaseActivity(), CaseDetailContract.View, View.OnClickListener {


    var isCollect: Boolean = false

    val caseId: Int by lazy { intent.getIntExtra(Constant.KEY_CASE_DETAIL_ID, Constant.DEFAULT_INT_VALUE) }

    val forGoBack by lazy { intent.getBooleanExtra(Constant.KEY_CASE_DETAIL_GO_BACK, false) }

    val presenter by lazy { CaseDetailPresenter(context, caseId, this) }

    val mTitles = arrayOf("现场实景", "原创团队", "项目资料")

    val mFragments by lazy {
        arrayListOf(RealSceneFragment.newInstance(caseId), CaseTeamFragment.newInstance(caseId), MaterialFragment.newInstance(caseId))
    }

    val guidanceFragment by lazy { GuidanceFragment() }

    val pageAdapter by lazy { TabWithPagerAdapter(supportFragmentManager, mTitles, mFragments as List<Fragment>) }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.topLeft -> close()
            R.id.btnShare -> presenter.share()
            R.id.btnCollect -> if (isCollect) presenter.cancelCollect() else presenter.collect()
            R.id.btnConnectTeam -> if (forGoBack) close() else presenter.connectTeam()
            R.id.btnComment -> CommentActivity.start(context, caseId)
            R.id.topRight -> if (isCollect) presenter.cancelCollect() else presenter.collect()
        }
    }


    override fun setCollectStatus(isCollected: Boolean) {
        this@CaseDetailActivity.isCollect = isCollected
        if (isAppC) {
            btnCollect.run {
                isEnabled = true
                setImageResource(if (isCollected) R.mipmap.bottom_collect_light_ico else R.mipmap.bottom_collect_ico)
            }
        } else {
            topRight.isEnabled = true
            if (isCollected) TextViewUtils.setDrawableToLeft(context, topRight, R.mipmap.bottom_collect_light_ico)
            else TextViewUtils.setDrawableToLeft(context, topRight, R.mipmap.bottom_collect_ico)
        }
    }


    override fun showDesignerPortrait(portraitUrl: String) {
//        JImageLolder.loadPortrait100(context, iv_designerPortrait, portraitUrl)
    }

    override fun CommentCount(count: Int) {
        UnreadMsgUtils.show(tv_messageNum, count, false)
    }


    companion object {
        fun start(context: Context, id: Int, forGoBack: Boolean) {
            val starter = Intent(context, CaseDetailActivity::class.java)
            starter.putExtra(Constant.KEY_CASE_DETAIL_ID, id)
            starter.putExtra(Constant.KEY_CASE_DETAIL_GO_BACK, forGoBack)
            context.startActivity(starter)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter.onNewIntent(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.case_details_act)
        topTitle.text = "作品详情"
        presenter.start()
        if (isAppC) {
            TextViewUtils.setDrawableToLeft(context, topRight, R.mipmap.bottom_collect_ico)
            bottom_layout.visibility = View.VISIBLE
            topRight.visibility = View.GONE
            topRight_second.visibility = View.GONE
        } else {
            topRight.isEnabled = false
            bottom_layout.visibility = View.GONE
            topRight.setOnClickListener(this)
            topRight_second.visibility = View.VISIBLE
            topRight_second.setOnClickListener { presenter.share() }
        }

        vp_content.run { adapter = pageAdapter }
        tl_title.setViewPager(vp_content)

        topLeft.setOnClickListener(this)
        btnShare.setOnClickListener(this)
        btnCollect.setOnClickListener(this)
        btnConnectTeam.setOnClickListener(this)
        btnComment.setOnClickListener(this)

        if (UserManager.guidanceCaseDetailsNeedShow(context))
            showGuidanceFragment()
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    fun removeGuidanceFragment() {
        supportFragmentManager.beginTransaction().remove(guidanceFragment).commit()

    }


    fun showGuidanceFragment() {
        supportFragmentManager.beginTransaction().add(R.id.guidance_fragment, guidanceFragment).commit()
    }
}