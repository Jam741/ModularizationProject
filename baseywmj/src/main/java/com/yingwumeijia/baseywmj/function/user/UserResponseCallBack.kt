package com.yingwumeijia.baseywmj.function.user

import com.yingwumeijia.baseywmj.entity.bean.UserBean

/**
 * Created by jamisonline on 2017/6/15.
 */
interface UserResponseCallBack {

    /**
     * 登录失败
     */
    fun error(msg: String, code: Int)

    /**
     * 登录成功
     */
    fun success(userBean: UserBean)

    /**
     * 登录完成
     */
    fun completed()
}