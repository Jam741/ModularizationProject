package com.yingwumeijia.baseywmj.function.casedetails

import android.app.Activity
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.casedetails.model.CaseDetailBannerBean
import com.yingwumeijia.baseywmj.function.casedetails.model.CreateSessionBean
import com.yingwumeijia.baseywmj.function.collect.CollectType
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import rx.Observable

/**
 * Created by jamisonline on 2017/6/25.
 */
class CaseDetailPresenter(var context: Activity, var caseId: Int, var view: CaseDetailContract.View) : CaseDetailContract.Presenter {

    var caseSimpleData: CaseDetailBannerBean? = null

    val isAppc = AppTypeManager.isAppC()

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
            showDesignerPortrait(caseSimpleData!!.designerImage)
        }
    }


    override fun collect() {
        if (!UserManager.precedent(context)) return Unit
        var ob = Api.service.collect(caseId, CollectType.CASE)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                view.setCollectStatus(true)
            }
        })
    }

    override fun share() {
        TODO("分享") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelCollect() {
        if (!UserManager.precedent(context)) return Unit
        var ob = Api.service.cancelCollect(caseId, CollectType.CASE)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                view.setCollectStatus(false)
            }
        })
    }

    override fun connectTeam() {
        if (!UserManager.precedent(context)) return Unit
        if (caseSimpleData == null) return Unit
        if (caseSimpleData!!.chatId != 0L) {
            TODO("去回话页面")
        } else {
            val ob = Api.service.createSession(caseId)
            HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<CreateSessionBean>(context) {
                override fun _onNext(t: CreateSessionBean?) {
                    if (t == null) return Unit
                    if (caseSimpleData != null)
                        caseSimpleData!!.setChatId(t!!.id)
                    TODO("去回话页面")
                }
            })
        }
    }

}