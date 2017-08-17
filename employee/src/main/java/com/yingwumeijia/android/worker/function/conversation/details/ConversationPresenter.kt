package com.yingwumeijia.android.worker.function.conversation.details

import android.app.Activity
import android.view.View
import android.widget.Toast
import com.yingwumeijia.android.worker.R
import com.yingwumeijia.baseywmj.im.IMManager
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CommonLanguage
import com.yingwumeijia.baseywmj.entity.bean.SessionDetailBean
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.CallUtils
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder

/**
 * Created by jamisonline on 2017/7/14.
 */
class ConversationPresenter(var context: Activity, var sessionId: String, var view: ConversationControact.View) : ConversationControact.Presenter {


    val inputQuickAdapter by lazy { createInputQuickAdapter() }

    var sessionInfo: SessionDetailBean? = null


    override fun start() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getSessionInfo(sessionId), object : ProgressSubscriber<SessionDetailBean>(context) {
            override fun _onNext(t: SessionDetailBean?) {
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
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getEmployeeOpenPhoneList(sessionId), object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                if (t == null) return Unit
                CallUtils.callPhone(t!!, context)
            }
        })
    }

    /**
     * 删除一条快捷回复
     */
    override fun deleteInputQuick(id: Int, position: Int) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.deleteCommonLanguage(id), object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show()
                inputQuickAdapter.remove(position)
            }
        })
    }


    /**
     * 新增一条快捷回复
     */
    override fun insertInput(content: String) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.insertCommonLanguage(content), object : ProgressSubscriber<CommonLanguage>(context) {
            override fun _onNext(t: CommonLanguage?) {
                Toast.makeText(context, "操作成功", Toast.LENGTH_SHORT).show()
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
        IMManager.sendTextMessage(sessionId, message)
    }


    private fun createInputQuickAdapter(): CommonRecyclerAdapter<CommonLanguage> {
        val adapter = object : CommonRecyclerAdapter<CommonLanguage>(context, null, null, R.layout.item_conversation_quick_input) {
            override fun convert(holder: RecyclerViewHolder, commonLanguage: CommonLanguage, position: Int) {
                holder
                        .run {
                            setTextWith(R.id.tv_content, commonLanguage.content)
                            setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                                override fun itemClick(itemView: View, position: Int) {
                                    view.showQuickInputPop(false)
                                    sendWith(commonLanguage.content)
                                }

                            })
                            setOnItemLongClickListener(object : RecyclerViewHolder.OnItemLongCliceListener {
                                override fun itemLongClick(itemView: View, position: Int): Boolean {
                                    view.showDeleteInputQuickDialog(commonLanguage, position)
                                    return true
                                }
                            })
                        }
            }
        }
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getCommonLanguage(), object : ProgressSubscriber<List<CommonLanguage>>(context) {
            override fun _onNext(t: List<CommonLanguage>?) {
                if (ListUtil.isEmpty(t)) return Unit
                inputQuickAdapter.refresh(t as ArrayList<CommonLanguage>)
            }
        })
        return adapter
    }


}