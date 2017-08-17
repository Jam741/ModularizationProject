package com.yingwumeijia.baseywmj.function.personal

import android.os.Bundle
import android.view.View
import com.yingwumeijia.baseywmj.base.JBaseFragment

/**
 * Created by jamisonline on 2017/7/3.
 */
abstract class PersonMenuFragment : JBaseFragment(), PersonGroupMenuAdapter.MenuOnItemClickListener, PersonGroupMenuAdapter.MenuOnItemLongClickListener {


    val personGroupMenuAdapter: PersonGroupMenuAdapter by lazy {
        PersonGroupMenuAdapter(activity, this, this)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initMenuForUserDetailType(userDetailType: Int)

    fun refreshMenu(menuInfosList: ArrayList<ArrayList<MenuInfo>>) {
        personGroupMenuAdapter.refreshMenu(menuInfosList)
    }
}