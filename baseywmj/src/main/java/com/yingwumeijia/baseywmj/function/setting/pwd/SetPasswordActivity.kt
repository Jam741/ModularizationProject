package com.yingwumeijia.baseywmj.function.setting.pwd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.utils.VerifyUtils
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import kotlinx.android.synthetic.main.password_set_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/27.
 */
class SetPasswordActivity : JBaseActivity() {

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, SetPasswordActivity::class.java)
            ActivityCompat.startActivity(activity, intent, Bundle.EMPTY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.password_set_act)
        topTitle.text = "密码设置"
        topLeft.setOnClickListener { close() }

        ed_Old_pwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btn_confirm.isEnabled = s.toString().length > 0
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        ed_new_pwd_confirm.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                hideSoftInput(v)
                if (verifyInput()) {
                    changePassword()
                    return@setOnEditorActionListener true
                }
            }
            return@setOnEditorActionListener false
        }
    }

    private fun changePassword() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.setPassword(oldPasswordValue(), newComfirePasswordValue()), object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                toastWith(R.string.edit_password_succ)
                close()
            }

        })
    }

    private fun verifyInput(): Boolean {
        if (!VerifyUtils.verifyPassword(oldPasswordValue())) {
            toastWith(R.string.input_password_error)
            return false
        }

        if (!VerifyUtils.verifyPassword(newPasswordValue())) {
            toastWith(R.string.input_password_error)
            return false
        }
        if (!VerifyUtils.verifyPassword(newComfirePasswordValue())) {
            toastWith(R.string.input_password_error)
            return false
        }
        if (!newPasswordValue().equals(newComfirePasswordValue())) {
            toastWith(R.string.input_password_error)
            return false
        }
        return true
    }


    fun oldPasswordValue(): String {
        return ed_Old_pwd.text.toString()
    }

    fun newPasswordValue(): String {
        return ed_new_pwd.text.toString()
    }

    fun newComfirePasswordValue(): String {
        return ed_new_pwd_confirm.text.toString()
    }

}