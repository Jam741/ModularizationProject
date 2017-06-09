package com.yingwumeijia.baseywmj.base

import android.os.Bundle
import android.util.Log
import android.view.View
import com.kaopiz.kprogresshud.KProgressHUD
import com.orhanobut.logger.Logger
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent
import com.yingwumeijia.commonlibrary.base.BaseFragment
import rx.subjects.PublishSubject

/**
 * Created by jamisonline on 2017/5/31.
 */
open class JBaseFragment : BaseFragment() {

    val TAG = this.javaClass.simpleName


    val lifecycleSubject = PublishSubject.create<ActivityLifeCycleEvent>()

    val progressDialog: KProgressHUD by lazy {
        KProgressHUD.create(context.context, KProgressHUD.Style.ANNULAR_DETERMINATE)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE)
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(TAG, "onHiddenChanged hidden:" + hidden)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d("onLowMemory:", this.javaClass.simpleName)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }


    override fun onPause() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE)
        super.onPause()
        Log.d(TAG, "onPause")

    }

    override fun onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP)
        super.onStop()
        Log.d(TAG, "onStop")

    }

    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY)
        super.onDestroy()
        Log.d(TAG, "onDestroy")

    }
}