package com.yingwumeijia.baseywmj.function.collect.company

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.function.collect.base.CollectListContract
import com.yingwumeijia.baseywmj.function.collect.base.CollectListFragment
import com.yingwumeijia.baseywmj.function.introduction.company.CompanyActivity
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder

/**
 * Created by jamisonline on 2017/6/30.
 */
class CompanyCollectFragment : CollectListFragment<CollectCompanyBean.ResultBean>() {
    override fun layoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(activity)
    }

    override fun createListAdapter(): CommonRecyclerAdapter<CollectCompanyBean.ResultBean> {
        return object : CommonRecyclerAdapter<CollectCompanyBean.ResultBean>(null, context, null, R.layout.item_productionteam_child_employ) {
            override fun convert(holder: RecyclerViewHolder, t: CollectCompanyBean.ResultBean, position: Int) {
                JImageLolder.loadPortrait256(context!!, holder.getViewWith(R.id.iv_portrait) as ImageView, t.companyHeadImage)
                var job = ""
                if (t.decorateTypes != null) {
                    for (s in t.decorateTypes) {
                        job += "/" + s
                    }
                    job = job.substring(1, job.length)
                }
                holder.run {
                    setVisible(R.id.topPadding, position == 0)
                    setTextWith(R.id.tv_name, t.companyName)
                    setTextWith(R.id.tv_job, job)
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            CompanyActivity.start(mContext!!, t.companyId, 0)
                        }

                    })
                    setOnItemLongClickListener(object : RecyclerViewHolder.OnItemLongCliceListener {
                        override fun itemLongClick(itemView: View, position: Int): Boolean {
                            showCancelDialog(t.companyId, position)
                            return false
                        }

                    })
                }
            }

        }
    }

    override fun createCollectPresenter(): CollectListContract.Presenter {
        return CompanyCollectPresenter(context.context, this)
    }

    override fun emptyText(): String {
        return "空空如也，快去收藏公司吧"
    }

}