package com.yingwumeijia.baseywmj.function.comment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.InputFilter
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.CommentResultBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.widget.recycler.XRecyclerView
import kotlinx.android.synthetic.main.comment_act.*
import kotlinx.android.synthetic.main.edit_view.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/25.
 */
class CommentActivity : JBaseActivity(), CommentContract.View, XRecyclerView.LoadingListener {


    val caseId by lazy { intent.getIntExtra(Constant.KEY_CASE_DETAIL_ID, Constant.DEFAULT_INT_VALUE) }

    val presenter by lazy { CommentPresenter(context, this, caseId) }

    var page = Config.page


    companion object {
        fun start(context: Context, caseId: Int) {
            val starter = Intent(context, CommentActivity::class.java)
            starter.putExtra(Constant.KEY_CASE_DETAIL_ID, caseId)
            context.startActivity(starter)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comment_act)
        topTitle.text = "所有评论"
        topLeft.setOnClickListener { close() }
        rv_comment.run {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.commentAdapter
            setLoadingListener(this@CommentActivity)
        }

        circleEt.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(500))

        sendIv.setOnClickListener {
            if (UserManager.precedent(context))
                if (!TextUtils.isEmpty(circleEt.text.toString().trim())) {
                    val str = circleEt.text.toString()
                    presenter.commnetAction(trimInnerSpaceStr(str))
                    circleEt.setText("")
                    hideSoftInput(sendIv)
                }
        }
    }

    override fun showEmpty(empty: Boolean) {
        empty_layout.visibility = if (empty) View.VISIBLE else View.GONE
    }

    override fun onResponse(list: List<*>) {
        if (page == Config.page) {
            presenter.commentAdapter.refresh(list as ArrayList<CommentResultBean.ResultBean>)
        } else {
            presenter.commentAdapter.addRange(list as ArrayList<CommentResultBean.ResultBean>)
        }
    }

    override fun onLoadComplete(page: Int, empty: Boolean) {
        if (page == Config.page) {
            rv_comment.setIsnomore(false)
            rv_comment.refreshComplete()
        } else {
            rv_comment.loadMoreComplete()
            rv_comment.setIsnomore(empty)
        }
    }

    override fun commentActionSuss(resultBean: CommentResultBean.ResultBean) {
        showEmpty(false)
        onRefresh()
    }

    override fun showReplaySucc(resultBean: CommentResultBean.ResultBean) {
        toastWith("回复成功")
        onRefresh()
    }

    override fun onRefresh() {
        page = Config.page
        presenter.getCommnetList(page)
    }

    override fun onLoadMore() {
        page++
        presenter.getCommnetList(page)
    }


    /***
     * 去掉字符串前后的空间，中间的空格保留

     * @param str
     * *
     * @return
     */
    fun trimInnerSpaceStr(str: String): String {
        var str = str
        str = str.trim { it <= ' ' }
        while (str.startsWith(" ")) {
            str = str.substring(1, str.length).trim { it <= ' ' }
        }
        while (str.endsWith(" ")) {
            str = str.substring(0, str.length - 1).trim { it <= ' ' }
        }
        return str
    }

}