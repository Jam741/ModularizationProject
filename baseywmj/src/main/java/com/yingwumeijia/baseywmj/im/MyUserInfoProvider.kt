package com.yingwumeijia.baseywmj.im

import android.net.Uri
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.MemberBean
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import io.rong.imkit.RongIM
import io.rong.imlib.model.UserInfo
import rx.Subscriber

/**
 * Created by jamisonline on 2017/7/28.
 */
class MyUserInfoProvider : RongIM.UserInfoProvider {

    override fun getUserInfo(p0: String?): UserInfo? {
        return findUserById(p0!!)
    }

    private fun findUserById(p0: String): UserInfo? {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getMemberInfo(p0), object : Subscriber<MemberBean>() {
            override fun onError(e: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onNext(t: MemberBean?) {
                if (t == null) return
                var showName = t.showName
                val userType = t.userType
                if (userType == "c") {
                    showName = showName + "-" + "客户"
                } else if (userType == "e") {
                    showName = showName + "-" + t.userTitle
                } else if (userType == "m") {
                    showName = showName + "-" + "美家客服"
                }
                val userInfo = UserInfo(
                        p0,
                        showName,
                        Uri.parse(t.showHead)
                )
                RongIM.getInstance().refreshUserInfoCache(userInfo)
            }

            override fun onCompleted() {

            }
        })
        return null
    }
}