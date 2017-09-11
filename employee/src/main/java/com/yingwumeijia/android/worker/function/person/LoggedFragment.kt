package com.yingwumeijia.android.worker.function.person

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.android.worker.R
import com.yingwumeijia.android.worker.function.person.info.PersonInfoActivity
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.UserBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.personal.BaseLoggedFragment
import com.yingwumeijia.baseywmj.function.personal.PersonalFragment
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.PhoneNumberUtils
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import kotlinx.android.synthetic.main.person_logged_header_e.*

/**
 * Created by jamisonline on 2017/6/30.
 */
class LoggedFragment : BaseLoggedFragment() {

    /**
     * 是否接受派单
     */
    override fun showDistributionStatus(show: Boolean) {
        switch1.isChecked = show
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
        return inflater!!.inflate(R.layout.person_logged_header_e, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onUserDataChanged()
        imageView.setOnClickListener {
            imageView.setOnClickListener { PersonInfoActivity.start(activity, userBean!!.showHead, userBean!!.showName, PhoneNumberUtils.getCryptographicPhone(userBean!!.userPhone), userBean!!.userDetailTypeDesc) }
        }
        switch1.setOnCheckedChangeListener { _, isChecked -> distribution(isChecked) }

    }

    /**
     * 设置是否接受分配
     */
    private fun distribution(checked: Boolean) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.distribution(checked), object : SimpleSubscriber<Boolean>(activity) {
            override fun _onNext(t: Boolean?) {
                if (t != null) showDistributionStatus(t)
            }

        })
    }

    private fun initView() {
        if (userBean == null) return Unit
        when (userBean!!.userTypeExtension) {
            PersonalFragment.USER_TYPE_E_JJGW -> switch1.visibility = View.VISIBLE
            PersonalFragment.USER_TYPE_E_KFJL -> switch1.visibility = View.VISIBLE
            PersonalFragment.USER_TYPE_E_NORMAL -> switch1.visibility = View.GONE
            PersonalFragment.USER_TYPE_E_DESIGNER -> switch1.visibility = View.GONE
        }

        val role = userBean!!.userDetailTypeDesc


        tv_name.text = if (TextUtils.isEmpty(userBean!!.showName)) "点击编辑信息" else userBean!!.showName + " - " + role
        if (TextUtils.isEmpty(userBean!!.showHead))
            JImageLolder.loadPortrait256(getContext(), iv_portrait, R.mipmap.mine_user_ywmj_circle)
        else
            JImageLolder.loadPortrait256(getContext(), iv_portrait, userBean!!.showHead)
    }
}