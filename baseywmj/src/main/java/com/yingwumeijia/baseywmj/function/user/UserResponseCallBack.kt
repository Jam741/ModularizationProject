package com.yingwumeijia.baseywmj.function.user

import com.yingwumeijia.baseywmj.entity.bean.UserBean

/**
 * Created by jamisonline on 2017/6/15.
 */
interface UserResponseCallBack {

    public fun error(msg: String, code: Int)

    public fun success(userBean: UserBean)

    public fun completed()
}