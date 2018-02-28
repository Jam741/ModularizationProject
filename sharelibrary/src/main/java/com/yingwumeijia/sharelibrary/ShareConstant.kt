package com.yingwumeijia.sharelibrary


/**
 * Created by Jam on 2016/8/11 15:12.
 * Describe:
 */
interface ShareConstant {
    companion object {

//        val APP_KEY = ShareManager.WB_APP_KEY           // 应用的APP_KEY
        val REDIRECT_URL = "https://api.weibo.com/oauth2/default.html"// 应用的回调页
        val SCOPE = // 应用申请的高级权限
                "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write"
    }
}
