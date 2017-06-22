package com.yingwumeijia.baseywmj.function.user.findpwd

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.UserBean
import com.yingwumeijia.baseywmj.function.main.MainActivity
import com.yingwumeijia.baseywmj.function.sms.SmsCodeController
import com.yingwumeijia.baseywmj.function.user.UserContract
import com.yingwumeijia.baseywmj.function.user.UserPresenter
import com.yingwumeijia.baseywmj.function.user.UserResponseCallBack
import kotlinx.android.synthetic.main.findpassword_frag.*

/**
 * Created by jamisonline on 2017/6/19.
 */

class FindPwdActivity : JBaseActivity(), UserContract.FindPasswordView, UserResponseCallBack, View.OnClickListener {


    companion object {
        fun start(activity: Activity) {
            val starter: Intent = Intent(activity, FindPwdActivity::class.java)
            activity.startActivity(starter)
        }
    }

    /**
     * 点击事件
     */
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.topLeft -> close()
            R.id.btn_confirm -> didFindPasssword()
            R.id.btn_sendSmsCode -> didSendSmsCode()
        }
    }

    override fun error(msg: String, code: Int) {
        toastWith(msg)
    }

    override fun success(userBean: UserBean) {}

    override fun completed() {
        MainActivity.start(context)
    }

    val userPresenter: UserContract.Presenter by lazy { UserPresenter(context, this, lifecycleSubject, this) }


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

    override fun resetSendSmsButtom() {
        btn_send_code.run {
            isEnabled = true
            setBackgroundDrawable(resources.getDrawable(R.drawable.button_gradual_bg_round))
            text = "重新发出"
        }

    }

    override fun didSendSmsButtonText(s: String) {
        btn_send_code.text = s
    }

    override fun launchSendSmsButtonText() {
        btn_send_code.run {
            isEnabled = false
            setBackgroundColor(Color.parseColor("#C3C3C3"))
            text = "60s"
            setTextColor(resources.getColor(R.color.text_color_whide))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.findpassword_frag)

        ed_password.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                hideSoftInput(v)
                didFindPasssword()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        ed_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_confirm.isEnabled = true
            }

        })

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

    /**
     * 找回密码
     */
    fun didFindPasssword() {
        userPresenter.findPasssword(phoneValue(), passwordValue(), smsCodeValue())
    }

    /**
     * 发送短信验证码
     */
    fun didSendSmsCode() {
        userPresenter.sendSmsCode(phoneValue(), SmsCodeController.SmsCodeSource.findPassword)
    }
}
