package com.yingwumeijia.android.ywmj.client.function.coupon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.CouponListResponseBean
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.widget.recycler.LoadingMoreFooter
import com.yingwumeijia.commonlibrary.widget.recycler.XRecyclerView
import kotlinx.android.synthetic.main.coupon_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/4.
 */
class CouponActivity : JBaseActivity(), CouponContract.View, XRecyclerView.LoadingListener {


    val available by lazy { intent.getBooleanExtra(Constant.KEY_AVAILABLE, false) }

    val presenter by lazy { CouponPresenter(context, this, available) }

    var page = Config.page

    companion object {

        fun start(context: Context, available: Boolean) {
            val starter = Intent(context, CouponActivity::class.java)
            starter.putExtra(Constant.KEY_AVAILABLE, available)
            context.startActivity(starter)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coupon_act)
        topTitle.text = "我的优惠"
        if (available) btn_checkPast.visibility = View.VISIBLE else btn_checkPast.visibility = View.GONE


        rv_content.run {
            layoutManager = LinearLayoutManager(context)
            addFootView(LoadingMoreFooter(context, "没有更多了"))
            adapter = presenter.couponAdapter
            setLoadingListener(this@CouponActivity)
        }

        btn_explain.visibility = if (available) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    private fun loadData() {
        presenter.getCouponList(available, page, Config.size)
    }

    override fun onResonpse(data: List<CouponListResponseBean.ResultBean>) {
        if (page == Config.page)
            presenter.couponAdapter.refresh(data as ArrayList<CouponListResponseBean.ResultBean>)
        else
            presenter.couponAdapter.addRange(data as ArrayList<CouponListResponseBean.ResultBean>)
    }

    override fun showEmptyLayout(empty: Boolean) {
        empty_layout.visibility = if (empty) View.VISIBLE else View.GONE
        rv_content.visibility = if (!empty) View.VISIBLE else View.GONE
        btn_explain.visibility = if (available) View.VISIBLE else View.GONE
    }

    override fun onLoadComplete(page: Int, empty: Boolean) {
        if (page == Config.page) {
            showEmptyLayout(empty)
            rv_content.setIsnomore(false)
            rv_content.refreshComplete()
        } else {
            rv_content.loadMoreComplete()
            rv_content.setIsnomore(empty)
        }
    }


    override fun onRefresh() {
        page = Config.page
        loadData()
    }

    override fun onLoadMore() {
        page++
        loadData()
    }

}