package com.yingwumeijia.baseywmj.function.enter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import cn.qqtheme.framework.entity.Province
import cn.qqtheme.framework.picker.AddressPicker
import cn.qqtheme.framework.util.ConvertUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.ApplyJoinBean
import com.yingwumeijia.baseywmj.utils.VerifyUtils
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import kotlinx.android.synthetic.main.edit_enter_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*
import java.io.IOException
import java.util.*

/**
 * Created by jamisonline on 2017/7/5.
 */
class EditEnterActivity : JBaseActivity(), EditEnterContract.View {


    companion object {
        fun start(context: Context) {
            val starter = Intent(context, EditEnterActivity::class.java)
            context.startActivity(starter)
        }
    }

    val presenter by lazy { EditEnterPresenter(context, this) }

    val addressPic by lazy { createAddressPick() }

    var companyAreaId = 0

    private val applyJoinBean by lazy { ApplyJoinBean() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_enter_act)
        topTitle.text = "申请入驻"

        topLeft.setOnClickListener { close() }
        btn_sendCode.setOnClickListener { if (verifyPhone(phoneValue())) presenter.sendSmsCode(phoneValue()) }
        btn_address.setOnClickListener { addressPic.show() }
        btn_commit.setOnClickListener { if (verifyApplyJoinbean()) presenter.commit(applyJoinBean) }
        ed_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_sendCode.isEnabled = true
            }
        })

        btn_sendCode.setOnClickListener { if (verifyPhone(phoneValue()))presenter.sendSmsCode(phoneValue()) }
    }

    private fun verifyApplyJoinbean(): Boolean {
        if (verifyCompanyName(companyNameValue())
                && verifyCompanyAddress(companyAreaId)
                && verifyContractName()
                && verifyPhone(phoneValue())
                && verifySMSCode(smsCodeValue())) {
            applyJoinBean.companyName = companyNameValue()
            applyJoinBean.areaId = companyAreaId
            applyJoinBean.contactName = contractNameValue()
            applyJoinBean.contactPhone = phoneValue()
            applyJoinBean.smsCode = smsCodeValue()
            return true
        }
        return false
    }


    override fun setSendSmsCodeText(s: String?) {
        btn_sendCode.text = s
    }

    override fun lockSendSmsButton() {
        btn_sendCode.run {
            setBackgroundDrawable(resources.getDrawable(R.drawable.button_gradual_bg_round))
            isEnabled = false
            text = "60s"
            setTextColor(resources.getColor(R.color.text_color_whide))
        }
    }

    override fun unLockSendSmsButton() {
        btn_sendCode.run {
            setBackgroundDrawable(resources.getDrawable(R.drawable.button_gradual_bg_round))
            isEnabled = true
            text = "重新发出"
        }
    }

    override fun joinSuccess() {
        toastWith("您的申请已经提交，请耐心等待！")
        close()
    }

    fun phoneValue(): String {
        return ed_phone.text.toString()
    }

    fun smsCodeValue(): String {
        return ed_code.text.toString()
    }

    fun companyNameValue(): String {
        return ed_companyName.text.toString()
    }

    fun contractNameValue(): String {
        return ed_name.text.toString()
    }

    fun companyAreaId(): Int {
        return companyAreaId
    }

    fun createAddressPick(): AddressPicker {
        return AddressPicker(context, getProvinces()).apply {
            setCancelTextColor(resources.getColor(R.color.color_6))
            setCancelText(R.string.btn_cancel)
            setCancelTextSize(16)
            setSubmitTextColor(resources.getColor(R.color.color_6))
            setSubmitText(R.string.btn_confirm)
            setTextColor(resources.getColor(R.color.color_6), resources.getColor(R.color.color_5))
            setSubmitTextSize(16)
            setCycleDisable(true)
            setHeight(ScreenUtils.screenHeight / 3)
            setLineColor(resources.getColor(R.color.color_3))
            setOnAddressPickListener { province, city, county ->
                companyAreaId = Integer.valueOf(county.areaId)
                btn_address.text = province.areaName + " " + city.areaName + " " + county.areaName
            }
        }

    }


    private fun getProvinces(): ArrayList<Province> {
        var addressJson: String? = null
        try {
            addressJson = ConvertUtils.toString(context.assets.open("allarea.json"))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val addressJsonLast = addressJson!!.replace("id", "areaId").replace("name", "areaName").replace("areas", "counties")

        val provinces = Gson().fromJson<ArrayList<Province>>(addressJsonLast, object : TypeToken<List<Province>>() {}.type)

        return provinces
    }

    fun verifyPhone(phone: String?): Boolean {
        if (!VerifyUtils.verifyMobilePhoneNumber(phone)) {
            toastWith("请输入正确格式的电话号码")
            return false
        } else {
            return true
        }
    }

    fun verifySMSCode(smsCode: String?): Boolean {
        if (!VerifyUtils.verifySmsCode(smsCode)) {
            toastWith("请输入正确的验证码")
            return false
        }
        return true
    }

    fun verifyCompanyName(companyName: String?): Boolean {
        if (!VerifyUtils.verifyCompanyName(companyName)) {
            toastWith("请输入正确的公司中文名称")
            return false
        }
        return true
    }

    fun verifyCompanyAddress(addressId: Int): Boolean {
        if (addressId == 0) {
            toastWith("请选择公司所在地")
            return false
        }
        return true
    }

    fun verifyContractName(): Boolean {
        if (TextUtils.isEmpty(contractNameValue()) || contractNameValue().trim().length < 2) {
            toastWith("请输入正确的联系人姓名")
            return false
        }
        return true
    }


}