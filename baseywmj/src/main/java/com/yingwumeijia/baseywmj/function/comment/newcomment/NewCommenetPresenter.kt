package com.yingwumeijia.baseywmj.function.comment.newcomment


import android.content.Context
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CommentResultBean
import com.yingwumeijia.baseywmj.function.comment.CommentContract
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil

/**
 * Created by Jam on 2017/3/2 下午7:21.
 * Describe:
 */

class NewCommenetPresenter(private val context: Context, private val mView: CommentContract.View) : CommentContract.Presenter {


    override fun getCommnetList(pageNum: Int) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getCommentAllData(pageNum, Config.page), object : SimpleSubscriber<CommentResultBean>(context) {
            override fun _onNext(t: CommentResultBean?) {
                if (t == null) return Unit
                mView.showEmpty(ListUtil.isEmpty(t.result) && pageNum == Config.page)
                mView.onLoadComplete(pageNum, ListUtil.isEmpty(t.result))
                if (t.result != null)
                    mView.onResponse(t.result)
            }

        })
    }

    override fun commnetAction(content: String) {

    }

    override fun commentReplay(commentId: Int, content: String) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.commentReplay(commentId, content), object : ProgressSubscriber<CommentResultBean.ResultBean>(context) {
            override fun _onNext(t: CommentResultBean.ResultBean?) {
                if (t == null) return
                mView.showReplaySucc(t)
            }

        })
    }

}


