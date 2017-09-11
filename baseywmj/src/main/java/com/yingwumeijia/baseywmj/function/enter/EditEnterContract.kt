package com.yingwumeijia.baseywmj.function.enter

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.ApplyJoinBean

/**
 * Created by Jam on 2017/2/24 上午11:41.
 * Describe:
 */

interface EditEnterContract {


    interface View : JBaseView {

        fun setSendSmsCodeText(s: String?)

        fun lockSendSmsButton()

        fun unLockSendSmsButton()

        fun joinSuccess()
    }

    interface Presenter : JBasePresenter {


        fun sendSmsCode(phoneNum: String?)

        fun commit(applyJoinBean: ApplyJoinBean?)

    }
}
