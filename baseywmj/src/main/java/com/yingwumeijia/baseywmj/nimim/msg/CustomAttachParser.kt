package com.yingwumeijia.baseywmj.nimim.msg

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.netease.nimlib.sdk.msg.attachment.MsgAttachmentParser
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment


/**
 * Created by jamisonline on 2017/9/21.
 *
 * 实现自定义消息的附件解析器。
 */
class CustomAttachParser : MsgAttachmentParser {


    // 根据解析到的消息类型，确定附件对象类型
    override fun parse(json: String): MsgAttachment {
        var attachment: CustomAttachment? = null
        try {
            val ob = JSON.parseObject(json)
            val type = ob.getInteger(KEY_TYPE)
            val data = ob.getJSONObject(KEY_DATA)
            when (type) {
                CustomAttachmentType.PAY_MESSAGE -> attachment = PayMessageAttachment()
//                else -> attachment = DefaultCustomAttachment()
            }

            if (attachment != null) {
                attachment.fromJson(data)
            }
        } catch (e: Exception) {

        }

        return attachment as MsgAttachment
    }


    companion object {

        private val KEY_TYPE = "messageType"
        private val KEY_DATA = "messageContent"


        fun packData(type: Int, data: JSONObject?): String {
            val ob = JSONObject()
            ob.put(KEY_TYPE, type)
            if (data != null) {
                ob.put(KEY_DATA, data)
            }
            return ob.toJSONString()
        }
    }
}

