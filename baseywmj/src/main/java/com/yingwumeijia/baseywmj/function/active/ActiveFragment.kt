package com.yingwumeijia.baseywmj.function.active

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment

/**
 * Created by jamisonline on 2017/5/31.
 */
class ActiveFragment : JBaseFragment() {

    companion object {
        fun newInstance(): ActiveFragment {
            return ActiveFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.active_frag, container, false)
    }


}