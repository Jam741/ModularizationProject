package com.yingwumeijia.baseywmj.function.collect.base

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.widget.recycler.XRecyclerView
import kotlinx.android.synthetic.main.collect_list_frag.*

/**
 * Created by jamisonline on 2017/6/30.
 */
abstract open class CollectListFragment<T> : JBaseFragment(), CollectListContract.View, XRecyclerView.LoadingListener {

    var page: Int = Config.page

    val presenter: CollectListContract.Presenter by lazy { createCollectPresenter() }

    val listAdapter: CommonRecyclerAdapter<T> by lazy { createListAdapter() }

    abstract fun createListAdapter(): CommonRecyclerAdapter<T>

    abstract fun createCollectPresenter(): CollectListContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.collect_list_frag, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_empty_layout.text = emptyText()
        rv_content.run {

        }
    }

    abstract fun emptyText(): String

    override fun onResponse(data: List<*>) {
        if (ListUtil.isEmpty(data)) return
        if (page == Config.page)
            listAdapter.refresh(data as ArrayList<T>)
        else
            listAdapter.addRange(data as ArrayList<T>)
    }

    override fun showEmpty(empty: Boolean) {
        empty_layout.visibility = if (empty) View.VISIBLE else View.GONE
    }


    override fun cancelSucc(position: Int) {
        listAdapter.remove(position)
        checkCllectAdapterNotNull()
        page = listAdapter.itemCount / 10
        toastWith("取消收藏成功")
    }

    override fun onLoadComplete(page: Int, empty: Boolean) {
        if (page == Config.page) {
            rv_content.setIsnomore(false)
            rv_content.refreshComplete()
        } else {
            rv_content.loadMoreComplete()
            rv_content.setIsnomore(empty)
        }
    }

    private fun checkCllectAdapterNotNull() {
        if (listAdapter.itemCount === 0) {
            page = Config.page
            presenter.loadData(page, Config.size)
        }
    }

    override fun onRefresh() {
        page = Config.page
        presenter.loadData(page, Config.size)
    }

    override fun onLoadMore() {
        page++
        presenter.loadData(page, Config.size)
    }

    override fun showCancelDialog(targetId: Int, position: Int) {
        AlertDialog.Builder(activity)
                .setMessage("确定取消收藏?")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定") { dialog, which -> presenter.cancelCollect(targetId, position) }
                .show()
    }
}