package com.yingwumeijia.baseywmj.function.personal.c

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.function.user.login.LoginActivity
import kotlinx.android.synthetic.main.person_notloggin_header_c.*

/**
 * Created by Jam on 2017/6/25.
 */
class NotLoggedFragment : JBaseFragment() {


    companion object {
        fun newInstance(): NotLoggedFragment {
            return NotLoggedFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.person_notloggin_header_c, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener { LoginActivity.start(activity, true) }
    }


}