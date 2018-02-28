package com.yingwumeijia.baseywmj.function.setting.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.commonlibrary.utils.CallUtils
import kotlinx.android.synthetic.main.connect_us_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/6/30.
 */
class ConnectUsActivity : JBaseActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ConnectUsActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.connect_us_act)
        topLeft.setOnClickListener { close() }
        btn_connectUs.setOnClickListener { CallUtils.callPhone("02885108092", context) }
    }
}