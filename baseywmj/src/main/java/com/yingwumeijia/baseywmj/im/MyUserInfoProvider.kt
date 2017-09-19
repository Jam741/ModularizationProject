package com.yingwumeijia.baseywmj.im

/**
 * Created by jamisonline on 2017/7/28.
 */
class MyUserInfoProvider  {
//
//    override fun getUserInfo(p0: String?): UserInfo? {
//        return findUserById(p0!!)
//    }
//
//    private fun findUserById(p0: String): UserInfo? {
//        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getMemberInfo(p0), object : Subscriber<MemberBean>() {
//            override fun onError(e: Throwable?) {
////                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onNext(t: MemberBean?) {
//                if (t == null) return
//                var showName = t.showName
//                val userType = t.userType
//                if (userType == "c") {
//                    showName = showName + "-" + "客户"
//                } else if (userType == "e") {
//                    showName = showName + "-" + t.userTitle
//                } else if (userType == "m") {
//                    showName = showName + "-" + "美家客服"
//                }
//                val userInfo = UserInfo(
//                        p0,
//                        showName,
//                        Uri.parse(t.showHead)
//                )
//                RongIM.getInstance().refreshUserInfoCache(userInfo)
//            }
//
//            override fun onCompleted() {
//
//            }
//        })
//        return null
//    }
}