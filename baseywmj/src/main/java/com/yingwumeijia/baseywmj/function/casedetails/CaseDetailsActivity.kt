package com.yingwumeijia.baseywmj.function.casedetails

import android.app.Activity
import android.content.Intent
import com.yingwumeijia.baseywmj.base.JBaseActivity

/**
 * Created by Jam on 2017/6/25.
 */
class CaseDetailsActivity : JBaseActivity() {


    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity, CaseDetailsActivity.javaClass)
            activity.startActivity(starter)
        }
    }
}