package com.yingwumeijia.commonlibrary.base

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kaopiz.kprogresshud.KProgressHUD

class BaseActivity : AppCompatActivity() {

    var content: Activity? = null

    private var progressDialog: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = this
    }


    fun showDialog(){
        if(progressDialog == null){
            progressDialog = KProgressHUD.create(content,KProgressHUD.Style.PIE_DETERMINATE)
        }

        if (!progressDialog!!.isShowing){
            progressDialog!!.show()
        }
    }
}
