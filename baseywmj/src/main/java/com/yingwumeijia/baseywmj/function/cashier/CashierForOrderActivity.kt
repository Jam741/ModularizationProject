package com.yingwumeijia.baseywmj.function.cashier

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.text.InputFilter
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.yingwumeijia.baseywmj.BuildConfig
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.BillSimpleInfo
import kotlinx.android.synthetic.main.cashier_for_order.*
import kotlinx.android.synthetic.main.toolbr_layout.*
import java.math.BigDecimal

/**
 * Created by jamisonline on 2017/8/7.
 */
class CashierForOrderActivity : JBaseActivity(), CashierContract.View, CashierPresenter.PayTypeChangedListener {


    val PAY_SUCCESS_BILLID_KEY = "PAY_SUCCESS_BILLID_KEY"

    val billId by lazy { intent.getStringExtra(Constant.KEY_BILL_ID) }

    val presenter by lazy { CashierPresenter(context, this, billId).apply { payTypeChangedListener = this@CashierForOrderActivity } }

    val miniAmount by lazy { if (BuildConfig.DEBUG) BigDecimal("0.02") else BigDecimal("300.00") }

    val inputPhasePayDialog by lazy { createInputPhasePayDialog() }

    private var phaseAmount: BigDecimal? = null
    private var cashAmount = ""
    private var unPayAmount: BigDecimal? = null
    private var currentPayTypeBean: PayTypeBean? = null


    companion object {
        fun start(context: Activity, billid: String) {
            val starter = Intent(context, CashierForOrderActivity::class.java)
            starter.putExtra(Constant.KEY_BILL_ID, billid)
            context.startActivityForResult(starter, Constant.REQUEST_CODE_FOR_PAY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cashier_for_order)
        topTitle.text = "支付方式"
        rv_pay.run {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.payTypeAdapter
        }

        topLeft.setOnClickListener { close() }
        btn_phasePay.setOnClickListener { inputPhasePayDialog.show() }
        btn_pay.setOnClickListener { presenter.toPay() }

        presenter.registerWXPay()

    }

    override fun onResume() {
        super.onResume()
        presenter.checkBillSimpleInfo()
    }


    override fun onDestroy() {
        presenter.unregisterWXPay()
        super.onDestroy()
    }

    override fun didPaySuccess() {
        toastWith("支付成功")
        val intent = Intent()
        intent.putExtra(Constant.PAY_SUCCESS_KEY, true)
        intent.putExtra(PAY_SUCCESS_BILLID_KEY, billId)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun didPayFailure() {
        toastWith("支付失败")
    }

    override fun initBillSimple(billSimpleInfo: BillSimpleInfo) {
        tv_allAmount.text = "￥ " + billSimpleInfo.amount + ""
        tv_payedAmount.text = "￥ " + billSimpleInfo.handledAmount + ""
        tv_unpayAmount.text = "￥ " + billSimpleInfo.cashAmount + ""
        unPayAmount = billSimpleInfo.cashAmount
        cashAmount = billSimpleInfo.cashAmount.toString()
        if (miniAmount.compareTo(unPayAmount) == -1) {
            phase_pay_layout.visibility = View.VISIBLE
        } else {
            phase_pay_layout.visibility = View.GONE
        }
        if (billSimpleInfo.isWhetherPayed)
            didPaySuccess()
        if (unPayAmount!!.compareTo(BigDecimal("0.00")) == 0) {
            finish()
        }
        if (currentPayTypeBean != null) {
            onPayTypeChanged(currentPayTypeBean!!)
        } else {
            btn_pay.text = "支付宝支付 " + cashAmount
        }
    }


    override fun onPayTypeChanged(payTypeBean: PayTypeBean) {
        currentPayTypeBean = payTypeBean
        when (payTypeBean.payType) {
            PayType.ALI -> btn_pay.text = "支付宝支付 " + cashAmount
            PayType.WCHART -> btn_pay.text = "微信支付 " + cashAmount
            PayType.YBAO -> btn_pay.text = "易宝支付 " + cashAmount
        }
    }


    /**
     * 分批支付弹窗
     */
    fun createInputPhasePayDialog(): AlertDialog {
        val phaselayout = LayoutInflater.from(context).inflate(R.layout.pay_phase_layout, null) as CardView
        val edInputPhaseContent = phaselayout.findViewById(R.id.ed_content) as EditText
        phaselayout.findViewById(R.id.btn_cancel).setOnClickListener {
            if (verifyAmount(edInputPhaseContent.text.toString())) {
                presenter.toPayPart(phaseAmount!!)
                inputPhasePayDialog!!.dismiss()
                edInputPhaseContent.setText("")
            }
        }
        phaselayout.findViewById(R.id.btn_confirm).setOnClickListener {
            inputPhasePayDialog!!.dismiss()
            edInputPhaseContent.setText("")
        }
        edInputPhaseContent.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
        edInputPhaseContent.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            if (source == "." && dest.toString().length == 0) {
                return@InputFilter "0."
            }
            if (dest.toString().contains(".")) {
                val index = dest.toString().indexOf(".")
                val mlength = dest.toString().substring(index).length
                if (mlength == 3) {
                    return@InputFilter ""
                }
            }
            return@InputFilter null
        })
        return AlertDialog
                .Builder(context)
                .setView(phaselayout)
                .create()
    }


    /**
     * 校验输入的分批支付金额

     * @param s
     * *
     * @return
     */
    private fun verifyAmount(s: String): Boolean {
        if (TextUtils.isEmpty(s)) {
            toastWith("输入不能为空")
            return false
        }
        phaseAmount = BigDecimal(s)
        if (phaseAmount!!.compareTo(miniAmount) == -1) {
            Log.d("jam", "phaseAmount:" + phaseAmount)
            toastWith("支付金额不能小于 ￥" + miniAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble())
            return false
        }
        if (unPayAmount!!.compareTo(phaseAmount) == -1) {
            toastWith("支付金额不能大于待付金额￥" + unPayAmount!!.setScale(2, BigDecimal.ROUND_HALF_UP).toDouble())
            return false
        }

        return true
    }

}