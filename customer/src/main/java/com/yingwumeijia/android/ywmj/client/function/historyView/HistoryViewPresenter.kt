package com.yingwumeijia.android.ywmj.client.function.historyView

import android.app.Activity
import android.view.View
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder

/**
 * Created by jamisonline on 2017/7/4.
 */
class HistoryViewPresenter(var activity: Activity, var view: HistoryViewContract.View) : HistoryViewContract.Presenter {


    val historyViewAdapter by lazy { createHistoryAdapter() }

    private fun createHistoryAdapter(): CommonRecyclerAdapter<CaseBean> {
        return object : CommonRecyclerAdapter<CaseBean>(activity, null, null, R.layout.item_history_view) {
            override fun convert(holder: RecyclerViewHolder, caseBean: CaseBean, position: Int) {
                holder.run {
                    setTextWith(R.id.tv_name, caseBean.caseName)
                    setImageUrl480(activity!!, R.id.iv_case, caseBean.caseCover)
                    setSize(R.id.item_layout, (ScreenUtils.screenWidth - 10) / 2, (ScreenUtils.screenWidth - 10) / 2 * 680 / 720)
                            .setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                                override fun itemClick(itemView: View, position: Int) {
                                    StartActivityManager.startCaseDetailActivity(activity!!, caseBean.caseId)
                                }
                            })
                }
            }
        }
    }

    override fun start() {
        val ob = Api.service.getHistoryViews()
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<List<CaseBean>>(activity) {
            override fun _onNext(t: List<CaseBean>?) {
                view.showEmpty(ListUtil.isEmpty(t))
                if (ListUtil.isEmpty(t)) return Unit
                view.showHistoryNum(t!!.size)
                view.showHistoryList(t!!)
            }
        })
    }

    override fun clear() {
        val ob = Api.service.clearHistoryViews()
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(activity) {
            override fun _onNext(t: String?) {
                view.showEmpty(true)
            }
        })
    }
}