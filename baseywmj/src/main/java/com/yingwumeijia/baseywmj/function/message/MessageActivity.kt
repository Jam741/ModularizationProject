package com.yingwumeijia.baseywmj.function.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.function.WebViewManager
import com.yingwumeijia.baseywmj.function.db.DBManager
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.adapter.CommonAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.ViewHolder
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import kotlinx.android.synthetic.main.message_act.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jamisonline on 2017/7/5.
 */
class MessageActivity : JBaseActivity() {

    val MSG_TYPE_COMMON = 1
    val MSG_TYPE_CONVERSATION = 2
    val MSG_TYPE_APPOINTMENT = 3
    val MSG_TYPE_TWITTER = 4
    val MSG_TYPE_ORDER = 5

    var page = Config.page

    val messageAdapter by lazy { createMessageAdapter() }


    companion object{
        fun start(context: Context) {
            val starter = Intent(context, MessageActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_act)
    }


    private fun createMessageAdapter(): CommonRecyclerAdapter<MessageBean> {
        return object : CommonRecyclerAdapter<MessageBean>(context, null, null, R.layout.item_message_normal) {
            override fun convert(holder: RecyclerViewHolder, messageBean: MessageBean, position: Int) {
                val btn = holder.getViewWith(R.id.btn_msgBottom) as TextView
                val imageView = holder.getViewWith(R.id.iv_msgType) as ImageView
                holder.run {
                    setTextWith(R.id.tv_msgDate, fromartDate(java.lang.Long.valueOf(messageBean.getMessageSendDate())))
                    setTextWith(R.id.tv_msgTitle, messageBean.getMessageTitle())
                    setTextWith(R.id.tv_msgContent, messageBean.getMessageContetnt())
                    setOnItemLongClickListener(object : RecyclerViewHolder.OnItemLongCliceListener {
                        override fun itemLongClick(itemView: View, position: Int): Boolean {
                            showItemMenuDialog(messageBean, position)
                            return true
                        }
                    })

                }

                when (Integer.valueOf(messageBean.getMessageType())) {
                    MSG_TYPE_COMMON -> {
                        imageView.setImageResource(R.mipmap.notice_list_notice_ico)
                        btn.visibility = View.GONE
                    }
                    MSG_TYPE_CONVERSATION -> {
                        imageView.setImageResource(R.mipmap.notice_list_talk_ico)
                        btn.visibility = View.VISIBLE
                        btn.text = "查看聊天"
                        btn.setOnClickListener { TODO("去回话详情") }
                    }
                    MSG_TYPE_TWITTER -> {
                        btn.visibility = View.VISIBLE
                        imageView.setImageResource(R.mipmap.notice_list_notice_ico)
                        btn.text = "查看详情"
                        btn.setOnClickListener { internalMessage(messageBean.getMessageId()) }
                    }
                    MSG_TYPE_ORDER -> {
                        btn.visibility = View.VISIBLE
                        imageView.setImageResource(R.mipmap.notice_list_order_ico)
                        btn.text = "查看详情"
                        btn.setOnClickListener { internalMessage(messageBean.getMessageId()) }
                    }
                }
            }
        }
    }


    private fun internalMessage(messageId: Int) {

        HttpUtil.getInstance().toNolifeSubscribe(Api.service.internalMessage(messageId), object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                if (TextUtils.isEmpty(t)) return Unit
                WebViewManager.startFullScreen(context, t!!)
            }

        })
    }


    /**
     * 显示Item长按菜单

     * @param position
     */
    private fun showItemMenuDialog(messageBean: MessageBean, position: Int) {
        val choiceItems = arrayOf("删除") as ArrayList<String>

        val adapter = object : CommonAdapter<String>(context, choiceItems, R.layout.item_session_member) {
            override fun conver(helper: ViewHolder?, item: String?, position: Int) {
                helper!!.setText(R.id.tv_title, item)
            }

        }
        AlertDialog.Builder(context)
                .setAdapter(adapter, { dialog, which ->
                    when (which) {
                        0 -> {
                            messageAdapter.remove(position)
                            showEmptyLayout(messageAdapter.itemCount === 0)
                            val dbManager = DBManager(context)
                            dbManager.deleteMessage(messageBean)
                        }
                    }
                })
                .show()

    }


    private fun fromartDate(times: Long): String {
        val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm")
        val date = sdf.format(Date(times))
        return date
    }


    private fun showEmptyLayout(empty: Boolean) {
        empty_layout.visibility = if (empty) View.VISIBLE else View.GONE
    }
}