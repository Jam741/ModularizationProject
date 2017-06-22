package com.yingwumeijia.baseywmj.function.user.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.UserBean
import com.yingwumeijia.baseywmj.function.main.MainActivity
import com.yingwumeijia.baseywmj.function.user.UserContract
import com.yingwumeijia.baseywmj.function.user.UserPresenter
import com.yingwumeijia.baseywmj.function.user.UserResponseCallBack
import com.yingwumeijia.baseywmj.function.user.findpwd.FindPwdActivity
import com.yingwumeijia.baseywmj.function.user.register.RegisterActivity
import kotlinx.android.synthetic.main.login_layout.*

/**
 * Created by jamisonline on 2017/6/15.
 */
class LoginActivity : JBaseActivity(), UserContract.LoginView, UserResponseCallBack {


    val currentLogin by lazy { intent.getBooleanExtra(Constant.KEY_LOGIN_SOURCE, Constant.DEFAULT_BOOLEAN_VALUE) }

    companion object {
        fun start(context: Activity, currentLogin: Boolean) {
            val starter = Intent(context, LoginActivity::class.java)
            starter.putExtra(Constant.KEY_LOGIN_SOURCE, currentLogin)
            context.startActivity(starter)
        }
    }

    override fun error(msg: String, code: Int) {
        toastWith(msg)
    }

    override fun success(userBean: UserBean) {}

    override fun completed() {
        close()
        if (!currentLogin) {
            MainActivity.start(context)
        }
    }

    val userPresenter by lazy {
        UserPresenter(context, this@LoginActivity, lifecycleSubject, this@LoginActivity)
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
        setContentView(R.layout.login_layout)

        /*E端隐藏注册按钮*/
        if (isAppC) btn_register.visibility = View.VISIBLE else btn_register.visibility = View.GONE

        ed_phone.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_login.isEnabled = true
            }
        })

        ed_password.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                hideSoftInput(v)
                didLogin()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }


        btn_login.setOnClickListener { v: View? -> didLogin() }
        btn_findPwd.setOnClickListener { v: View? -> FindPwdActivity.start(context) }
        btn_register.setOnClickListener { v: View? -> RegisterActivity.start(context) }

    }


    fun phoneValue(): String {
        return ed_phone.text.toString()
    }

    fun passwordValue(): String {
        return ed_password.text.toString()
    }

    /**
     * 登录操作
     */
    fun didLogin() {
        userPresenter.login(phoneValue(), passwordValue())
    }
}