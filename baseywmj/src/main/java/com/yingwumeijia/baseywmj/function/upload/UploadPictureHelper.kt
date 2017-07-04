package com.yingwumeijia.baseywmj.function.upload

import android.content.Context
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.ImageTools
import org.json.JSONObject
import java.io.File

/**
 * Created by jamisonline on 2017/7/4.
 */
object UploadPictureHelper {


    fun uploadSinglePicture(context: Context, filePath: String, singleLoadListener: OnSingleLoadListener) {
        val ob = Api.service.getUpLoadToken()
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                UploadManager().put(File(filePath), null, t!!, { key, info, response -> singleLoadListener.success(assmibleUrl(response, filePath)) }, null)
            }

        })
    }


    private fun assmibleUrl(res: JSONObject, filePath: String): String {
        return Config.BASE_QINIU_URL + res.getString("key") + ImageTools.getImageWidthHeightStringParam(filePath)
    }


    interface OnSingleLoadListener {
        /**
         * @param url 上传成功的图片的地址
         */
        fun success(url: String)

    }

    interface OnMultinLoadListener {

        /**
         * @param urls 上传成功的图片的地址列表
         */
        fun success(urls: ArrayList<String>)
    }
}