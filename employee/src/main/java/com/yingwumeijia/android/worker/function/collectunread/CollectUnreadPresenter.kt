package com.yingwumeijia.android.worker.function.collectunread

import android.content.Context
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CollectUnreadResultBean
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.RetrofitUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil


import rx.functions.Action1

/**
 * Created by Jam on 2017/3/25 下午11:17.
 * Describe:
 */

class CollectUnreadPresenter(private val context: Context, private val view: CollectUnreadContract.View) : CollectUnreadContract.Presenter {


    override fun loadData(pageNum: Int) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getCollectUnreadData(pageNum, Config.size), object : SimpleSubscriber<CollectUnreadResultBean>(context) {
            override fun _onNext(t: CollectUnreadResultBean?) {
                if (t == null) return
                view.showEmptyLayout(ListUtil.isEmpty(t.result) && pageNum == Config.page)
                if (!ListUtil.isEmpty(t.result))
                    view.onResponse(t.result)
                view.loadCompleted(pageNum, ListUtil.isEmpty(t.result))

            }

        })

    }
}
