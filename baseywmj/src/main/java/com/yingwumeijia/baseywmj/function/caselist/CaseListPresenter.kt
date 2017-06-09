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
import com.yingwumeijia.commonlibrary.utils.ListUtil
import rx.Scheduler
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject

/**
 * Created by jamisonline on 2017/6/5.
 */

class CaseListPresenter(var view: CaseListContract.View, var context: Fragment, var lifecycleSubject: PublishSubject<ActivityLifeCycleEvent>) : CaseListContract.Presenter {


    override fun loadCaseList(page: Int, caseFilterOptionBody: CaseFilterOptionBody) {

        val ob = Api.service.getCaseList(page, Config.size, caseFilterOptionBody)


//        ob.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<List<CaseBean>>() {
//                    override fun onNext(t: List<CaseBean>?) {
//                    }
//
//                    override fun onCompleted() {
//                        Logger.d("onCompleted")
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        e!!.printStackTrace()
//                        Logger.d("onError")
//                        if (e is ApiException)
//                    }
//
//                })


        HttpUtil.getInstance().toSubscribe(ob, object : ProgressSubscriber<List<CaseBean>>(context.context) {
            override fun _onError(message: String) {
                Toast.makeText(context.activity,message,Toast.LENGTH_SHORT).show()
            }

            override fun _onNext(list: List<CaseBean>?) {
                view.showEmpty(page == Config.page && ListUtil.isEmpty(list))
                view.onLoadComplete(page, ListUtil.isEmpty(list))
                if (list != null)
                    view.onResponseList(list as ArrayList<CaseBean>)

            }
        }, "cacheKey", ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, false)

    }


    init {
        Logger.d("createPresenter")
    }
}
