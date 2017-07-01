package com.yingwumeijia.baseywmj.function

import android.app.Activity
import android.content.Context
import com.yingwumeijia.baseywmj.function.casedetails.CaseDetailActivity

/**
 * Created by jamisonline on 2017/6/27.
 */
object StartActivityManager {

    /**
     * 打开案例详情页面
     */
    fun startCaseDetailActivity(activity: Activity, caseId: Int) {
        CaseDetailActivity.start(activity, caseId)
    }

    /**
     * 跳转到用户服务页面

     * @param context
     */
    fun startAgreementActivity(context: Context) {
        WebViewManager.startHasTitle(context, "https://mobile.yingwumeijia.com/template/userAgreement.html", "用户服务协议")
    }
}