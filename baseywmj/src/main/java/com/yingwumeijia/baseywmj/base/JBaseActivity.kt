package com.yingwumeijia.baseywmj.base

import android.os.Bundle
import com.umeng.analytics.MobclickAgent
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent
import com.yingwumeijia.commonlibrary.base.BaseActivity
import rx.subjects.PublishSubject

open class JBaseActivity : BaseActivity(), JBaseView {

    val isAppC: Boolean = AppTypeManager.isAppC()


    val lifecycleSubject = PublishSubject.create<ActivityLifeCycleEvent>()


    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        lifecycleSubject.onNext(ActivityLifeCycleEvent.PAUSE)
        MobclickAgent.onPause(this)
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
