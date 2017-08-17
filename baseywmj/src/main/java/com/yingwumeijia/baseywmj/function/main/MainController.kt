package com.yingwumeijia.baseywmj.function.main

import android.content.Context
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CaseTypeSetBean
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import rx.Observable
import rx.subjects.PublishSubject

/**
 * Created by jamisonline on 2017/6/12.
 */
open class MainController(var context: Context, var publishSubject: PublishSubject<ActivityLifeCycleEvent>) {

    var classfilyAdapter: CaseFilterClassfilyAdapter? = null
    var styleAdapter: NewCaseTypeAdapter? = null
    var hoseTypeAdapter: NewCaseTypeAdapter? = null
    var hoseAreaAdapter: NewCaseTypeAdapter? = null
    var cityAdapter: NewCaseTypeAdapter? = null

    init {

    }


    fun didCaseFilterSet() {
        var ob: Observable<CaseTypeSetBean>
        if (AppTypeManager.isAppC()) {
            ob = Api.service.getCaseTypeSet()
        } else {
            ob = Api.service.getCaseTypeSet_E()
        }
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<CaseTypeSetBean>(context) {
            override fun _onNext(t: CaseTypeSetBean?) {
                if (t != null) {
                    classfilyAdapter = CaseFilterClassfilyAdapter(t!!.doneCasesTypes, t.designCasesTypes)
                    styleAdapter = NewCaseTypeAdapter(t.decorateStyleType, context)
                    hoseTypeAdapter = NewCaseTypeAdapter(t.houseType, context)
                    hoseAreaAdapter = NewCaseTypeAdapter(t.houseAreaTypes, context)
                    cityAdapter = NewCaseTypeAdapter(t.cityTypes, context)
                    classfilyAdapter!!.setSelected(0, 0)
                    styleAdapter!!.setSelected(0)
                    hoseTypeAdapter!!.setSelected(0)
                    hoseAreaAdapter!!.setSelected(0)
                    cityAdapter!!.setSelected(0)
                }
            }

        })
    }
}