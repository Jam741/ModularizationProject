package com.yingwumeijia.baseywmj.nimim.msg

import com.alibaba.fastjson.JSONObject
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment

/**
 * Created by jamisonline on 2017/9/21.
 *
 *  先定义一个自定义消息附件的基类，负责解析你的自定义消息的公用字段，比如类型等等
 */
abstract class CustomAttachment(val messageType: Int) : MsgAttachment {

    // 解析附件内容。
    fun fromJson(data: JSONObject?) {
        if (data != null) {
            parseData(data)
        }
    }

    // 实现 MsgAttachment 的接口，封装公用字段，然后调用子类的封装函数。
    override fun toJson(send: Boolean): String {
        return CustomAttachParser.packData(messageType, packData())
    }

    // 子类的解析和封装接口。
    protected abstract fun parseData(data: JSONObject)

    protected abstract fun packData(): JSONObject
}