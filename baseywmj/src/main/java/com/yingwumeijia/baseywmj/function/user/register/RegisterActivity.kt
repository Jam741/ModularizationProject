package com.yingwumeijia.baseywmj.function.user.register

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.UserBean
import com.yingwumeijia.baseywmj.function.main.MainActivity
import com.yingwumeijia.baseywmj.function.sms.SmsCodeController
import com.yingwumeijia.baseywmj.function.user.UserContract
import com.yingwumeijia.baseywmj.function.user.UserPresenter
import com.yingwumeijia.baseywmj.function.user.UserResponseCallBack
import kotlinx.android.synthetic.main.register_layout.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/6/16.
 */

class RegisterActivity : JBaseActivity(), UserContract.RegisterView, UserResponseCallBack, TextView.OnEditorActionListener, View.OnClickListener {


    val currentLogin by lazy { intent.getBooleanExtra(Constant.KEY_LOGIN_SOURCE, Constant.DEFAULT_BOOLEAN_VALUE) }


    companion object {
        fun start(activity: Activity, currentLogin: Boolean) {
            val starter: Intent = Intent(activity, RegisterActivity::class.java)
            starter.putExtra(Constant.KEY_LOGIN_SOURCE, currentLogin)
            activity.startActivity(starter)
        }
    }

    val userPresenter: UserPresenter by lazy {
        UserPresenter(context, this@RegisterActivity, lifecycleSubject, this@RegisterActivity)
    }

    override fun error(msg: String, code: Int) {
        toastWith(msg)
    }

    override fun success(userBean: UserBean) {}

    override fun completed() {
        close()
        if (!currentLogin)
            MainActivity.start(context)
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

    override fun showInputErrorFromSmsCode() {
        ed_smsCode.error = resources.getString(R.string.input_smsCode_error)
    }

    override fun launchSendSmsButtonText() {
        btn_sendSmsCode.run {
            isEnabled = false
            setBackgroundColor(Color.parseColor("#C3C3C3"))
            text = "60s"
            setTextColor(resources.getColor(R.color.text_color_whide))
        }
    }

    override fun resetSendSmsButtom() {
        btn_sendSmsCode.run {
            isEnabled = true
            setBackgroundDrawable(resources.getDrawable(R.drawable.button_gradual_bg_round))
            text = "重新发出"
        }

    }

    override fun didSendSmsButtonText(s: String) {
        btn_sendSmsCode.text = s
    }

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_register -> didRegister()
            R.id.topLeft -> close()
            R.id.btn_sendSmsCode -> didSendSmsCode()
        }
    }


    /**
     * 输入法事件
     */
    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            hideSoftInput(v!!)
            didRegister()
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_layout)
        topTitle.text = "注册新账号"

        ed_phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_register.isEnabled = true
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        ed_password.setOnEditorActionListener(this)
        ed_invCode.setOnEditorActionListener(this)
        topLeft.setOnClickListener(this)
        btn_register.setOnClickListener(this)
        btn_sendSmsCode.setOnClickListener(this)
    }


    fun phoneValue(): String {
        return ed_phone.text.toString()
    }

    fun passwordValue(): String {
        return ed_password.text.toString()
    }

    fun smsCodeValue(): String {
        return ed_smsCode.text.toString()
    }

    fun inviteCodeValue(): String {
        return ed_invCode.text.toString()
    }


    fun didRegister() {
        userPresenter.register(phoneValue(), passwordValue(), smsCodeValue(), inviteCodeValue())
    }

    fun didSendSmsCode() {
        userPresenter.sendSmsCode(phoneValue(), SmsCodeController.SmsCodeSource.register)
    }

}
