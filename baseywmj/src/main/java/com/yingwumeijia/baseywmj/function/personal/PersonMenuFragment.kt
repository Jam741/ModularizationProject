package com.yingwumeijia.baseywmj.function.personal

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import kotlinx.android.synthetic.main.person_menu.*

/**
 * Created by jamisonline on 2017/7/3.
 */
abstract class PersonMenuFragment : JBaseFragment(), PersonGroupMenuAdapter.MenuOnItemClickListener, PersonGroupMenuAdapter.MenuOnItemLongClickListener {


    val personGroupMenuAdapter: PersonGroupMenuAdapter by lazy {
        PersonGroupMenuAdapter(activity, this, this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.person_menu, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_menu.run {
            layoutManager = LinearLayoutManager(context)
            adapter = personGroupMenuAdapter
        }
    }

    abstract fun initMenuForUserDetailType(userDetailType: Int)

    fun refreshMenu(menuInfosList: ArrayList<ArrayList<MenuInfo>>) {
        personGroupMenuAdapter.refreshMenu(menuInfosList)
    }
}