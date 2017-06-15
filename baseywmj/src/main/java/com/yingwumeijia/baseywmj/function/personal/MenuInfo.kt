package com.yingwumeijia.baseywmj.function.personal

import android.graphics.drawable.Icon
import android.support.annotation.IdRes
import com.yingwumeijia.baseywmj.R

/**
 * Created by jamisonline on 2017/6/11.
 */

data class MenuInfo constructor(var action: MenuAction, @IdRes var icon: Int, var text: String, var textColor: Int) {

    constructor(action: MenuAction, icon: Int, text: String) : this(action, icon, text, R.color.color_1)
}