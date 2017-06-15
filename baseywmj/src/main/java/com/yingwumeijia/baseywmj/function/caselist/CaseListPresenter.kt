package com.yingwumeijia.baseywmj.function.caselist

import android.support.v4.app.Fragment
import android.widget.Toast
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent
import com.yingwumeijia.commonlibrary.net.HttpUtil
import com.yingwumeijia.commonlibrary.net.exception.ApiException
import com.yingwumeijia.commonlibrary.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import rx.Scheduler
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject

/**
 * Created by jamisonline on 2017/6/5.
 */

class CaseListPresenter(var view: CaseListContract.View, var context: Fragment, var lifecycleSubject: PublishSubject<ActivityLifeCycleEvent>) : CaseListContract.Presenter {


    override fun loadCaseList(page: Int, caseFilterOptionBody: CaseFilterOptionBody) {
        Logger.d(page.toString())
        val ob = Api.service.getCaseList(page, Config.size, caseFilterOptionBody)
        HttpUtil.getInstance().toSimpleSubscribe(ob, HttpUtil.newSubscriber(context.context, { list: List<CaseBean>? ->
            view.showEmpty(page == Config.page && ListUtil.isEmpty(list))
            Logger.d(page.toString() + ListUtil.isEmpty(list))
            view.onLoadComplete(page, ListUtil.isEmpty(list))
            if (list != null)
                view.onResponseList(list as ArrayList<CaseBean>)
        }), lifecycleSubject)
    }

    init {
        Logger.d("createPresenter")
    }
}
