package com.yingwumeijia.baseywmj.base

import android.os.Bundle
import com.yingwumeijia.baseywmj.im.IMManager

/**
 * Created by jamisonline on 2017/7/24.
 */
open class BaseConversationActivity : JBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        JBaseApp.setCurrentConversation(this)

        IMManager.setCurrentSessionId(this, intent.data.getQueryParameter("targetId"))
    }
}