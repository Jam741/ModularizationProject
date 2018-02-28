package com.yingwumeijia.baseywmj.function.user

/**
 * Created by jamisonline on 2017/6/15.
 */
interface UserContract {

    interface Presenter {

        fun login(phone: String, password: String)

        fun register(phone: String, password: String, smsCode: String, invaCode: String?)

        fun findPasssword(phone: String,password: String,smsCode: String)

        fun sendSmsCode(phone: String, source: Int)
    }


    interface View {

        fun showInputErrorFromPhone()

        fun showInputErrorFromPassword()

        fun cleanPwassword()

    }

    interface LoginView : View

    interface SendSmsView : View {

        fun showInputErrorFromSmsCode()

        fun resetSendSmsButtom()

        fun didSendSmsButtonText(s: String)

        fun launchSendSmsButtonText()
    }


    interface FindPasswordView : SendSmsView

    interface RegisterView : SendSmsView
}