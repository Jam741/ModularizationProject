package com.yingwumeijia.android.worker.function.minecase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.flyco.tablayout.utils.UnreadMsgUtils
import com.flyco.tablayout.widget.MsgView
import com.yingwumeijia.android.worker.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.MineCaseResultBean
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.comment.CommentActivity
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import com.yingwumeijia.commonlibrary.widget.recycler.XRecyclerView
import kotlinx.android.synthetic.main.mine_case_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/8/4.
 */
class MineCaseActivity : JBaseActivity(), MineCaseContract.View {

    val presenter by lazy { MineCasePresenter(this, this) }

    val caseAdapter by lazy { createMineCaseAdapter() }

    var pageNum = Config.page

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MineCaseActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mine_case_act)

        topTitle.text = "我的作品"

        rv_content.run {
            layoutManager = LinearLayoutManager(context)
            adapter = caseAdapter
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
        presenter.loadMineCase(pageNum)
    }

    override fun onResponse(list: List<*>) {
        if (pageNum == Config.page)
            caseAdapter.refresh(list as ArrayList<MineCaseResultBean.ResultBean>)
        else
            caseAdapter.addRange(list as ArrayList<MineCaseResultBean.ResultBean>)
    }

    override fun loadComplete(page: Int, isEmpty: Boolean) {
        if (page == Config.page) {
            rv_content.setIsnomore(false)
            rv_content.refreshComplete()
        } else {
            rv_content.loadMoreComplete()
            rv_content.setIsnomore(isEmpty)
        }
    }

    override fun showEmptyLayout(empty: Boolean) {
        empty_layout.visibility = if (empty) View.VISIBLE else View.GONE
    }


    private fun createMineCaseAdapter(): CommonRecyclerAdapter<MineCaseResultBean.ResultBean> {
        return object : CommonRecyclerAdapter<MineCaseResultBean.ResultBean>(context, null, null, R.layout.item_mine_case) {
            override fun convert(holder: RecyclerViewHolder, resultBean: MineCaseResultBean.ResultBean, position: Int) {

                val imageView = holder.getViewWith(R.id.iv_goods) as ImageView
                val msgView = holder.getViewWith(R.id.tv_messageNum) as MsgView
                UnreadMsgUtils.show(msgView, resultBean.unreadCommentNumber)
                JImageLolder.load(context, imageView, resultBean.caseCover)
                holder.run {
                    setTextWith(R.id.tv_goodsName, resultBean.caseName)
                    setTextWith(R.id.tv_view_count, resultBean.visitNumber.toString())
                    setTextWith(R.id.tv_collect_count, resultBean.collectionNumber.toString())
                    setTextWith(R.id.tv_goodsName, resultBean.caseName)
                    setOnClickListener(R.id.btn_checkComment, View.OnClickListener {
                        UnreadMsgUtils.show(msgView, 0)
                        CommentActivity.start(context, resultBean.id)
                    })
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            StartActivityManager.startCaseDetailActivity(context, resultBean.id)
                        }
                    })
                }
            }
        }
    }

}