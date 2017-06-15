package com.yingwumeijia.baseywmj.function.user

import com.yingwumeijia.commonlibrary.net.ErrorCode

/**
 * Created by jamisonline on 2017/6/15.
 */
interface UserContract {

    interface Presenter {

        fun login(phone: String, password: String)

        fun register(phone: String, password: String, smsCode: String, invaCode: String)

        fun sendSmsCode(phone: String, source: Int)
    }


    interface View {

        fun showInputErrorFromPhone()

        fun showInputErrorFromPassword()

        fun cleanPwassword()

    }

    interface LoginView : View

    interface FindPassworldView : View {

        fun showInputErrorFromSmsCode()

        fun enableSendSmsButton(enable: Boolean)

        fun resetSendSmsButtom()

        fun didSendSmsButtonText(s: String)
    }

    interface RegisterView : FindPassworldView
}