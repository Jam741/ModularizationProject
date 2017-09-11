package com.yingwumeijia.baseywmj.function.user.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.UserBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.enter.EnterActivity
import com.yingwumeijia.baseywmj.function.main.MainActivity
import com.yingwumeijia.baseywmj.function.user.UserContract
import com.yingwumeijia.baseywmj.function.user.UserPresenter
import com.yingwumeijia.baseywmj.function.user.UserResponseCallBack
import com.yingwumeijia.baseywmj.function.user.findpwd.FindPwdActivity
import com.yingwumeijia.baseywmj.function.user.register.RegisterActivity
import io.rong.imkit.RongIM
import kotlinx.android.synthetic.main.login_layout.*

/**
 * Created by jamisonline on 2017/6/15.
 */
open class LoginActivity : JBaseActivity(), UserContract.LoginView, UserResponseCallBack {


    val currentLogin by lazy { intent.getBooleanExtra(Constant.KEY_LOGIN_SOURCE, Constant.DEFAULT_BOOLEAN_VALUE) }

    companion object {

        val request_code = 902


        fun start(context: Context, currentLogin: Boolean) {
            val starter = Intent(context, LoginActivity::class.java)
            starter.putExtra(Constant.KEY_LOGIN_SOURCE, currentLogin)
            context.startActivity(starter)
        }

        fun startCurrent(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            starter.putExtra(Constant.KEY_LOGIN_SOURCE, true)
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
//            if (AppTypeManager.isAppC()) {
//                val activityName = "ECustomerMainActivity"
//                val clazz = Class.forName(activityName)
//                val intent = Intent(this, clazz)
//                startActivity(intent)
//            } else {
//                val activityName = "EmployeeMainActivity"
//                val clazz = Class.forName(activityName)
//                val intent = Intent(this, clazz)
//                startActivity(intent)
//            }
            RongIM.getInstance().startConversationList(context, null)
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

        UserManager.setLoginStatus(context, false)

        /*E端隐藏注册按钮*/
        if (isAppC) {
            btn_register.text = "注册新账号"
            btn_register.setOnClickListener { RegisterActivity.start(context, currentLogin) }

            btn_findPwd.text = "找回密码"
            btn_findPwd.setOnClickListener { v: View? -> FindPwdActivity.start(context, currentLogin) }
        } else {
            btn_register.text = "找回密码"
            btn_register.setOnClickListener { FindPwdActivity.start(context, currentLogin) }

            btn_findPwd.text = "申请入驻"
            btn_findPwd.setOnClickListener { v: View? -> EnterActivity.start(context) }
        }

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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isAppC) {
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(0)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == request_code) {
            if (data != null) {
                val success = data.getBooleanExtra(Constant.KEY_CURRENT, false)
                if (success)
                    close()
            }
        }
    }

}