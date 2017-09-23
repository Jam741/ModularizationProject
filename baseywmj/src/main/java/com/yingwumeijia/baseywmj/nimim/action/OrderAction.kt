package com.yingwumeijia.baseywmj.nimim.action

import android.widget.Toast
import com.netease.nim.uikit.session.actions.BaseAction
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.WebViewManager
import com.yingwumeijia.baseywmj.im.IMManager
import com.yingwumeijia.baseywmj.utils.net.SeverUrlManager

/**
 * Created by jamisonline on 2017/9/21.
 */

class OrderAction : BaseAction(R.drawable.nim_message_plus_order_selector, R.string.nim_im_action_order) {

    override fun onClick() {
        if (UserManager.getUserData()!!.userDetailType == 10) {
            Toast.makeText(activity, "家居顾问不能创建订单", Toast.LENGTH_SHORT).show();
        } else {
            WebViewManager.startFullScreen(activity, SeverUrlManager.baseWebUrl() + "#/createOrder?type=1&sessionId=" + IMManager.currentSessionId(activity) + "&oneselfRole=" + UserManager.getUserData()!!.getUserDetailTypeDesc());
        }
    }

}
