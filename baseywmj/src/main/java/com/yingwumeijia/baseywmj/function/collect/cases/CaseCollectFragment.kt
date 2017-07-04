package com.yingwumeijia.baseywmj.function.collect.cases

import android.view.View
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.collect.base.CollectListContract
import com.yingwumeijia.baseywmj.function.collect.base.CollectListFragment
import com.yingwumeijia.baseywmj.function.collect.company.CaseCollectPresenter
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder

/**
 * Created by jamisonline on 2017/7/1.
 */
class CaseCollectFragment : CollectListFragment<CollectCaseBean.ResultBean>() {
    override fun createListAdapter(): CommonRecyclerAdapter<CollectCaseBean.ResultBean> {
        return object : CommonRecyclerAdapter<CollectCaseBean.ResultBean>(null, context, null, R.layout.item_collect_list) {
            override fun convert(holder: RecyclerViewHolder, t: CollectCaseBean.ResultBean, position: Int) {
                holder.run {
                    setTextWith(R.id.tv_name, t.caseName)
                    setImageUrl480(context, R.id.iv_case, t.caseCover)
                    setSize(R.id.item_layout, (ScreenUtils.screenWidth - 10) / 2, ((ScreenUtils.screenWidth - 10) / 2) * 680 / 720)
                    setOnClickListener(R.id.iv_cancel_collection, View.OnClickListener { showCancelDialog(t.caseId, position) })
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            StartActivityManager.startCaseDetailActivity(context.activity, t.caseId)
                        }

                    })
                }
            }

        }
    }

    override fun createCollectPresenter(): CollectListContract.Presenter {
        return CaseCollectPresenter(context.context, this)
    }

    override fun emptyText(): String {
        return "空空如也，快去收藏作品吧"
    }
}