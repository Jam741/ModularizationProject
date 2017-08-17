package com.yingwumeijia.android.worker.function.conversation

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.android.worker.R
import com.yingwumeijia.android.worker.function.collectunread.CollectUnreadActivity
import com.yingwumeijia.baseywmj.function.comment.newcomment.NewCommentActivity
import com.yingwumeijia.baseywmj.function.im.JBaseConversationListFragment
import com.yingwumeijia.baseywmj.function.message.MessageActivity
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import io.rong.imkit.fragment.ConversationListFragment
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.conversation_list_container.*

/**
 * Created by jamisonline on 2017/8/2.
 */
class EmployeeConversationFragment : JBaseConversationListFragment() {


    companion object {
        fun newInstance(): EmployeeConversationFragment {
            return EmployeeConversationFragment()
        }
    }


    val loggedFragment by lazy { createConversationListFragment() }

    val systemMessageAdapter by lazy { createSystemMessageAdapter() }

    /**
     * 创建会话列表Fragment
     */
    private fun createConversationListFragment(): Fragment {
        val fragment = ConversationListFragment()
        fragment.uri = Uri.parse("rong://" + activity.applicationInfo.packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话聚合显示
                .build()
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.conversation_list_container, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topTitle.text = "聊天"

        rv_message.run {
            layoutManager = LinearLayoutManager(context)
            adapter = systemMessageAdapter
        }
    }


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
                                        SystemMessageInfo.MessageType.SYSTEM -> MessageActivity.start(getContext())
                                    }
                                }
                            })

                        }
            }
        }
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
}