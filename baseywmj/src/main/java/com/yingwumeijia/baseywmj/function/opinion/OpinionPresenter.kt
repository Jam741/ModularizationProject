package com.yingwumeijia.baseywmj.function.opinion

import android.content.Context
import android.os.Build
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.function.upload.UploadPictureHelper
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.AppUtils
import com.yingwumeijia.commonlibrary.utils.DeviceID
import com.yingwumeijia.commonlibrary.utils.ListUtil


/**
 * Created by jamisonline on 2017/7/5.
 */
class OpinionPresenter(var context: Context, var view: OpinionContract.View) : OpinionContract.Presenter {


    val feedbackBean by lazy {
        FeedbackBean(DeviceID.getDeviceID(context), "android " + Build.VERSION.RELEASE, android.os.Build.MODEL, AppUtils.getVersionName(context), null, null)
    }

    override fun commit(images: ArrayList<String>, content: String) {
        feedbackBean.content = content
        if (ListUtil.isEmpty(images)) {
            sendOpinion(feedbackBean)
        } else {
            UploadPictureHelper.upLoadMultinPicture(context, images, object : UploadPictureHelper.OnMultinLoadListener {
                override fun success(urls: ArrayList<String>?) {
                    feedbackBean.images = urls
                    sendOpinion(feedbackBean)
                }
            })
        }
    }


    fun sendOpinion(feedbackBean: FeedbackBean) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.sendOpinion(feedbackBean), object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                view.showSuccess()
            }
        })
    }
}