package com.yingwumeijia.android.ywmj.client.function.bill

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.bill.list.BillPayedListActivity
import com.yingwumeijia.android.ywmj.client.function.bill.list.BillPaymentListActivity
import com.yingwumeijia.baseywmj.base.JBaseActivity
import kotlinx.android.synthetic.main.mine_bill_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/27.
 */
class MyBillActivity : JBaseActivity() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, MyBillActivity::class.java)
            context.startActivity(starter)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mine_bill_act)
        topTitle.text = "账单"
        topLeft.setOnClickListener { close() }
        btn_bill_payment.setOnClickListener { BillPaymentListActivity.start(context) }
        btn_bill_detail.setOnClickListener { BillPayedListActivity.start(context) }
    }


}