package com.yingwumeijia.android.worker.function.collectunread

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.yingwumeijia.android.worker.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.CollectUnreadResultBean
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import com.yingwumeijia.commonlibrary.widget.recycler.LoadingMoreFooter
import com.yingwumeijia.commonlibrary.widget.recycler.XRecyclerView
import kotlinx.android.synthetic.main.collect_unread_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by Jam on 2017/3/25 下午11:10.
 * Describe:
 */

class CollectUnreadActivity : JBaseActivity(), CollectUnreadContract.View {

    private var pageNum = Config.page

    private val presenter by lazy { CollectUnreadPresenter(context, this) }

    private val listAdapter: CommonRecyclerAdapter<CollectUnreadResultBean.ResultBean> by lazy { createAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collect_unread_act)
        topTitle.text = "收藏"
        topLeft.setOnClickListener { close() }

        rv_comment.run {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
            addFootView(LoadingMoreFooter(context, Config.NO_MORE_DATA))
            setLoadingListener(object : XRecyclerView.LoadingListener {
                override fun onRefresh() {
                    pageNum = Config.page
                    loadList()
                }

                override fun onLoadMore() {
                    pageNum++
                    loadList()
                }
            })
        }
        loadList()
    }

    fun loadList() {
        presenter.loadData(pageNum)
    }


    private fun createAdapter(): CommonRecyclerAdapter<CollectUnreadResultBean.ResultBean> {
        return object : CommonRecyclerAdapter<CollectUnreadResultBean.ResultBean>(context, null, null, R.layout.item_unread_collect) {
            override fun convert(holder: RecyclerViewHolder, resultBean: CollectUnreadResultBean.ResultBean, position: Int) {

                val imageView = holder.getViewWith(R.id.iv_portrait) as ImageView
                JImageLolder.loadPortrait256(context, imageView, resultBean.userShowHead)
                holder.run {
                    setTextWith(R.id.tv_name, resultBean.userShowName)
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            StartActivityManager.startCaseDetailActivity(context, resultBean.caseId)
                        }
                    })
                }

            }
        }
    }


    override fun onResponse(list: List<*>) {
        if (ListUtil.isEmpty(list)) return
        if (pageNum == Config.page)
            listAdapter.refresh(list as ArrayList<CollectUnreadResultBean.ResultBean>)
        else
            listAdapter.addRange(list as ArrayList<CollectUnreadResultBean.ResultBean>)
    }

    override fun loadCompleted(page: Int, empty: Boolean) {
        if (page == Config.page) {
            showEmptyLayout(empty)
            rv_comment.setIsnomore(false)
            rv_comment.refreshComplete()
        } else {
            rv_comment.loadMoreComplete()
            rv_comment.setIsnomore(empty)
        }
    }

    override fun showEmptyLayout(empty: Boolean) {
        empty_layout!!.visibility = if (empty) View.VISIBLE else View.GONE
        rv_comment.visibility = if (!empty) View.VISIBLE else View.GONE
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, CollectUnreadActivity::class.java)
            context.startActivity(starter)
        }
    }
}
