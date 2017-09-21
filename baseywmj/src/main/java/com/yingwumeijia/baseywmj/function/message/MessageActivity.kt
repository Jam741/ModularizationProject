package com.yingwumeijia.baseywmj.function.message

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.WebViewManager
import com.yingwumeijia.baseywmj.function.db.DBManager
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.adapter.CommonAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.ViewHolder
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import com.yingwumeijia.commonlibrary.widget.recycler.LoadingMoreFooter
import com.yingwumeijia.commonlibrary.widget.recycler.XRecyclerView
import kotlinx.android.synthetic.main.message_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jamisonline on 2017/7/5.
 */
class MessageActivity : JBaseActivity(), XRecyclerView.LoadingListener {
    override fun onRefresh() {
        rv_conten.loadMoreComplete()
        page_num = 0
        refreshData()
    }

    override fun onLoadMore() {
        page_num++
        loadMoreData()
    }

    val MSG_TYPE_COMMON = 1
    val MSG_TYPE_CONVERSATION = 2
    val MSG_TYPE_APPOINTMENT = 3
    val MSG_TYPE_TWITTER = 4
    val MSG_TYPE_ORDER = 5


    val RECEIVE_MSG = 1
    private var mRecivedMessageBean: MessageBean? = null


    var page = Config.page

    internal var page_num = 0
    internal var page_size = 20
    internal var lastPosition = 0

    internal var messageBeanList = ArrayList<MessageBean>()

    val messageAdapter by lazy { createMessageAdapter() }
    private val allData: List<MessageBean>? by lazy { MessageManager.getMessageList(context) }

    val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                RECEIVE_MSG ->
                    //TODO ：处理当前广播接收道德消息
                    if (mRecivedMessageBean != null)
                        insertMessage(mRecivedMessageBean!!)
            }
        }
    }

    companion object {


        val ACTION_MESSAGE = "com.yingwumeijia.client.message"
        val KEY_MESSAGE = "KEY_MESSAGE"

        fun start(context: Context) {
            val starter = Intent(context, MessageActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_act)
        topTitle.text = "消息"
        registerBoradcastReceiver()


        rv_conten.run {
            layoutManager = LinearLayoutManager(context)
            setLoadingListener(this@MessageActivity)
            addFootView(LoadingMoreFooter(context, "没有更多了"))
            refreshComplete()
            loadMoreComplete()
            adapter = messageAdapter
        }
        topLeft.setOnClickListener { close() }

        if (ListUtil.isEmpty(allData)) {
            showEmptyLayout(true)
        } else {
            refreshData()
        }
    }


//    private fun getData(isRefresh: Boolean) {
//
//        Logger.d(allData!!.size)
//        if (ListUtil.isEmpty(allData)) {
//            showEmptyLayout(true)
//            return
//        }
//
//        if (isRefresh) {
//            lastPosition = 0
//        }
//
//        messageBeanList.clear()
//
//
//        if (allData!!.size <= page_size) {
//            messageBeanList.addAll(allData!!)
//        } else {
//            for (i in 0..page_size - 1) {
//                if (lastPosition < allData!!.size) {
//                    messageBeanList.add(allData!!.get(lastPosition))
//                    lastPosition++
//                }
//            }
//        }
//
//
//        if (isRefresh) {
//            showEmptyLayout(messageBeanList.size == 0)
//            messageAdapter.refresh(messageBeanList as ArrayList<MessageBean>)
//            rv_conten.setIsnomore(false)
//            rv_conten.refreshComplete()
//        } else {
//            rv_conten.loadMoreComplete()
//            rv_conten.setIsnomore(messageBeanList.size == 0)
//            messageAdapter.addRange(messageBeanList as ArrayList<MessageBean>)
//        }
//
//    }


    private fun refreshData() {
        if (ListUtil.isEmpty(allData)) {
            showEmptyLayout(true)
            return
        }
        messageBeanList.clear()
        lastPosition = 0
        if (allData!!.size > page_size) {
            for (i in 0..9) {
                messageBeanList.add(allData!!.get(lastPosition))
                lastPosition++
            }
        } else {
            messageBeanList = allData as ArrayList<MessageBean>
        }

        showEmptyLayout(messageBeanList.size == 0)
        messageAdapter.refresh(messageBeanList as ArrayList<MessageBean>)
        rv_conten.setIsnomore(false)
        rv_conten.refreshComplete()
    }


    private fun loadMoreData() {
        messageBeanList.clear()
        if (allData!!.size > page_size) {
            for (i in 0..9) {
                if (allData!!.get(lastPosition) != null) {
                    messageBeanList.add(allData!!.get(lastPosition))
                    lastPosition++
                }
            }
        }
        rv_conten.loadMoreComplete()
        rv_conten.setIsnomore(messageBeanList.size == 0)
        messageAdapter.addRange(messageBeanList)
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
                        btn.setOnClickListener { StartActivityManager.startConversation(context, messageBean.messageTargetId) }
                    }
                    MSG_TYPE_APPOINTMENT -> {
                        btn.visibility = View.VISIBLE
                        imageView.setImageResource(R.mipmap.notice_list_notice_ico)
                        btn.text = "查看详情"
                        btn.setOnClickListener { internalMessage(messageBean.getMessageId()) }
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

    internal var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == ACTION_MESSAGE) {
                mRecivedMessageBean = intent.getSerializableExtra(KEY_MESSAGE) as MessageBean
                mHandler.sendEmptyMessage(RECEIVE_MSG)
            }
        }
    }

    /**
     * 插入一条消息到裂变

     * @param mCurrentMessageBean
     */
    private fun insertMessage(mCurrentMessageBean: MessageBean) {
        if (messageAdapter != null) {
            messageAdapter.insert(messageAdapter.itemCount, mCurrentMessageBean)
//            if (RongIM.getInstance() != null) {
//                RongIM.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.SYSTEM, Constant.SYSTEM_TARGET_ID, object : RongIMClient.ResultCallback<Boolean>() {
//                    override fun onSuccess(aBoolean: Boolean?) {
//
//                    }
//
//                    override fun onError(errorCode: RongIMClient.ErrorCode) {
//
//                    }
//                })
//            }
        }
    }


    fun registerBoradcastReceiver() {
        val myIntentFilter = IntentFilter()
        myIntentFilter.addAction(ACTION_MESSAGE)
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter)
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadcastReceiver)
    }
}