package com.yingwumeijia.commonlibrary.base

import android.app.Activity
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

    lateinit var context: Fragment

    private var toast: Toast? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context = this
    }


    fun toastWith(msg: String) {
        if (toast == null)
            toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
        else
            toast!!.setText(msg)
        toast!!.show()
    }

    fun toastWith(@IdRes msg: Int) {
        if (toast == null)
            toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
        else
            toast!!.setText(msg)
        toast!!.show()
    }

    fun toastCancel() {
        if (toast != null)
            toast!!.cancel()
    }

}