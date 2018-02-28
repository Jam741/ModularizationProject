package com.yingwumeijia.baseywmj.nimim.conversation

import android.app.Activity
import android.content.Intent
import com.netease.nim.uikit.session.SessionCustomization
import com.netease.nim.uikit.team.model.TeamExtras
import com.netease.nim.uikit.team.model.TeamRequestCode

/**
 * Created by jamisonline on 2017/9/21.
 */
open class BaseTeamSessionCustomization : SessionCustomization() {


    init {

    }


    override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TeamRequestCode.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val reason = data!!.getStringExtra(TeamExtras.RESULT_EXTRA_REASON)
                val finish = reason != null && (reason == TeamExtras.RESULT_EXTRA_REASON_DISMISS || reason == TeamExtras.RESULT_EXTRA_REASON_QUIT)
                if (finish) {
                    activity!!.finish() // 退出or解散群直接退出多人会话
                }
            }
        }
    }
}