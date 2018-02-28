package com.yingwumeijia.baseywmj.im.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import kotlinx.android.synthetic.main.edit_person_info_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/24.
 */
class EditProjectNameActivity : JBaseActivity() {

    val sessionId by lazy { intent.getStringExtra(Constant.KEY_SESSION_ID) }

    val projectName by lazy { intent.getStringExtra(Constant.KEY_INPUT_TEXT) }

    companion object {
        fun startForResult(context: Activity, sessionId: String, projectName: String, requster_code: Int) {
            val starter = Intent(context, EditProjectNameActivity::class.java)
            starter.putExtra(Constant.KEY_SESSION_ID, sessionId)
            starter.putExtra(Constant.KEY_INPUT_TEXT, projectName)
            ActivityCompat.startActivityForResult(context, starter, requster_code, Bundle.EMPTY)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_person_info_act)

        topTitle.text = "项目名称"
        ed_input.run {
            setText(projectName)
            hint = "请输入名称"
            filters = arrayOf<InputFilter>(InputFilter.LengthFilter(50))
        }
        btn_confirm.isEnabled = true


        ed_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_clear_edit.visibility = if (TextUtils.isEmpty(inputValue())) View.GONE else View.VISIBLE;
            }

        })
        topLeft.setOnClickListener { close() }
        btn_clear_edit.setOnClickListener { ed_input.setText("") }
        btn_confirm.setOnClickListener { if (verifyInput()) alterProjectName(inputValue()) }

    }

    private fun verifyInput(): Boolean {

        if (projectName.equals(inputValue().trim())) {
            return false
        }

        if (TextUtils.isEmpty(inputValue())) {
            toastWith("输入不能为空")
            return false
        }
        if (inputValue().trim().length < 4 || inputValue().length > 50) {
            toastWith("至少输入4个字符")
            return false
        }
        return true
    }

    fun inputValue(): String {
        return ed_input.text.toString()
    }

    fun alterProjectName(projectName: String) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.renameSession(sessionId, projectName), object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                toastWith("修改成功")
                val intent = Intent()
                intent.putExtra(Constant.KEY_INPUT_RESULT, projectName)
                setResult(RESULT_OK, intent)
                ActivityCompat.finishAfterTransition(context)
            }

        })
    }
}