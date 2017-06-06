package com.yingwumeijia.commonlibrary.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by jamisonline on 2017/5/31.
 */
open class BaseFragment : Fragment() {

    lateinit var context: BaseActivity

    private val toast: Toast by lazy {
        createToast(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context = activity as BaseActivity
    }

    fun createToast(context: Context): Toast {
        var toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        return toast
    }

    fun toastWith(msg: String) {
        toast.setText(msg)
    }

    fun toastWith(@IdRes msg: Int) {
        toast.setText(msg)
    }

    fun toastCancel() {
        toast.cancel()
    }

}