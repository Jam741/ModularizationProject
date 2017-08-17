package com.yingwumeijia.commonlibrary.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kaopiz.kprogresshud.KProgressHUD

/**
 * Created by jamisonline on 2017/5/31.
 */
open class BaseFragment : Fragment() {

    lateinit var context: Fragment

    private var toast: Toast? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    val progressDialog: KProgressHUD by lazy {
        KProgressHUD.create(activity)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        context = this
        super.onViewCreated(view, savedInstanceState)
    }

    fun toastWith(msg: String) {
        if (toast == null)
            toast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT)
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