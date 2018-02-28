package com.yingwumeijia.baseywmj.function.introduction.serviceStandard

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView

/**
 * Created by jamisonline on 2017/6/29.
 */
interface ServiceStandardContract {

    interface View : JBaseView {


        fun showItems(items: List<ServiceStandardBean.ServiceBean.ItemsBean>)


        fun showEmployee(employeeVoBean: ServiceStandardBean.EmployeeVoBean)

    }

    interface Presenter : JBasePresenter {

        fun loadProcedureData(id: Int, sourceType: Int, serviceType: Int)

    }
}