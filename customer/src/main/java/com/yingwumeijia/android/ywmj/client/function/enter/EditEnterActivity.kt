package com.yingwumeijia.android.ywmj.client.function.enter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import kotlinx.android.synthetic.main.edit_enter_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/5.
 */
class EditEnterActivity : JBaseActivity(),EditEnterContract.View {

    companion object{
        fun start(context: Context) {
            val starter = Intent(context, EditEnterActivity::class.java)
            context.startActivity(starter)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_enter_act)
        topTitle.text = "申请入驻"

        topLeft.setOnClickListener { close() }
        ed_phone.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {  }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { btn_sendCode.isEnabled = true }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


    override fun setSendSmsCodeText(s: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lockSendSmsButton() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unLockSendSmsButton() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun joinSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}