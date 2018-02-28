package com.yingwumeijia.baseywmj.nimim.provider

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.TextUtils

import com.netease.nim.uikit.NimUIKit
import com.netease.nim.uikit.cache.NimUserInfoCache
import com.netease.nim.uikit.cache.TeamDataCache
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.netease.nimlib.sdk.team.model.Team
import com.netease.nimlib.sdk.uinfo.UserInfoProvider
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.MemberBean
import com.yingwumeijia.baseywmj.utils.net.HttpUtil

import rx.Subscriber

/**
 * Created by jamisonline on 2017/9/19.
 */

class YWMJUserInfoProvider(private val context: Context) : UserInfoProvider {

    override fun getUserInfo(account: String): UserInfoProvider.UserInfo? {
        val user = NimUserInfoCache.getInstance().getUserInfo(account)
        if (user == null) {

            HttpUtil.getInstance().toNolifeSubscribe(Api.service.getMemberInfo(account), object : Subscriber<MemberBean>() {
                override fun onError(e: Throwable?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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


//                    val userInfo = NimUserInfo()

//                    NimUserInfoCache.getInstance().registerObservers()

//                    NimUserInfoCache.getInstance()

                }

                override fun onCompleted() {

                }
            })
        }

        return user
    }

    override fun getDefaultIconResId(): Int {
        return com.netease.nim.uikit.R.drawable.nim_avatar_default
    }

    override fun getDisplayNameForMessageNotifier(account: String, sessionId: String, sessionType: SessionTypeEnum): String? {
        var nick: String? = null
        if (sessionType == SessionTypeEnum.P2P) {
            nick = NimUserInfoCache.getInstance().getAlias(account)
        } else if (sessionType == SessionTypeEnum.Team) {
            nick = TeamDataCache.getInstance().getDisplayNameWithoutMe(sessionId, account)
        }
        // 返回null，交给sdk处理。如果对方有设置nick，sdk会显示nick
        if (TextUtils.isEmpty(nick)) {
            return null
        }

        return nick
    }

    override fun getAvatarForMessageNotifier(account: String): Bitmap? {
        /*
         * 注意：这里最好从缓存里拿，如果加载时间过长会导致通知栏延迟弹出！该函数在后台线程执行！
         */
        val user = getUserInfo(account)
        return if (user != null) NimUIKit.getImageLoaderKit().getNotificationBitmapFromCache(user.avatar) else null
    }

    override fun getTeamIcon(teamId: String): Bitmap? {
        /*
         * 注意：这里最好从缓存里拿，如果加载时间过长会导致通知栏延迟弹出！该函数在后台线程执行！
         */
        val team = TeamDataCache.getInstance().getTeamById(teamId)
        if (team != null) {
            val bm = NimUIKit.getImageLoaderKit().getNotificationBitmapFromCache(team.icon)
            if (bm != null) {
                return bm
            }
        }

        // 默认图
        val drawable = context.resources.getDrawable(com.netease.nim.uikit.R.drawable.nim_avatar_group)
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        return null
    }
}
