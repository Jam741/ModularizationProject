package com.yingwumeijia.android.ywmj.client.function.person

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.person.info.PersonInfoActivity
import com.yingwumeijia.baseywmj.entity.bean.UserBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.personal.BaseLoggedFragment
import com.yingwumeijia.commonlibrary.utils.PhoneNumberUtils
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import kotlinx.android.synthetic.main.person_logged_header.*

/**
 * Created by jamisonline on 2017/6/23.
 *
 * C端 已登录头部片段
 */

class LoggedFragment : BaseLoggedFragment() {

    override fun showDistributionStatus(show: Boolean) {

    }

    override fun onUserDataChanged() {
        userBean = UserManager.getUserData()
        initView()
    }


    companion object {
        fun newInstance(): LoggedFragment {
            return LoggedFragment()
        }
    }


    var userBean: UserBean? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.person_logged_header, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView.setOnClickListener { PersonInfoActivity.start(activity, userBean!!.showHead, userBean!!.showName, PhoneNumberUtils.getCryptographicPhone(userBean!!.userPhone)) }
    }

    private fun initView() {
        if (userBean == null) return Unit
        if (TextUtils.isEmpty(userBean!!.name)) return
        tv_name.text = userBean!!.showName
        if (TextUtils.isEmpty(userBean!!.showHead))
            JImageLolder.loadPortrait256(context.context, iv_portrait, R.mipmap.mine_user_ywmj_circle)
        else
            JImageLolder.loadPortrait256(context, iv_portrait, userBean!!.showHead)
    }

}
