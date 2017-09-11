package com.yingwumeijia.baseywmj.function.casedetails

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.sina.weibo.sdk.share.WbShareCallback
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.casedetails.model.CaseDetailBannerBean
import com.yingwumeijia.baseywmj.function.casedetails.model.CreateSessionBean
import com.yingwumeijia.baseywmj.function.collect.CollectType
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.T
import com.yingwumeijia.sharelibrary.ShareData
import com.yingwumeijia.sharelibrary.ShareDialog
import com.yingwumeijia.sharelibrary.ShareManager
import rx.Observable

/**
 * Created by jamisonline on 2017/6/25.
 */
class CaseDetailPresenter(var context: Activity, var caseId: Int, var view: CaseDetailContract.View) : CaseDetailContract.Presenter {

    var caseSimpleData: CaseDetailBannerBean? = null

    val isAppc = AppTypeManager.isAppC()

    var shareManager: ShareManager? = null

    val shareDialog by lazy { ShareDialog(shareManager!!) }

    override fun start() {

        var ob: Observable<CaseDetailBannerBean>
        if (isAppc) {
            ob = Api.service.getCaseDetailBannerBean_C(caseId)
        } else {
            ob = Api.service.getCaseDetailBannerBean_E(caseId)
        }
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<CaseDetailBannerBean>(context) {
            override fun _onNext(t: CaseDetailBannerBean?) {
                if (t != null) {
                    caseSimpleData = t
                    initView()
                }
            }
        })
    }

    private fun initView() {
        view.run {
            setCollectStatus(caseSimpleData!!.isCollected)
            CommentCount(caseSimpleData!!.commentTotalCount)
//            if (!TextUtils.isEmpty(caseSimpleData!!.designerImage))
//                showDesignerPortrait(caseSimpleData!!.designerImage)
        }
    }


    override fun collect() {
        if (!UserManager.precedent(context)) return Unit
        var ob = Api.service.collect(caseId, CollectType.CASE)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                view.setCollectStatus(true)
                T.showShort(context,"收藏作品成功")
            }
        })
    }

    override fun share() {
        if (caseSimpleData == null) return Unit
        val shareInfo = caseSimpleData!!.shareInfo
        Glide.with(context.applicationContext).load(shareInfo.cover + "&imageView2/2/w/256").asBitmap().into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                if (shareManager == null) {
                    val shareData = ShareData(shareInfo.caseName, shareInfo.designConcept, shareInfo.url, resource, shareInfo.cover + "&imageView2/2/w/256", 1)
                    shareManager = ShareManager(context, shareData!!, object : WbShareCallback {
                        override fun onWbShareFail() {}

                        override fun onWbShareCancel() {}

                        override fun onWbShareSuccess() {}
                    }, object : IUiListener {
                        override fun onComplete(p0: Any?) {}

                        override fun onCancel() {}

                        override fun onError(p0: UiError?) {}
                    })
                }
                shareDialog.show()
            }
        })
    }

    override fun cancelCollect() {
        if (!UserManager.precedent(context)) return Unit
        var ob = Api.service.cancelCollect(caseId, CollectType.CASE)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                view.setCollectStatus(false)
                T.showShort(context,"取消收藏成功")

            }
        })
    }

    override fun connectTeam() {
        if (!UserManager.precedent(context)) return Unit
        if (caseSimpleData == null) return Unit
        if (!TextUtils.isEmpty(caseSimpleData!!.chatId)) {
            StartActivityManager.startConversation(context, caseSimpleData!!.chatId)
        } else {
            val ob = Api.service.createSession(caseId)
            HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<CreateSessionBean>(context) {
                override fun _onNext(t: CreateSessionBean?) {
                    if (t == null) return Unit
                    if (caseSimpleData != null)
                        caseSimpleData!!.chatId = t!!.id
                    StartActivityManager.startConversation(context, t!!.id)
                }
            })
        }
    }


    fun onNewIntent(intent: Intent?) {
        if (intent != null && shareManager != null) {
            shareManager!!.onNextIntent(intent!!)
        }
    }
}