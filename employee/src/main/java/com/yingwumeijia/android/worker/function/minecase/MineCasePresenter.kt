package com.yingwumeijia.android.worker.function.minecase

import android.content.Context
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.MineCaseResultBean
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil

/**
 * Created by Jam on 2017/3/3 下午4:57.
 * Describe:
 */

class MineCasePresenter(private val context: Context, private val mView: MineCaseContract.View) : MineCaseContract.Presenter {


    override fun loadMineCase(pageNum: Int) {

        HttpUtil.getInstance().toNolifeSubscribe(Api.service.mineCaseData(pageNum, Config.size), object : SimpleSubscriber<MineCaseResultBean>(context) {
            override fun _onNext(t: MineCaseResultBean?) {
                if (t == null) return
                mView.showEmptyLayout(ListUtil.isEmpty(t.result) && pageNum == Config.page)
                mView.loadComplete(pageNum, ListUtil.isEmpty(t.result))
                if (!ListUtil.isEmpty(t.result))
                    mView.onResponse(t.result)
            }

        })

    }


}
