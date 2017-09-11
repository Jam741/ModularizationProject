package com.yingwumeijia.android.ywmj.client.function.bill.list.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.BillSimpleInfo
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.FromartDateUtil
import kotlinx.android.synthetic.main.bill_payed_detail_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/27.
 */
class BillPayedDetailActivity : JBaseActivity() {

    val bilId by lazy { intent.getIntExtra(Constant.KEY_BILL_ID, Constant.DEFAULT_INT_VALUE) }

    companion object {
        fun start(context: Context, billId: Int) {
            val starter = Intent(context, BillPayedDetailActivity::class.java)
            starter.putExtra(Constant.KEY_BILL_ID, billId)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bill_payed_detail_act)
        topTitle.text = "支付详情"

        topLeft.setOnClickListener { close() }

        loadBillSimpleData()
    }

    private fun loadBillSimpleData() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.checkBillSimpleInfo(bilId.toString()), object : ProgressSubscriber<BillSimpleInfo>(context) {
            override fun _onNext(t: BillSimpleInfo?) {
                if (t == null) return
                initView(t)
            }

        })
    }

    private fun initView(billSimpleInfo: BillSimpleInfo) {
        tv_price.text = "" + billSimpleInfo.amount
        tv_companyName.text = billSimpleInfo.companyName
        tv_contentType.text = billSimpleInfo.serviceContentStr
        tv_date.text = FromartDateUtil.fromartDateYMdHmSs(billSimpleInfo.payTime)
    }

}