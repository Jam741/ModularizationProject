package com.yingwumeijia.baseywmj.function.active

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.function.web.JsIntelligencer
import com.yingwumeijia.baseywmj.function.web.OneWebFragment
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber

/**
 * Created by jamisonline on 2017/5/31.
 */
class ActiveFragment : JBaseFragment() {

    companion object {
        fun newInstance(): ActiveFragment {
            return ActiveFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.active_frag, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getActivityUrl(), object : SimpleSubscriber<String>(getContext()) {
            override fun _onNext(t: String?) {
                initContent(t!!)
            }

        })
    }


    fun initContent(url: String) {
        if (childFragmentManager.findFragmentById(R.id.content) == null) {

            val webFragment = OneWebFragment.newInstance(url)
//            val webFragment = OneWebFragment.newInstance("http://192.168.28.50:8089/src/template/activity/activityList.html?userType=c")
            childFragmentManager.beginTransaction().add(R.id.content, webFragment).commit()
        }
    }

}