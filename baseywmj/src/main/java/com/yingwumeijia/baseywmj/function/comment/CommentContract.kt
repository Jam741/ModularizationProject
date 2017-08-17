package com.yingwumeijia.baseywmj.function.comment

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.CommentResultBean

/**
 * Created by jamisonline on 2017/7/25.
 */
interface CommentContract {

    interface View : JBaseView {

        fun showEmpty(empty: Boolean)

        fun onResponse(list: List<*>)

        fun onLoadComplete(page: Int, empty: Boolean)

        fun commentActionSuss(resultBean: CommentResultBean.ResultBean)

        fun showReplaySucc(resultBean: CommentResultBean.ResultBean)
    }

    interface Presenter : JBasePresenter {

        fun getCommnetList(pageNum: Int)

        fun commnetAction(content: String)

        fun commentReplay(commentId: Int, content: String)

    }
}