package com.yingwumeijia.android.ywmj.client.function.person

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.mzule.activityrouter.annotation.Router
import com.github.mzule.activityrouter.router.Routers

import com.yingwumeijia.android.ywmj.client.R

@Router(value ="test")
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        Routers.open(this, "jam://main")

    }
}
