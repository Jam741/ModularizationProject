package com.yingwumeijia.commonlibrary.base

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD

open class BaseActivity : AppCompatActivity() {

    lateinit var content: Activity

    private var toast: Toast? = null

    val progressDialog: KProgressHUD by lazy {
        KProgressHUD.create(content, KProgressHUD.Style.PIE_DETERMINATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = this
    }


    fun createToast(context: Context): Toast {
        var toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        return toast
    }

    fun toastWith(msg: String) {
        if (toast == null)
            toast = Toast.makeText(content, msg, Toast.LENGTH_SHORT)
        else
            toast!!.setText(msg)
        toast!!.show()
    }

    fun toastWith(@IdRes msg: Int) {
        if (toast == null)
            toast = Toast.makeText(content, msg, Toast.LENGTH_SHORT)
        else
            toast!!.setText(msg)
        toast!!.show()
    }

    fun toastCancel() {
        toast!!.cancel()
    }

}
