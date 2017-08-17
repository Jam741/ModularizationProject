package com.yingwumeijia.baseywmj.im

import android.net.Uri
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.SessionDetailBean
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import io.rong.imkit.RongIM
import io.rong.imlib.model.Group
import rx.Subscriber

/**
 * Created by jamisonline on 2017/7/28.
 */
class MyGroupInfoProvider : RongIM.GroupInfoProvider {
    override fun getGroupInfo(p0: String?): Group? {
        return findGrupById(p0!!)
    }

    private fun findGrupById(p0: String): Group? {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getSessionInfo(p0), object : Subscriber<SessionDetailBean>() {
            override fun onNext(t: SessionDetailBean?) {
                val group = Group(
                        p0,
                        t!!.sessionInfo.name,
                        Uri.parse(t!!.relatedCaseInfo.caseCover)
                )
                RongIM.getInstance().refreshGroupInfoCache(group)
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
            }
        })
        return null
    }
}