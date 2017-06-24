package com.yingwumeijia.baseywmj.function.personal.c

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.entity.bean.UserBean
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import kotlinx.android.synthetic.main.person_logged_header_c.*

/**
 * Created by jamisonline on 2017/6/23.
 */

class LoggedFragment : JBaseFragment() {

    var userBean: UserBean? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.person_logged_header_c, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView

    }

    private fun initView() {
        if (userBean == null) return Unit
        tv_name.text = userBean!!.showName
        if (TextUtils.isEmpty(userBean!!.showHead))
            JImageLolder.load(getContext(), iv_portrait, R.mipmap.mine_user_ywmj_circle)
        else
            JImageLolder.load(context, iv_portrait, userBean!!.showHead)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Logger.d(hidden)
    }

}
