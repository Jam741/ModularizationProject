package com.yingwumeijia.commonlibrary.base

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD

open class BaseActivity : AppCompatActivity() {

    lateinit var context: Activity

    private var toast: Toast? = null

    val progressDialog: KProgressHUD by lazy {
        KProgressHUD.create(context, KProgressHUD.Style.PIE_DETERMINATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
    }


    fun createToast(context: Context): Toast {
        var toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        return toast
    }

    fun toastWith(msg: String) {
        if (toast == null)
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        else
            toast!!.setText(msg)
        toast!!.show()
    }

    fun toastWith(@IdRes msg: Int) {
        if (toast == null)
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        else
            toast!!.setText(msg)
        toast!!.show()
    }

    fun toastCancel() {
        toast!!.cancel()
    }

    fun hideSoftInput(v: View) {
        val imm = v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }

    fun close() {
        ActivityCompat.finishAfterTransition(this)
    }

}
