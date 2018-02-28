package com.yingwumeijia.baseywmj.nimim.action

import com.netease.nim.uikit.session.actions.BaseAction
import com.netease.nimlib.sdk.msg.MessageBuilder
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.nimim.msg.PayMessageAttachment

/**
 * Created by jamisonline on 2017/9/21.
 */

class VipAction : BaseAction(R.drawable.nim_message_plus_vip_selector, R.string.nim_im_action_vip) {

    override fun onClick() {
        StartActivityManager.startVipInfoPage(activity)
    }

}
