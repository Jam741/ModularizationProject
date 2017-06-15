package com.yingwumeijia.baseywmj.function.user.login

import android.os.Bundle
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.function.user.UserContract
import kotlinx.android.synthetic.main.login_act.*

/**
 * Created by jamisonline on 2017/6/15.
 */
class LoginActivity : JBaseActivity(), UserContract.LoginView {

    val userPresenter by lazy {

    }

    override fun showInputErrorFromPhone() {
        ed_phone.error = resources.getString(R.string.input_phone_error)
    }

    override fun showInputErrorFromPassword() {
        ed_password.error = resources.getString(R.string.input_password_error)
    }

    override fun cleanPwassword() {
        ed_password.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_act)
    }

}