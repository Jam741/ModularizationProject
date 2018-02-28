package com.yingwumeijia.baseywmj.function.introduction.serviceStandard

import android.app.Activity
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import rx.Observable

/**
 * Created by jamisonline on 2017/6/29.
 */
class ServiceStandardPresenter(var activity: Activity, var view: ServiceStandardContract.View) : ServiceStandardContract.Presenter {

    val isAppC = AppTypeManager.isAppC()

    var serviceBean: ServiceStandardBean? = null

    val itemAdapter by lazy { createItemAdapter() }

    private fun createItemAdapter(): CommonRecyclerAdapter<ServiceStandardBean.ServiceBean.ItemsBean> {
        return object : CommonRecyclerAdapter<ServiceStandardBean.ServiceBean.ItemsBean>(activity, null, serviceBean!!.service.items as ArrayList<ServiceStandardBean.ServiceBean.ItemsBean>, R.layout.item_servicestandard_item) {
            override fun convert(holder: RecyclerViewHolder, t: ServiceStandardBean.ServiceBean.ItemsBean, position: Int) {
                var costPercent = ""

                //费用类型，1表示百分比，2表示固定金额，3表示免费，4表示另记,
                when (t.costType) {
                    1 -> costPercent = "占总价" + t.costValue + "%"
                    2 -> costPercent = "¥ " + t.costValue
                    3 -> costPercent = "免费"
                    4 -> costPercent = "另计"
                }

                holder.run {
                    setVisible(R.id.line, position != itemCount - 1)
                    setTextWith(R.id.tv_title, "【" + (position + 1) + "】" + t.title)
                    setTextWith(R.id.tv_price, costPercent)
                    setTextWith(R.id.tv_desc, t.description)
                }
            }
        }
    }

    override fun loadProcedureData(id: Int, sourceType: Int, serviceType: Int) {
        var ob: Observable<ServiceStandardBean>
        if (isAppC) ob = Api.service.getServiceStandardData_C(id, sourceType, serviceType)
        else ob = Api.service.getServiceStandardData_E(id, sourceType, serviceType)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<ServiceStandardBean>(activity) {
            override fun _onNext(t: ServiceStandardBean?) {
                if (t == null) return Unit
                serviceBean = t
                if (t.service != null)
                    if (!ListUtil.isEmpty(t.service.items))
                        view.showItems(t.service.items)
                if (t.employeeVo != null)
                    view.showEmployee(t.employeeVo)
            }

        })
    }
}