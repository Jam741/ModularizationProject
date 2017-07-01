package com.yingwumeijia.baseywmj.function.caselist

import android.support.v4.app.Fragment
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import rx.subjects.PublishSubject

/**
 * Created by jamisonline on 2017/6/5.
 */

class CaseListPresenter(var view: CaseListContract.View, var context: Fragment, var lifecycleSubject: PublishSubject<ActivityLifeCycleEvent>) : CaseListContract.Presenter {


    override fun loadCaseList(page: Int, caseFilterOptionBody: CaseFilterOptionBody) {
        val ob = Api.service.getCaseList(page, Config.size, caseFilterOptionBody)
        HttpUtil.getInstance().toSimpleSubscribe(ob, object : SimpleSubscriber<List<CaseBean>>(context.context) {
            override fun _onNext(list: List<CaseBean>?) {
                view.showEmpty(page == Config.page && ListUtil.isEmpty(list))
                view.onLoadComplete(page, ListUtil.isEmpty(list))
                if (list != null)
                    view.onResponseList(list as ArrayList<CaseBean>)
            }

        }, lifecycleSubject)
    }

    init {
    }
}
