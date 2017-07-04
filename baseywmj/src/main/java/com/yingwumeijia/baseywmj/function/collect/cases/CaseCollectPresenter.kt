package com.yingwumeijia.baseywmj.function.collect.company

import android.content.Context
import com.yingwumeijia.android.ywmj.client.function.collect.base.CollectListPresenter
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.function.collect.CollectType
import com.yingwumeijia.baseywmj.function.collect.base.CollectListContract
import com.yingwumeijia.baseywmj.function.collect.cases.CollectCaseBean
import com.yingwumeijia.baseywmj.function.collect.employee.CollectEmployeeBean
import com.yingwumeijia.baseywmj.option.Config
import rx.Observable

/**
 * Created by jamisonline on 2017/6/30.
 */
class CaseCollectPresenter(context: Context, view: CollectListContract.View) : CollectListPresenter<CollectCaseBean>(context, view) {

    override fun loadListDataObservable(page: Int): Observable<CollectCaseBean> {
        return Api.service.getCollectCaseData(page, Config.size)
    }

    override fun type(): String {
        return CollectType.CASE
    }
}