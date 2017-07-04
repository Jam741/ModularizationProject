package com.yingwumeijia.android.ywmj.client.function.person.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.person.info.PersonInfoActivity
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.utils.VerifyUtils
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import kotlinx.android.synthetic.main.edit_person_info_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/4.
 */
class EditPersonInfoActivity : JBaseActivity() {

    val inputStr by lazy { intent.getStringExtra(Constant.KEY_INPUT_TEXT) }

    companion object {
        fun startForResult(activity: Activity, request_code: Int, inputText: String) {
            val intent = Intent(activity, EditPersonInfoActivity::class.java)
            intent.putExtra(Constant.KEY_INPUT_TEXT, inputText)
            ActivityCompat.startActivityForResult(activity, intent, request_code, Bundle.EMPTY)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_person_info_act)

        topTitle.text = "更改昵称"
        ed_input.hint = "请输入昵称"
        ed_input.setText(inputStr)
        topLeft.setOnClickListener { close() }
        btn_clear_edit.setOnClickListener { ed_input.setText("") }
        btn_confirm.setOnClickListener { if (verifyNikeName(inputValue())) updateNikeName() }
        ed_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_confirm.isEnabled = inputValue().length > 0
                btn_clear_edit.visibility = if (inputValue().length > 0) View.VISIBLE else View.GONE
            }
        })
    }


    private fun verifyNikeName(nikeName: String?): Boolean {
        if (!VerifyUtils.verifyNikeName(nikeName)) {
            toastWith(R.string.input_nikename_error)
            return false
        }
        return true
    }


    fun inputValue(): String {
        return ed_input.text.toString()
    }

    fun updateNikeName() {
        val ob = Api.service.updateNickName(inputValue())
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                didUpdateSuccess()
            }

        })
    }

    private fun didUpdateSuccess() {
        toastWith("修改成功")
        UserManager.refreshNikeName(inputValue())
        val intent = Intent(context, PersonInfoActivity::class.java)
        intent.putExtra(Constant.KEY_INPUT_RESULT, inputValue())
        setResult(RESULT_OK, intent)
        finish()
    }
}