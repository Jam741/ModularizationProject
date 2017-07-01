package com.yingwumeijia.baseywmj.function.setting.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.commonlibrary.utils.AppUtils
import kotlinx.android.synthetic.main.about_us_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/6/30.
 */
class AboutUsActivity : JBaseActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, AboutUsActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_us_act)

        topTitle.text = "关于鹦鹉美家"
        topLeft.setOnClickListener { close() }
        btn_function_describe.setOnClickListener { TODO("功能介绍页面") }
        btn_connect_us.setOnClickListener { ConnectUsActivity.start(context) }
        tv_version_name.text = AppUtils.getAppName(context) + "" + AppUtils.getVersionName(context)

    }

}