package com.yingwumeijia.android.worker.function.conversation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.netease.nim.uikit.recent.RecentContactsCallback
import com.netease.nim.uikit.recent.RecentContactsFragment
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.netease.nimlib.sdk.msg.model.RecentContact
import com.yingwumeijia.android.worker.R
import com.yingwumeijia.android.worker.function.collectunread.CollectUnreadActivity
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.UnreadBean
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.comment.newcomment.NewCommentActivity
import com.yingwumeijia.baseywmj.function.im.JBaseConversationListFragment
import com.yingwumeijia.baseywmj.function.message.MessageActivity
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.RetrofitUtil
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import kotlinx.android.synthetic.main.conversation_list_container.*
import kotlinx.android.synthetic.main.person_title_layout.*
import rx.Subscriber

/**
 * Created by jamisonline on 2017/8/2.
 */
class EmployeeConversationFragment : JBaseConversationListFragment() {


    companion object {
        fun newInstance(): EmployeeConversationFragment {
            return EmployeeConversationFragment()
        }
    }


    val systenMessageReceive = SystenMessageReceive()


    val loggedFragment by lazy { assembleRecentContactsFragment() }

    val systemMessageAdapter by lazy { createSystemMessageAdapter() }


//    internal val conversationTypeSystem = arrayOf(Conversation.ConversationType.SYSTEM)


    private fun assembleRecentContactsFragment(): RecentContactsFragment {
        val recentContactsFragment = RecentContactsFragment()
        recentContactsFragment.setCallback(contactssCallback())
        return recentContactsFragment
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.conversation_list_container, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topTitle.text = "聊天"

        registerSystemMessafeReceive()

        rv_message.run {
            layoutManager = LinearLayoutManager(context)
            adapter = systemMessageAdapter
        }


        getUnRead()
//
//        RongIM.getInstance()
//                .addUnReadMessageCountChangedObserver(unread, *conversationTypeSystem)


        if (MessageActivity.isUnRead(getContext())) {
            systemMessageAdapter.data!![2].content = "有新的消息"
            systemMessageAdapter.notifyDataSetChanged()
        }
    }


//    val unread = object : IUnReadMessageObserver {
//        override fun onCountChanged(p0: Int) {
//            if (p0 > 0) {
//                systemMessageAdapter.data!![2].content = "有新的消息"
//            } else {
//                systemMessageAdapter.data!![2].content = "暂时没有新消息"
//            }
//            systemMessageAdapter.notifyDataSetChanged()
//        }
//    }


    private fun createSystemMessageAdapter(): CommonRecyclerAdapter<SystemMessageInfo> {

        val list = ArrayList<SystemMessageInfo>()

        list.add(SystemMessageInfo(R.mipmap.news_list_chat_ic, "评论", SystemMessageInfo.MessageType.COMMENT))
        list.add(SystemMessageInfo(R.mipmap.news_list_collect_ic, "收藏", SystemMessageInfo.MessageType.COLLECT))
        list.add(SystemMessageInfo(R.mipmap.news_list_system_ic, "系统消息", SystemMessageInfo.MessageType.SYSTEM, "暂时没有新的消息"))

        return object : CommonRecyclerAdapter<SystemMessageInfo>(null, context, list, R.layout.item_system_message) {
            override fun convert(holder: RecyclerViewHolder, bean: SystemMessageInfo, position: Int) {
                holder
                        .run {
                            setImageResource(R.id.iv_icon, bean.getIcon())
                            setTextWith(R.id.tv_title, bean.getTitle())
                            setTextWith(R.id.tv_content, bean.getContent())
                            setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                                override fun itemClick(itemView: View, position: Int) {
                                    when (bean.getType()) {
                                        SystemMessageInfo.MessageType.COMMENT -> NewCommentActivity.start(getContext())
                                        SystemMessageInfo.MessageType.COLLECT -> CollectUnreadActivity.start(getContext())
                                        SystemMessageInfo.MessageType.SYSTEM -> {
//                                            RongIM.getInstance().removeUnReadMessageCountChangedObserver(unread)
                                            MessageActivity.setUnReader(getContext(), false)
                                            systemMessageAdapter.data!![2].content = "暂时没有新消息"
                                            systemMessageAdapter.notifyDataSetChanged()
                                            MessageActivity.start(getContext())
                                        }
                                    }
                                }
                            })

                        }
            }
        }
    }


    override fun onDestroyView() {
        unRegisterSystemMessafeReceive()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        putFragment(loggedFragment)
    }

    private fun putFragment(fragment: Fragment) {
        val currentFragment = childFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            childFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        } else if (currentFragment != fragment) {
            childFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }
    }


    fun getUnRead() {

        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getUnreadBean(), object : Subscriber<UnreadBean>() {
            override fun onNext(t: UnreadBean?) {
                if (t!!.getCommentNum() === 0)
                    systemMessageAdapter.data!![0].setContent("暂没有新的消息")
                else
                    systemMessageAdapter.data!![0].setContent("最近有" + t!!.getCommentNum() + "名业主评论了你的作品")


                if (t!!.getCollectionNum() === 0)
                    systemMessageAdapter.data!![1].setContent("暂没有新的消息")
                else
                    systemMessageAdapter.data!![1].setContent("最近有" + t!!.getCollectionNum() + "名业主收藏了你的作品")

                systemMessageAdapter.notifyDataSetChanged()
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
            }

        })

    }


    /**
     * 会话列表事件回调
     */
    inner class contactssCallback : RecentContactsCallback {
        override fun onRecentContactsLoaded() {
            // 最近联系人列表加载完毕
        }

        override fun onUnreadCountChange(unreadCount: Int) {
            // 未读数发生变化
        }

        override fun onItemClick(recent: RecentContact?) {
            when (recent!!.sessionType) {
                SessionTypeEnum.Team -> StartActivityManager.startConversation(getContext(), recent.contactId)
            }
        }

        override fun getDigestOfAttachment(recent: RecentContact?, attachment: MsgAttachment?): String? {
            // 设置自定义消息的摘要消息，展示在最近联系人列表的消息缩略栏上
            // 当然，你也可以自定义一些内建消息的缩略语，例如图片，语音，音视频会话等，自定义的缩略语会被优先使用。
            return null
        }

        override fun getDigestOfTipMsg(recent: RecentContact?): String? {
            val msgId = recent!!.recentMessageId
            val uuids = ArrayList<String>()
            uuids.add(msgId)
            val msgs = NIMClient.getService(MsgService::class.java).queryMessageListByUuidBlock(uuids)
            if (msgs != null && !msgs.isEmpty()) {
                val msg = msgs[0]
                val content = msg.remoteExtension
                if (content != null && !content.isEmpty()) {
                    return content["content"] as String
                }
            }
            return null
        }

    }


    inner class SystenMessageReceive : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            systemMessageAdapter.data!![2].content = "有新的消息"
        }

    }


    fun registerSystemMessafeReceive() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constant.SYSTEM_MSG_RECEIVE_ACTION_E)
        getContext().registerReceiver(systenMessageReceive, intentFilter)
    }


    fun unRegisterSystemMessafeReceive() {
        getContext().unregisterReceiver(systenMessageReceive)
    }
}