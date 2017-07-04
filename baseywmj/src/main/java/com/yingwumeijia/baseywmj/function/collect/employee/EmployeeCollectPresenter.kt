package com.yingwumeijia.baseywmj.function.collect.employee

import android.content.Context
import com.yingwumeijia.android.ywmj.client.function.collect.base.CollectListPresenter
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.function.collect.CollectType
import com.yingwumeijia.baseywmj.function.collect.base.CollectListContract
import com.yingwumeijia.baseywmj.option.Config
import rx.Observable

/**
 * Created by jamisonline on 2017/7/1.
 */
class EmployeeCollectPresenter(context: Context,view:CollectListContract.View) :CollectListPresenter<CollectEmployeeBean>(context,view){

    override fun loadListDataObservable(page: Int): Observable<CollectEmployeeBean> {
        return Api.service.getCollectEmployeeData(page, Config.size)
    }

    override fun type(): String {
        return CollectType.EMPLOYEE
    }
}