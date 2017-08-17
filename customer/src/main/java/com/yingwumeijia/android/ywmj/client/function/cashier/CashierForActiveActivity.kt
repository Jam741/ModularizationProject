package com.yingwumeijia.android.ywmj.client.function.cashier

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.BillSimpleInfo
import com.yingwumeijia.baseywmj.function.UserManager
import kotlinx.android.synthetic.main.toolbr_layout.*
import kotlinx.android.synthetic.main.ywmj_cashier_act.*


/**
 * Created by jamisonline on 2017/8/7.
 */
class CashierForActiveActivity : JBaseActivity(), CashierContract.View {

    val billId by lazy { intent.getStringExtra(Constant.KEY_BILL_ID) }

    val presenter by lazy { CashierPresenter(context, this, billId) }

    val PAY_SUCCESS_KEY = "PAY_SUCCESS_KEY"
    val PAY_SUCCESS_BILLID_KEY = "PAY_SUCCESS_BILLID_KEY"

    companion object {
        fun start(context: Activity, billid: String) {
            val starter = Intent(context, CashierForActiveActivity::class.java)
            starter.putExtra(Constant.KEY_BILL_ID, billid)
            context.startActivityForResult(starter, CashierPresenter.ybao_resquest)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ywmj_cashier_act)
        topTitle.text = "在线支付"
        presenter.registerWXPay()

        rv_pay.run {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.payTypeAdapter
        }

        btn_pay.setOnClickListener { presenter.toPay() }
        topLeft.setOnClickListener { close() }

    }

    override fun onResume() {
        super.onResume()
        if (!UserManager.isLogin(context)) {
            close()
            return
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null)
            presenter.onUnionPayResult(data)
    }


    override fun didPaySuccess() {
        toastWith("支付成功")
        val intent = Intent()
        intent.putExtra(PAY_SUCCESS_KEY, true)
        intent.putExtra(PAY_SUCCESS_BILLID_KEY, billId)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun didPayFailure() {
        toastWith("支付失败")
    }

    override fun initBillSimple(billSimpleInfo: BillSimpleInfo) {
        tv_amount.text = billSimpleInfo.cashAmount.toString()
        if (billSimpleInfo.isWhetherPayed)
            didPaySuccess()
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.unregisterWXPay()
    }

}