package com.yingwumeijia.android.worker.function.person

import com.yingwumeijia.baseywmj.function.personal.BaseLoggedFragment
import com.yingwumeijia.baseywmj.function.personal.PersonMenuFragment
import com.yingwumeijia.baseywmj.function.personal.PersonalFragment

/**
 * Created by jamisonline on 2017/7/3.
 */
class EmployeePersonFragment : PersonalFragment() {

    val employeeLoggedHeaderFragment by lazy { LoggedFragment.newInstance() }

    override fun getMenuFragment(): PersonMenuFragment {
        return MenuFragment.newInstance()
    }


    companion object {
        fun newInstance(): EmployeePersonFragment {
            return EmployeePersonFragment()
        }
    }

    override fun getLoggedHeaderFragment(): BaseLoggedFragment {
        return employeeLoggedHeaderFragment
    }

    override fun showDistributionStatus(show: Boolean) {
        super.showDistributionStatus(show)
    }

}