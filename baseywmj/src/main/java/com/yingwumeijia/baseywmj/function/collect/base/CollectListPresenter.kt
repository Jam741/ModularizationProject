package com.yingwumeijia.android.ywmj.client.function.collect.base

import android.content.Context
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.function.collect.base.CollectListContract
import com.yingwumeijia.baseywmj.function.collect.cases.CollectCaseBean
import com.yingwumeijia.baseywmj.function.collect.company.CollectCompanyBean
import com.yingwumeijia.baseywmj.function.collect.employee.CollectEmployeeBean
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import rx.Observable

/**
 * Created by jamisonline on 2017/7/1.
 */
abstract class CollectListPresenter<T>(var context: Context, var view: CollectListContract.View) : CollectListContract.Presenter {

    override fun loadData(page: Int, size: Int) {
        val ob: Observable<T> = loadListDataObservable(page)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : SimpleSubscriber<T>(context) {
            override fun _onNext(t: T?) {
                if (t is CollectCompanyBean) {
                    view.showEmpty(ListUtil.isEmpty(t.result))
                    view.onLoadComplete(page, ListUtil.isEmpty(t.result))
                    if (!ListUtil.isEmpty(t.result))
                        view.onResponse(t.result)
                } else if (t is CollectEmployeeBean) {
                    view.showEmpty(ListUtil.isEmpty(t.result))
                    view.onLoadComplete(page, ListUtil.isEmpty(t.result))
                    if (!ListUtil.isEmpty(t.result))
                        view.onResponse(t.result)
                } else if (t is CollectCaseBean) {
                    view.showEmpty(ListUtil.isEmpty(t.result))
                    view.onLoadComplete(page, ListUtil.isEmpty(t.result))
                    if (!ListUtil.isEmpty(t.result))
                        view.onResponse(t.result)
                }
            }
        })
    }

    abstract fun loadListDataObservable(page: Int): Observable<T>

    override fun cancelCollect(targetId: Int, position: Int) {
        val ob: Observable<String> = Api.service.cancelCollect(targetId, type())
        HttpUtil.getInstance().toNolifeSubscribe(ob,object :ProgressSubscriber<String>(context){
            override fun _onNext(t: String?) {
                view.cancelSucc(position)
            }

        })
    }

    abstract fun type(): String
}