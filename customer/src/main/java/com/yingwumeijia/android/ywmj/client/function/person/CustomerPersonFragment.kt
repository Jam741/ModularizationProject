package com.yingwumeijia.android.ywmj.client.function.person

import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.function.personal.BaseLoggedFragment
import com.yingwumeijia.baseywmj.function.personal.PersonMenuFragment
import com.yingwumeijia.baseywmj.function.personal.PersonalFragment

/**
 * Created by jamisonline on 2017/7/3.
 */
class CustomerPersonFragment : PersonalFragment() {


    companion object {
        fun newInstance(): CustomerPersonFragment {
            return CustomerPersonFragment()
        }
    }

    override fun getLoggedHeaderFragment(): BaseLoggedFragment {
        return LoggedFragment.newInstance()
    }


    override fun getMenuFragment(): PersonMenuFragment {
        return MenuFragment.newInstance()
    }

}