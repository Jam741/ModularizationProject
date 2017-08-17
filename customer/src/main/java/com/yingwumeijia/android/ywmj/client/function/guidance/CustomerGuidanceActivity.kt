package com.yingwumeijia.android.ywmj.client.function.guidance

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.home.CustomerMainActivity
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.guidance.GuidanceActivity
import java.util.ArrayList

/**
 * Created by jamisonline on 2017/8/1.
 */
class CustomerGuidanceActivity : GuidanceActivity() {

    override fun createViews(): ArrayList<View> {
        return ArrayList<View>().apply {
            add(LayoutInflater.from(context).inflate(R.layout.guidance_page1, null))
            add(LayoutInflater.from(context).inflate(R.layout.guidance_page2, null))
            add(LayoutInflater.from(context).inflate(R.layout.guidance_page3, null))
        }
    }

    override fun next() {
        UserManager.setNotFirst(context)
        CustomerMainActivity.start(context)
    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, CustomerGuidanceActivity::class.java)
            context.startActivity(starter)
        }
    }
}