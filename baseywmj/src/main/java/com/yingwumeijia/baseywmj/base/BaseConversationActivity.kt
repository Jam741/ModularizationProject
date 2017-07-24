package com.yingwumeijia.baseywmj.base

import android.os.Bundle

/**
 * Created by jamisonline on 2017/7/24.
 */
open class BaseConversationActivity : JBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JBaseApp.setCurrentConversation(this)
    }
}