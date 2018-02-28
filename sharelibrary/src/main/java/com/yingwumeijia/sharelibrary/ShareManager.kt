package com.yingwumeijia.sharelibrary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.sina.weibo.sdk.api.TextObject
import com.sina.weibo.sdk.api.WebpageObject
import com.sina.weibo.sdk.api.WeiboMultiMessage
import com.sina.weibo.sdk.share.WbShareCallback
import com.sina.weibo.sdk.share.WbShareHandler
import com.sina.weibo.sdk.utils.Utility
import com.tencent.mm.sdk.modelmsg.SendMessageToWX
import com.tencent.mm.sdk.modelmsg.WXMediaMessage
import com.tencent.mm.sdk.modelmsg.WXWebpageObject
import com.tencent.mm.sdk.openapi.IWXAPI
import com.tencent.mm.sdk.openapi.WXAPIFactory
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent

/**
 * Created by jamisonline on 2017/7/10.
 */
class ShareManager(var context: Activity, var shareData: ShareData, var wbShareCallback: WbShareCallback, var iUiListener: IUiListener?) {

    val WB_APP_KEY by lazy { getMetaData(context, "WB_APP_KEY") }
    val WX_APP_ID by lazy { getMetaData(context, "WX_APP_ID") }

    //微博
    val wbShareHandler by lazy { WbShareHandler(context) }
    val weiboMessage: WeiboMultiMessage by lazy { createWbMessage() }


    //微信
    val iwxapi: IWXAPI by lazy { createIWXAPI() }
    val req by lazy { createReq() }

    //QQ
    val mAppid = "1105522002"
    val mTencent: Tencent by lazy { Tencent.createInstance(mAppid, context); }

    fun shareToWXConversation() {
        req.scene = SendMessageToWX.Req.WXSceneSession
        iwxapi.sendReq(req)
    }

    fun shareToWXFirends() {
        req.scene = SendMessageToWX.Req.WXSceneTimeline
        iwxapi.sendReq(req)
    }


    fun shareToWb() {
        wbShareHandler.registerApp()
        wbShareHandler.shareMessage(weiboMessage, true)
    }


    fun onNextIntent(intent: Intent) {
        wbShareHandler.doResultIntent(intent, wbShareCallback)
    }

    fun shareToQq() {
        val bundle = Bundle()
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString("targetUrl", shareData.url)
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString("title", shareData.title)
        //分享的图片URL
        bundle.putString("imageUrl", shareData.imgUrl)
        //分享的消息摘要，最长50个字
        bundle.putString("summary", shareData.description)
        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        bundle.putString("appName", "鹦鹉美家")
        //标识该消息的来源应用，值为应用名称+AppId。
        bundle.putString("appSource", "鹦鹉美家" + mAppid)

        mTencent.shareToQQ(context, bundle, iUiListener)
    }

    private fun createReq(): SendMessageToWX.Req {
        val req = SendMessageToWX.Req()
        val webpage = WXWebpageObject()
        webpage.webpageUrl = shareData.url


        val msg = WXMediaMessage(webpage)
        msg.title = shareData.title
        msg.description = shareData.description
        //这里替换一张自己工程里的图片资源
        msg.setThumbImage(shareData.img)

        req.transaction = System.currentTimeMillis().toString()
        req.message = msg

        return req
    }

    private fun createIWXAPI(): IWXAPI {
        val api = WXAPIFactory.createWXAPI(context, WX_APP_ID, true)
        api.registerApp(WX_APP_ID)
        return api
    }


    private fun createWbMessage(): WeiboMultiMessage {
        val weiboMessage = WeiboMultiMessage()
        weiboMessage.mediaObject = getWeiboPageObj()
        weiboMessage.textObject = getTextObj("我在鹦鹉美家发现了一个精美作品" + shareData.title + "@鹦鹉美家:")
        return weiboMessage
    }

    private fun getWeiboPageObj(): WebpageObject {
        val webpageObject = WebpageObject()
        webpageObject.setThumbImage(shareData.img)
        webpageObject.title = shareData.title//不能超过512
        webpageObject.actionUrl = shareData.url// 不能超过512
        webpageObject.description = shareData.description//不能超过1024
        webpageObject.identify = Utility.generateGUID()//这个不知道做啥的
        webpageObject.defaultText = "Webpage 默认文案"//这个也不知道做啥的
        return webpageObject
    }

    /**
     * 创建文本消息对象。

     * @return 文本消息对象。
     */
    private fun getTextObj(text: String): TextObject {
        val textObject = TextObject()
        textObject.text = text
        return textObject
    }

    /**
     * 获取AndroidManifest中配置的meta-data

     * @param context Context
     * *
     * @param key     String
     * *
     * @return String
     */
    fun getMetaData(context: Context?, key: String?): String? {
        var metaData: Bundle? = null
        var value: String? = null
        if (context == null || key == null) {
            return null
        }
        try {
            val ai = context.packageManager.getApplicationInfo(
                    context.packageName, PackageManager.GET_META_DATA)
            if (null != ai) {
                metaData = ai.metaData
            }
            if (null != metaData) {
                value = metaData.getString(key)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            // Nothing to do
        }

        return value
    }

}