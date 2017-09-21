package com.yingwumeijia.baseywmj.function.casedetails.material

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/9/21.
 */
class NullWebsiteActivity : JBaseActivity() {


    companion object {

        fun statr(context: Context) {
            val intent = Intent(context, NullWebsiteActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.null_website_act)

        topLeft.setOnClickListener { close() }
    }
}