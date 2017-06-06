package com.yingwumeijia.baseywmj.function.caselist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment


/**
 * Created by jamisonline on 2017/5/31.
 */
class CaseListFragment : JBaseFragment() {


    companion object {

        fun newInstance(): CaseListFragment {
            val args = Bundle()
            val fragment = CaseListFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.case_list_frag, container, false)
    }


}