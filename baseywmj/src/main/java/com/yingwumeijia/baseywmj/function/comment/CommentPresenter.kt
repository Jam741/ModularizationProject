package com.yingwumeijia.baseywmj.function.comment

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CommentResultBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import com.yingwumeijia.commonlibrary.widget.SpannableTextView
import java.lang.Long
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jamisonline on 2017/7/25.
 */
class CommentPresenter(var context: Activity, var view: CommentContract.View, var caseId: Int) : CommentContract.Presenter {


    val commentAdapter by lazy {
        createCommentAdapter()
    }

    var mCurrentCommentId = 0

    var isRepaly = false


    private fun createCommentAdapter(): CommonRecyclerAdapter<CommentResultBean.ResultBean> {
        return object : CommonRecyclerAdapter<CommentResultBean.ResultBean>(context, null, null, R.layout.item_comment) {
            override fun convert(holder: RecyclerViewHolder, resultBean: CommentResultBean.ResultBean, position: Int) {
                JImageLolder.loadPortrait256(context, holder.getViewWith(R.id.iv_portrait) as ImageView, resultBean.userShowHead)
                holder.run {

                    setTextWith(R.id.tv_name, resultBean.userShowName)
                    setTextWith(R.id.tv_describe, resultBean.content)
                    setTextWith(R.id.tv_time, getInterval(resultBean.createTime))
                    setVisible(R.id.rv_reply, resultBean.replyList != null)
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                           if (!AppTypeManager.isAppC()){
                               mCurrentCommentId = resultBean.id
                               view.setInputHint("回复：" + resultBean.userShowName)
                               view.showEdittextbody(true)
                               isRepaly = true
                           }
//                            circleEt.setHint("回复：" + resultBean.userShowName)
//                            edittextbody.setVisibility(View.VISIBLE)
                        }

                    })
                }

                if (!ListUtil.isEmpty(resultBean.replyList)) {
                    val rvReplay = holder.getViewWith(R.id.rv_reply) as RecyclerView
                    rvReplay.layoutManager = LinearLayoutManager(context)
                    rvReplay.adapter = createReplayAdapter(resultBean.replyList)
                }
            }
        }
    }


    private fun createReplayAdapter(replyList: List<CommentResultBean.ResultBean.ReplyListBean>): CommonRecyclerAdapter<CommentResultBean.ResultBean.ReplyListBean> {
        return object : CommonRecyclerAdapter<CommentResultBean.ResultBean.ReplyListBean>(context, null, replyList as ArrayList<CommentResultBean.ResultBean.ReplyListBean>, R.layout.item_comment_replay) {
            override fun convert(holder: RecyclerViewHolder, replyListBean: CommentResultBean.ResultBean.ReplyListBean, position: Int) {
                val tvReplay = holder.getViewWith(R.id.tv_replay)
                createSpannableTextViewForReplay(tvReplay as SpannableTextView, replyListBean.employeeDetailTypeDesc + "·" + replyListBean.employeeShowName + ": ", replyListBean.content)
            }
        }
    }

    fun createSpannableTextViewForReplay(tv: SpannableTextView, title: String, content: String) {
        // clear pieces
        tv.reset()
        // Add the first piece
        tv.addPiece(SpannableTextView.Piece.Builder(title).textColor(context.resources.getColor(R.color.color_1)).style(Typeface.BOLD).textSize(context.resources.getDimension(R.dimen.font4_sp).toInt()).build())
        // Add the second piece
        tv.addPiece(SpannableTextView.Piece.Builder(content).textColor(context.resources.getColor(R.color.color_4)).style(Typeface.NORMAL).textSize(context.resources.getDimension(R.dimen.font4_sp).toInt()).build())
        // Display the final, styled text
        tv.display()
    }


    override fun getCommnetList(pageNum: Int) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getCommentData(caseId, pageNum, Config.size), object : SimpleSubscriber<CommentResultBean>(context) {
            override fun _onNext(t: CommentResultBean?) {
                if (t == null) return Unit
                view.run {
                    showEmpty(ListUtil.isEmpty(t.result) && pageNum == Config.page)
                    onLoadComplete(pageNum, ListUtil.isEmpty(t.result))
                    if (!ListUtil.isEmpty(t.result))
                        onResponse(t.result)
                }
            }
        })
    }

    override fun commnetAction(content: String) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.commnetAction(caseId, content), object : ProgressSubscriber<CommentResultBean.ResultBean>(context) {
            override fun _onNext(t: CommentResultBean.ResultBean?) {
                if (t == null) return Unit
                view.run {
                    commentActionSuss(t)
                }


            }
        })
    }


    override fun commentReplay(commentId: Int, content: String) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.commentReplay(commentId, content), object : ProgressSubscriber<CommentResultBean.ResultBean>(context) {
            override fun _onNext(t: CommentResultBean.ResultBean?) {
                if (t == null) return Unit
                view.run {
                    showReplaySucc(t)
                }
            }
        })
    }


    /**
     * 获取时间间隔

     * @param inputTime 传入的时间格式必须类似于“yyyy-MM-dd HH:mm:ss”这样的格式
     * *
     * @return
     */

    fun getInterval(inputTime: String): String {
        var inputTime = inputTime
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        inputTime = simpleDateFormat.format(Date(Long.valueOf(inputTime)!!))

        if (inputTime.length != 19) {

            return inputTime

        }

        var result: String

        try {

            val sd = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            val pos = ParsePosition(0)

            val d1 = sd.parse(inputTime, pos)

            // 用现在距离1970年的时间间隔new

            // Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔

            val time = Date().time - d1.time// 得出的时间间隔是毫秒

            if (time / 1000 <= 0) {

                // 如果时间间隔小于等于0秒则显示“刚刚”time/10得出的时间间隔的单位是秒

                result = "刚刚"

            } else if (time / 1000 < 60) {

                // 如果时间间隔小于60秒则显示多少秒前

                val se = (time % 60000 / 1000).toInt()

                result = se.toString() + "秒前"

            } else if (time / 60000 < 60) {

                // 如果时间间隔小于60分钟则显示多少分钟前

                val m = (time % 3600000 / 60000).toInt()// 得出的时间间隔的单位是分钟

                result = m.toString() + "分钟前"

            } else if (time / 3600000 < 24) {

                // 如果时间间隔小于24小时则显示多少小时前

                val h = (time / 3600000).toInt()// 得出的时间间隔的单位是小时

                result = h.toString() + "小时前"

            } else if (time / 86400000 < 30) {

                // 如果时间间隔小于30天则显示多少天前

                val sdf = SimpleDateFormat("MM月dd日 HH:mm")

                result = sdf.format(d1.time)

            } else if (time / 2592000000L < 12) {

                // 如果时间间隔小于12个月则显示多少月前

                val sdf = SimpleDateFormat("MM月dd日")

                result = sdf.format(d1.time)


            } else {

                //                // 大于1年，显示年月日时间

                val sdf = SimpleDateFormat("yyyy年MM月dd日")

                result = sdf.format(d1.time)

            }//            else if (time / 86400000 < 2) {
            //
            //                // 如果时间间隔小于2天则显示昨天
            //
            //                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            //
            //                result = sdf.format(d1.getTime());
            //
            //                result = "昨天" +
            //                        result;
            //
            //            } else if (time / 86400000 < 3) {
            //
            //                // 如果时间间隔小于3天则显示前天
            //
            //                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            //
            //                result = sdf.format(d1.getTime());
            //
            //                result = "前天" +
            //                        result;
            //
            //            }

        } catch (e: Exception) {

            return inputTime

        }

        return result

    }


}