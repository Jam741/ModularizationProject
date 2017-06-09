package com.yingwumeijia.baseywmj.base

import android.os.Bundle
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent
import com.yingwumeijia.commonlibrary.base.BaseActivity
import rx.subjects.PublishSubject

open class JBaseActivity : BaseActivity(), JBaseView {


    val lifecycleSubject = PublishSubject.create<ActivityLifeCycleEvent>()


    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY)
        super.onDestroy()
    }


}
