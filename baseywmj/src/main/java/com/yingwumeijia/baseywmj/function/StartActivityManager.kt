package com.yingwumeijia.baseywmj.function

import android.app.Activity
import android.content.Context
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.function.casedetails.CaseDetailActivity
import com.yingwumeijia.baseywmj.function.message.MessageActivity
import com.yingwumeijia.baseywmj.nimim.conversation.customer.CustomerTeamMessageActivity
import com.yingwumeijia.baseywmj.option.PATHUrlConfig

/**
 * Created by jamisonline on 2017/6/27.
 */
object StartActivityManager {

    /**
     * 打开案例详情页面
     */
    fun startCaseDetailActivity(activity: Activity, caseId: Int) {
        CaseDetailActivity.start(activity, caseId, false)
    }

    /**
     * 跳转到用户服务页面

     * @param context
     */
    fun startAgreementActivity(context: Context) {
        WebViewManager.startHasTitle(context, "https://mobile.yingwumeijia.com/template/userAgreement.html", "用户服务协议")
    }


    /**
     * 跳转到站内信
     */
    fun startMessageActivity(context: Context) {
        MessageActivity.start(context)
    }


    /**
     * 跳转到会员权益
     */
    fun startVipInfoPage(context: Context) {
        WebViewManager.startHasTitle(context, "https://mobile.yingwumeijia.com/template/memberRights.html", null)
    }

    /**
     * 跳转到美家保障协议
     */
    fun startMjProjectInfoPage(context: Context) {
        val url = PATHUrlConfig.baseH5Url().replace("appv/", "")
        WebViewManager.startFullScreen(context, url + "template/safeguard/safeguard.html?backArrow=1")
    }

    fun startConversation(context: Context, sessionId: String) {
//        if (RongIM.getInstance() != null) {
//            RongIM.getInstance().startConversation(context, Conversation.ConversationType.GROUP, sessionId, null)
//        }

        if (AppTypeManager.isAppC())
            CustomerTeamMessageActivity.start(context, sessionId)
//        else

//        NimUIKit.startTeamSession(context, sessionId)
    }


    /**
     * 优惠券说明

     * @param context
     */
    fun startCouponKnow(context: Context) {
        WebViewManager.startFullScreen(context, "https://mobile.yingwumeijia.com/template/twitter/coupon-know.shtml?close=1")
    }

}