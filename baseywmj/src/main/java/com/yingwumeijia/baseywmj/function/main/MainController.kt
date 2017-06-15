package com.yingwumeijia.baseywmj.function.main

import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CaseTypeSetBean
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent
import com.yingwumeijia.commonlibrary.net.HttpUtil
import com.yingwumeijia.commonlibrary.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.net.subscriber.SimpleSubscriber
import rx.Observable
import rx.subjects.PublishSubject

/**
 * Created by jamisonline on 2017/6/12.
 */
class MainController(var view: MainActivity, var publishSubject: PublishSubject<ActivityLifeCycleEvent>) {

    var classfilyAdapter: CaseFilterClassfilyAdapter? = null
    var styleAdapter: NewCaseTypeAdapter? = null
    var hoseTypeAdapter: NewCaseTypeAdapter? = null
    var hoseAreaAdapter: NewCaseTypeAdapter? = null
    var cityAdapter: NewCaseTypeAdapter? = null

    init {

    }


    fun didCaseFilterSet() {
        var ob: Observable<CaseTypeSetBean> = Api.service.getCaseTypeSet()
        HttpUtil.getInstance().toSimpleSubscribe(ob, object : ProgressSubscriber<CaseTypeSetBean>(view) {

            override fun _onNext(t: CaseTypeSetBean) {
                classfilyAdapter = CaseFilterClassfilyAdapter(t.doneCasesTypes, t.designCasesTypes)
                styleAdapter = NewCaseTypeAdapter(t.decorateStyleType, view)
                hoseTypeAdapter = NewCaseTypeAdapter(t.houseType, view)
                hoseAreaAdapter = NewCaseTypeAdapter(t.houseAreaTypes, view)
                cityAdapter = NewCaseTypeAdapter(t.cityTypes, view)
                classfilyAdapter!!.setSelected(0, 0)
                styleAdapter!!.setSelected(0)
                hoseTypeAdapter!!.setSelected(0)
                hoseAreaAdapter!!.setSelected(0)
                cityAdapter!!.setSelected(0)
            }
        }, publishSubject)
    }
}