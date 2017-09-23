package com.yingwumeijia.baseywmj.nimim.conversation.customer

import com.netease.nim.uikit.session.actions.BaseAction
import com.yingwumeijia.baseywmj.nimim.action.OrderAction
import com.yingwumeijia.baseywmj.nimim.action.VipAction
import com.yingwumeijia.baseywmj.nimim.conversation.BaseTeamSessionCustomization
import java.util.ArrayList

/**
 * Created by jamisonline on 2017/9/21.
 */
class EmployeeTeamCustomization : BaseTeamSessionCustomization() {


    init {
        val actions = ArrayList<BaseAction>()
        actions.add(OrderAction())
        this.actions = actions
    }


}