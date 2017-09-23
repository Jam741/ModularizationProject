package com.yingwumeijia.baseywmj.nimim.msg

import android.util.Log
import com.alibaba.fastjson.JSONObject

/**
 * Created by jamisonline on 2017/9/21.
 */
class PayMessageAttachment : CustomAttachment(CustomAttachmentType.PAY_MESSAGE) {


    var title: String = ""
    var billId: Int = 0
    var billAmount: Int = 0
    var billTypeStr: String = ""

    override fun parseData(data: JSONObject) {
        Log.d("PayMessageAttachment", data.toJSONString())
        title = data.getString("title")
        billId = data.getInteger("billId")
        billAmount = data.getInteger("billAmount")
        billTypeStr = data.getString("billTypeStr")
    }

    override fun packData(): JSONObject {
        val ob = JSONObject()
        ob.put("title", title)
        ob.put("billId", billId)
        ob.put("billAmount", billAmount)
        ob.put("billTypeStr", billTypeStr)
        return ob
    }
}