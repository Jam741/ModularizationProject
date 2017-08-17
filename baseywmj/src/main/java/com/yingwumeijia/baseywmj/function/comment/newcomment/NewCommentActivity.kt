package com.yingwumeijia.baseywmj.function.comment.newcomment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.CommentResultBean
import com.yingwumeijia.baseywmj.function.comment.CommentContract
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import com.yingwumeijia.commonlibrary.widget.SpannableTextView
import com.yingwumeijia.commonlibrary.widget.recycler.LoadingMoreFooter
import com.yingwumeijia.commonlibrary.widget.recycler.XRecyclerView
import kotlinx.android.synthetic.main.comment_act.*
import kotlinx.android.synthetic.main.edit_view.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/8/3.
 */
class NewCommentActivity : JBaseActivity(), CommentContract.View, XRecyclerView.LoadingListener {

    val presenter by lazy { NewCommenetPresenter(context, this) }

    val commentAdapter by lazy { createCommentAdapter() }

    var mCurrentCommentId = 0

    var pageNum = Config.page

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, NewCommentActivity::class.java)
            context.startActivity(starter)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comment_act)

        topTitle.text = "评论"

        circleEt.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(500))
        rv_comment.run {
            addFootView(LoadingMoreFooter(context, Config.NO_MORE_DATA))
            layoutManager = LinearLayoutManager(context)
            setLoadingListener(this@NewCommentActivity)
            adapter = commentAdapter
            setOnTouchListener { v, event ->
                if (editTextBodyLl.getVisibility() == View.VISIBLE) {
                    return@setOnTouchListener true
                }
                return@setOnTouchListener false
            }
        }

        loadList()

        topLeft.setOnClickListener { close() }
        sendIv.setOnClickListener {
            if (!TextUtils.isEmpty(circleEt.text.toString().trim())) {
                presenter.commentReplay(mCurrentCommentId, trimInnerSpaceStr(circleEt.text.toString()))
                hideSoftInput(editTextBodyLl)
            }
        }
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

    fun loadList() {
        presenter.getCommnetList(pageNum)
    }

    private fun createCommentAdapter(): CommonRecyclerAdapter<CommentResultBean.ResultBean> {
        return object : CommonRecyclerAdapter<CommentResultBean.ResultBean>(context, null, null, R.layout.item_comment_new) {
            override fun convert(holder: RecyclerViewHolder, resultBean: CommentResultBean.ResultBean, position: Int) {
                JImageLolder.loadPortrait256(context, holder.getViewWith(R.id.iv_portrait) as ImageView, resultBean.userShowHead)
                holder.run {
                    setTextWith(R.id.tv_name, resultBean.userShowName)
                    setTextWith(R.id.tv_describe, resultBean.content)
                    setTextWith(R.id.tv_caseName, "评论了你的作品-" + resultBean.caseName)
                    setVisible(R.id.rv_reply, resultBean.replyList != null)
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            mCurrentCommentId = resultBean.id
                            circleEt.hint = "回复：" + resultBean.userShowName
                            editTextBodyLl.visibility = View.VISIBLE
                        }
                    })
                }



                if (resultBean.replyList != null) {
                    val rvReplay = holder.getViewWith(R.id.rv_reply) as RecyclerView
                    rvReplay.run {
                        layoutManager = LinearLayoutManager(context)
                        adapter = createReplayAdapter(resultBean.replyList as ArrayList<CommentResultBean.ResultBean.ReplyListBean>)
                    }
                }
            }
        }
    }

    private fun createReplayAdapter(replyList: ArrayList<CommentResultBean.ResultBean.ReplyListBean>): CommonRecyclerAdapter<CommentResultBean.ResultBean.ReplyListBean> {
        return object : CommonRecyclerAdapter<CommentResultBean.ResultBean.ReplyListBean>(context, null, replyList, R.layout.item_comment_replay) {
            override fun convert(holder: RecyclerViewHolder, replyListBean: CommentResultBean.ResultBean.ReplyListBean, position: Int) {
                val tvReplay = holder.getViewWith(R.id.tv_replay) as SpannableTextView
                createSpannableTextViewForReplay(tvReplay, replyListBean.employeeDetailTypeDesc + "·" + replyListBean.employeeShowName + ": ", replyListBean.content)
            }
        }
    }

    fun createSpannableTextViewForReplay(tv: SpannableTextView, title: String, content: String) {

        // clear pieces
        tv.reset()
        // Add the first piece
        tv.addPiece(SpannableTextView.Piece.Builder(title).textColor(resources.getColor(R.color.color_1))
                .textSize(resources.getDimension(R.dimen.font4_sp).toInt()).build())

        // Add the second piece
        tv.addPiece(SpannableTextView.Piece.Builder(content).textColor(resources.getColor(R.color.color_4))
                .textSize(resources.getDimension(R.dimen.font4_sp).toInt()).build())

        // Display the final, styled text
        tv.display()
    }


    override fun showEmpty(empty: Boolean) {
        empty_layout.visibility = if (empty) View.VISIBLE else View.GONE
    }

    override fun onResponse(list: List<*>) {
        if (pageNum == Config.page) {
            commentAdapter.refresh(list as ArrayList<CommentResultBean.ResultBean>)
        } else {
            commentAdapter.addRange(list as ArrayList<CommentResultBean.ResultBean>)
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
        editTextBodyLl.visibility = View.GONE
    }

    override fun onRefresh() {
        pageNum = Config.page
        loadList()
    }

    override fun onLoadMore() {
        pageNum++
        loadList()
    }
}