package com.yingwumeijia.baseywmj.nimim.conversation.customer

import android.os.Handler
import com.netease.nimlib.sdk.msg.MessageBuilder
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.yingwumeijia.baseywmj.entity.bean.CommonLanguage
import com.yingwumeijia.baseywmj.entity.bean.SessionDetailBean
import com.yingwumeijia.baseywmj.im.IMManager

/**
 * Created by jamisonline on 2017/7/14.
 */
class ConversationPresenter(var context: android.app.Activity, var sessionId: String, var view: ConversationControact.View) : ConversationControact.Presenter {


    val inputQuickAdapter by lazy { createInputQuickAdapter() }

    var sessionInfo: com.yingwumeijia.baseywmj.entity.bean.SessionDetailBean? = null


    override fun start() {
        com.yingwumeijia.baseywmj.utils.net.HttpUtil.getInstance().toNolifeSubscribe(com.yingwumeijia.baseywmj.api.Api.Companion.service.getSessionInfoNIM(sessionId), object : com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber<SessionDetailBean>(context) {
            override fun _onNext(t: com.yingwumeijia.baseywmj.entity.bean.SessionDetailBean?) {
                sessionInfo = t
                if (t != null)
                    view.showConversationTitle(t.sessionInfo.name + "(" + t.sessionInfo.members.size + ")")
            }
        })
    }


    /**
     * 获取业者联系电话
     */
    override fun callContactPhone(sessionId: String) {
        com.yingwumeijia.baseywmj.utils.net.HttpUtil.getInstance().toNolifeSubscribe(com.yingwumeijia.baseywmj.api.Api.Companion.service.getEmployeeOpenPhoneList(sessionId), object : com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                if (t == null) return Unit
                com.yingwumeijia.commonlibrary.utils.CallUtils.callPhone(t!!, context)
            }
        })
    }

    /**
     * 删除一条快捷回复
     */
    override fun deleteInputQuick(id: Int, position: Int) {
        com.yingwumeijia.baseywmj.utils.net.HttpUtil.getInstance().toNolifeSubscribe(com.yingwumeijia.baseywmj.api.Api.Companion.service.deleteCommonLanguage(id), object : com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                android.widget.Toast.makeText(context, "操作成功", android.widget.Toast.LENGTH_SHORT).show()
                inputQuickAdapter.remove(position)
            }
        })
    }


    /**
     * 新增一条快捷回复
     */
    override fun insertInput(content: String) {
        com.yingwumeijia.baseywmj.utils.net.HttpUtil.getInstance().toNolifeSubscribe(com.yingwumeijia.baseywmj.api.Api.Companion.service.insertCommonLanguage(content), object : com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber<CommonLanguage>(context) {
            override fun _onNext(t: com.yingwumeijia.baseywmj.entity.bean.CommonLanguage?) {
                android.widget.Toast.makeText(context, "操作成功", android.widget.Toast.LENGTH_SHORT).show()
                if (t != null)
                    inputQuickAdapter.insert(t)
                view.showAddInputQuickDialog(false)
            }
        })
    }

    /**
     * 在当前回话下发送文本消息
     */
    override fun sendWith(message: String) {
        val msg = MessageBuilder.createTextMessage(sessionId, SessionTypeEnum.Team, message)
        view.sendMessage(msg)

    }


    private fun createInputQuickAdapter(): com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter<CommonLanguage> {
        val adapter = object : com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter<CommonLanguage>(context, null, null, com.yingwumeijia.baseywmj.R.layout.item_conversation_quick_input) {
            override fun convert(holder: com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder, commonLanguage: com.yingwumeijia.baseywmj.entity.bean.CommonLanguage, position: Int) {
                holder
                        .run {
                            setTextWith(com.yingwumeijia.baseywmj.R.id.tv_content, commonLanguage.content)
                            setOnItemClickListener(object : com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder.OnItemCliceListener {
                                override fun itemClick(itemView: android.view.View, position: Int) {
                                    view.showQuickInputPop(false)
                                    sendWith(commonLanguage.content)
                                }

                            })
                            setOnItemLongClickListener(object : com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder.OnItemLongCliceListener {
                                override fun itemLongClick(itemView: android.view.View, position: Int): Boolean {
                                    view.showDeleteInputQuickDialog(commonLanguage, position)
                                    return true
                                }
                            })
                        }
            }
        }
        com.yingwumeijia.baseywmj.utils.net.HttpUtil.getInstance().toNolifeSubscribe(com.yingwumeijia.baseywmj.api.Api.Companion.service.getCommonLanguage(), object : com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber<List<CommonLanguage>>(context) {
            override fun _onNext(t: List<com.yingwumeijia.baseywmj.entity.bean.CommonLanguage>?) {
                if (com.yingwumeijia.commonlibrary.utils.ListUtil.isEmpty(t)) return Unit
                inputQuickAdapter.refresh(t as ArrayList<com.yingwumeijia.baseywmj.entity.bean.CommonLanguage>)
            }
        })
        return adapter
    }


}